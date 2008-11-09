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
