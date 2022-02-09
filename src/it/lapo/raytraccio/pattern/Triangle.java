// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Modifica un altro pattern modificando il suo range
 * <code>1.0-2.0*abs(x-0.5)</code>.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Triangle extends Pattern {

    /** pattern da modificare */
    Pattern pat;

    public Triangle(Pattern p) {
        pat = p;
    }

    public double scalar(Vector3D p) {
        double u = pat.scalar(p);
        if (u > 0.5)
            u = 1.0 - u;
        u *= 2;
        return (u);
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double u[] = pat.vectorial(p, dim);
        while (dim-- > 0) {
            if (u[dim] > 0.5)
                u[dim] = 1.0 - u[dim];
            u[dim] *= 2;
        }
        return (u);
    }

}
