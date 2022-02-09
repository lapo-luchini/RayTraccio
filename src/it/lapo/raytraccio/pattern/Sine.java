// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Modifica un altro pattern modificando il suo range comprimendolo agli estremi
 * <code>0.5+0.5*sin((PI/2)*(x-0.5))</code>.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Sine extends Pattern {

    /** pattern da modificare */
    Pattern pat;

    public Sine(Pattern p) {
        pat = p;
    }

    public double scalar(Vector3D p) {
        return (0.5 + 0.5 * Math.sin(Math.PI * (pat.scalar(p) - 0.5)));
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double u[] = pat.vectorial(p, dim);
        while (dim-- > 0)
            u[dim] = Math.sin(Math.PI * u[dim]);
        return (u);
    }

}
