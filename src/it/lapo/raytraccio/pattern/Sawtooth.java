// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Campo scalare che aumenta con l'asse X da <code>0.0</code> a
 * <code>1.0</code> e poi re-inizia da capo.
 *
 * @author: Lapo Luchini
 */
public class Sawtooth extends Pattern {

    public double scalar(Vector3D p) {
        return (p.x - Math.floor(p.x));
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double[] v = new double[dim];
        double nv = p.x - Math.floor(p.x);
        while (dim-- > 0)
            v[dim] = nv;
        return (v);
    }

}
