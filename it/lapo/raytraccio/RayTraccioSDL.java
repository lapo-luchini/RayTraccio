// RayTraccio ray-tracing library Copyright (c) 2001-2002 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/RayTraccio.java,v 1.22 2002/02/23 16:41:16 lapo Exp $

// This file is part of RayTraccio.
//
// RayTraccio is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// RayTraccio is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RayTraccio; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

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

    public void init() {
        super.init();
        initRenderer(loadScene("default.sdl"));
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
        RT.init();
        if (filename != null)
            RT.loadScene(filename);
        RT.start();
        f.add("Center", RT);
        f.setSize(dimX + 20, dimY + 40);
        f.setVisible(true);
        f.addWindowListener(RT);
    }

}
