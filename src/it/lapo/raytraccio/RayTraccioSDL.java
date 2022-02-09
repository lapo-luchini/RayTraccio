// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio;

import it.lapo.raytraccio.parser.SDL;

import java.awt.Dimension;
import java.awt.Frame;

/**
 * Applet dimostrativo del componente {@link RayTracer}(non contiene altro).
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class RayTraccioSDL extends RayTraccio {

    private String sdl = "examples/default.sdl";

    public void init() {
        super.init();
        initRenderer(loadScene(sdl));
    }

    public Scene loadScene(String str) {
        Scene scn = null;
        java.io.Reader r = null;
        if ((str.length() > 4) && str.substring(str.length() - 4, str.length()).equalsIgnoreCase(".sdl")) { // is a filename
            try {
                // tries online
                java.net.URL u = new java.net.URL(getCodeBase(), str);
                System.out.println("Loading URL " + u);
                r = new java.io.InputStreamReader(u.openStream());
            } catch (Exception e) {
                // tries offline
                try {
                    System.out.println("Loading local file " + str);
                    r = new java.io.FileReader(str);
                } catch (Exception e2) {
                    r = null;
                }
            }
            if (r == null)
                throw new RuntimeException("Could not load file: " + str);
        } else {
            r = new java.io.StringReader(str);
            if (r == null)
                throw new RuntimeException("Could not load string data.");
        }
        // System.out.println("Using following Reader: "+r);
        rt.setVisible(true);
        error.setVisible(false);
        try {
            long t = System.currentTimeMillis();
            scn = (new SDL(r)).sdlScene();
            System.out.println("Parsed in " + (System.currentTimeMillis() - t)
                    + "ms");
            // rt.init(scn, 1 /*threads*/, size, scala, true);
            // ((CardLayout)getLayout()).show(this, "rt");
        } catch (Exception e) {
            scn = null;
            // error.setText(e.toString());
            // ((CardLayout)getLayout()).show(this, "er");
            System.err.println(e);
        }
        return scn;
    }

    public static void main(String as[]) {
        int dimX = 300, dimY = 300, scala = 3;
        String filename = null;
        if (as.length >= 1) {
            filename = as[0];
            if (as.length >= 3) {
                dimX = Integer.parseInt(as[1]);
                dimY = Integer.parseInt(as[2]);
                scala = 1;
                if (as.length >= 4)
                    scala = Integer.parseInt(as[3]);
            }
        } else if (as.length != 0) {
            System.out.println("USE: java RayTraccio [filename.sdl [dimX dimY [scale]]]");
            System.exit(1);
        }
        Frame f = new Frame("RayTraccio");
        RayTraccioSDL RT = new RayTraccioSDL();
        RT.setRenderSize(new Dimension(dimX, dimY), scala);
        if (filename != null)
            RT.sdl = filename;
        RT.init();
        RT.start();
        f.add("Center", RT);
        f.setSize(dimX + 20, dimY + 40);
        f.setVisible(true);
        f.addWindowListener(RT);
    }

}
