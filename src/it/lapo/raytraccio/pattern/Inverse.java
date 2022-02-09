// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Modifica un altro pattern invertendo il suo range <code>(1.0-x)</code>.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Inverse extends Pattern {

    /** pattern da modificare */
    Pattern pat;

    public Inverse(Pattern p) {
        pat = p;
    }

    public double scalar(Vector3D p) {
        return (1.0 - pat.scalar(p));
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double u[] = pat.vectorial(p, dim);
        while (dim-- > 0)
            u[dim] = 1.0 - u[dim];
        return (u);
    }

}
