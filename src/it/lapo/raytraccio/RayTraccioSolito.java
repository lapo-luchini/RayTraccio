// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio;

import it.lapo.raytraccio.shape.*;

import java.awt.Dimension;
import java.awt.Frame;

/**
 * Applet dimostrativo del componente {@link RayTracer}(non contiene altro).
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class RayTraccioSolito extends RayTraccio {

    public void init() {
        super.init();
        initRenderer(loadScene());
    }

    public Scene loadScene() {
        Scene scn;
        Shape3D s;
        CSG_Union u = new CSG_Union();
        it.lapo.raytraccio.texture.Map t;
        t = new it.lapo.raytraccio.texture.Map(
                new it.lapo.raytraccio.pattern.Checker(), 2);
        t.add(0.0, new it.lapo.raytraccio.texture.Plain(Color.RED, 0.0));
        t.add(1.0, new it.lapo.raytraccio.texture.Plain(Color.GREEN, 0.0));
        u.add(new it.lapo.raytraccio.shape.Plane(Vector3D.VERS_Y, 1.0, t));
        t = new it.lapo.raytraccio.texture.Map(
                new it.lapo.raytraccio.pattern.Checker(), 2);
        t.add(0.0, new it.lapo.raytraccio.texture.Plain(Color.BLUE, 0.0));
        t.add(1.0, new it.lapo.raytraccio.texture.Plain(Color.YELLOW, 0.0));
        u.add(new it.lapo.raytraccio.shape.Plane(new Vector3D(0.0, -1.0, 0.0), // -Vector.VERS_Y
                1.0, t));
        t = new it.lapo.raytraccio.texture.Map(
                new it.lapo.raytraccio.pattern.Perlin((byte) 5, 0.4, 1977l), 2);
        t.add(0.0, new it.lapo.raytraccio.texture.Plain(Color.WHITE, 0.5));
        t.add(1.0, new it.lapo.raytraccio.texture.Plain(Color.WHITE, 1.0));
        u.add(new it.lapo.raytraccio.shape.Quadric(
                it.lapo.raytraccio.shape.Quadric.SFERA,
                new it.lapo.raytraccio.texture.Scale(t, 0.2)));
        s = new it.lapo.raytraccio.shape.ShapeTransform(u, TransformMatrix.RotateY(30.0));
        scn = new Scene(s,
                new Vector3D(0.0, 1.0, -5.0),
                new Vector3D(0.0, -0.5, 0.0),
                new Vector3D(4.0, 0.0, 0.0),
                ((double) size.width) / size.height);
        scn.addLight(new Light(new Vector3D(1.0, 0.9, -5.0), Color.WHITE, 18.0));
        scn.addLight(new Light(new Vector3D(-2.0, -0.9, 0.0), Color.WHITE, 6.0));
        scn.addLight(new Light(new Vector3D(2.0, 0.9, 1.0), Color.WHITE, 6.0));
        scn.addLight(new Light(new Vector3D(-1.0, 0.9, 5.0), Color.WHITE, 6.0));
        return scn;
    }

    public static void main(String as[]) {
        int dimX = 300, dimY = 300, scala = 3;
        if (as.length >= 2) {
            dimX = Integer.parseInt(as[0]);
            dimY = Integer.parseInt(as[1]);
            scala = 1;
            if (as.length >= 3)
                scala = Integer.parseInt(as[2]);
        } else if (as.length != 0) {
            System.out.println("USE: java RayTraccio [dimX dimY [scale]]");
            System.exit(1);
        }
        Frame f = new Frame("RayTraccio");
        RayTraccioSolito RT = new RayTraccioSolito();
        RT.setRenderSize(new Dimension(dimX, dimY), scala);
        RT.init();
        RT.start();
        f.add("Center", RT);
        f.setSize(dimX + 20, dimY + 40);
        f.setVisible(true);
        f.addWindowListener(RT);
    }

}
