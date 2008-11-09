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

package it.lapo.raytraccio;

/**
 * Luce puntiforme.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Light {

    /** Vettore posizione */
    protected Vector3D o;

    /** Colore della luce (alla distanza <code>p</code>) */
    protected Color c;

    /**
     * Potenza della luce (distanza alla quale usare il colore <code>c</code>
     * invariato)
     */
    protected double p;

    /**
     * Crea una luce puntiforme cno parametri dati. <br>
     *
     * @param o Vettore posizione
     * @param c Colore
     * @param p Potenza
     */
    public Light(Vector3D o, Color c, double p) {
        this.o = o;
        this.c = c;
        this.p = p;
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio: <code>Light[Vector3D[1.0,2.0,3.0],Color[1.0,0.0,0.0],5.0]</code>
     * <br>
     */
    public String toString() {
        return ("Light[" + o + "," + c + "," + p + "]");
    }

}
