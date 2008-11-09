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

package it.lapo.raytraccio.texture;

import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Mix di due sottomateriali. <br>
 * Il colore viene calcolato moltiplicando il colore dei due sottomateriali per
 * il relativo coefficiente.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Mix extends Texture {

    /** Array contenente i due sottomateriali */
    private Texture c[];

    /** Array contenente i valori di frazione del sottomateriale rispettivo */
    private double v[];

    public Mix(Texture a, double b, Texture d, double e) {
        c = new Texture[2];
        c[0] = a;
        c[1] = d;
        v = new double[2];
        v[0] = b / (b + e);
        v[1] = e / (b + e);
    }

    public Color color(Vector3D p) {
        return c[0].color(p).mul(v[0]).addU(c[1].color(p).mul(v[1]));
    }

    public double reflect(Vector3D p) {
        return c[0].reflect(p) * v[0] + c[1].reflect(p) * v[1];
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio: <code>TextureChecker[0.3,Texture[...],0.7,Texture[...]]</code>
     */
    public String toString() {
        return "TextureMix[" + c[0] + "," + v[0] + "," + c[1] + "," + v[1] + "]";
    }

}
