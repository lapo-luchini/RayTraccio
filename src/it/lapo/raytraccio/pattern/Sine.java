// RayTraccio ray-tracing library Copyright (c) 2001-2008 Lapo Luchini <lapo@lapo.it>

// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

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