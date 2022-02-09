// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Campo scalare che vale <code>0.0</code> e <code>1.0</code>
 * alternativamente in ogni cubo unitario.
 *
 * @author: Lapo Luchini
 */
public class Checker extends Pattern {

    public double scalar(Vector3D p) {
        return ((((int) Math.floor(p.x)) ^ ((int) Math.floor(p.y)) ^ ((int) Math
                .floor(p.z))) & 1);
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double[] v = new double[dim];
        double nv = (((int) Math.floor(p.x)) ^ ((int) Math.floor(p.y)) ^ ((int) Math
                .floor(p.z))) & 1;
        while (dim-- > 0)
            v[dim] = nv;
        return (v);
    }

}
