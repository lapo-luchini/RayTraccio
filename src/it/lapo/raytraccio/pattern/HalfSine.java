// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Modifica un altro pattern modificando il suo range comprimendolo sulla parte
 * alta <code>sin((PI/2)*x)</code>.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class HalfSine extends Pattern {

    /** pattern da modificare */
    Pattern pat;

    public final static double PI2 = 1.5707963267948966192313216916397514420985847;

    public HalfSine(Pattern p) {
        pat = p;
    }

    public double scalar(Vector3D p) {
        return (Math.sin(PI2 * pat.scalar(p)));
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double u[] = pat.vectorial(p, dim);
        while (dim-- > 0)
            u[dim] = Math.sin(PI2 * u[dim]);
        return (u);
    }

}
