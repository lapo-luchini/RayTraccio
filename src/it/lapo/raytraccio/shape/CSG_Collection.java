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

package it.lapo.raytraccio.shape;

import it.lapo.raytraccio.Shape3D;

/**
 * Figura astratta formata da un insieme di altre figure.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract public class CSG_Collection extends Shape3D {

    /** Array di figure */
    protected Shape3D[] s = new Shape3D[2];

    /** Numero di figure contenute nell'array */
    protected int n = 0;

    /**
     * Valore di overturn (<code>+1</code> = non capovolgere, <code>-1</code>
     * = capovolgi)
     */
    protected int ot = 1;

    /**
     * Aggiunge una figura alla collezione. <br>
     * Le figure sono contenute in un array la cui lunghezza è aumentata in modo
     * dinamico per ottimizzare spazio occupato e velocità di aggiunta (in caso
     * manchi spazio l'array viene aumentato del 50%+1).
     *
     * @param a figura da aggiungere
     */
    public void add(Shape3D a) {
        if (n == s.length) {
            Shape3D old[] = s;
            s = new Shape3D[(s.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
            System.arraycopy(old, 0, s, 0, n);
        }
        s[n++] = a;
    }

    /**
     * Ottimizza l'occupazione di memoria (da usare dopo aver aggiunto tutti i
     * valori).
     */
    public void optimize() {
        if (s.length > n) {
            Shape3D old[] = s;
            s = new Shape3D[n];
            System.arraycopy(old, 0, s, 0, n);
        }
    }

    public void overturn() {
        ot = -ot;
    }

    public String toString() {
        return "CSG_Collection[" + n + " shapes]";
    }

}
