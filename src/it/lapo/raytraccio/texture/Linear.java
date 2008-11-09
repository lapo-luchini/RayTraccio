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
import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale che mixa due materiali secondo un pattern dato, sfumando dal primo
 * al secondo in modo lineare.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Linear extends Texture {

    /** Array contenente i due sottomateriali */
    private Texture c[];

    /** Pattern usato per mixare i due sottomateriali */
    private Pattern pat;

    public Linear(Texture a, Texture d, Pattern p) {
        c = new Texture[2];
        c[0] = a;
        c[1] = d;
        pat = p;
    }

    public Color color(Vector3D p) {
        double r = pat.scalar(p);
        return c[0].color(p).mul(r).addU(c[1].color(p).mul(1.0 - r));
    }

    public double reflect(Vector3D p) {
        double r = pat.scalar(p);
        return c[0].reflect(p) * r + c[1].reflect(p) * (1.0 - r);
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio:
     * <code>TextureLinear[Pattern[...],Texture[...],Texture[...]]</code><br>
     */
    public String toString() {
        return "TextureLinear[" + pat + "," + c[0] + "," + c[1] + "]";
    }

}
