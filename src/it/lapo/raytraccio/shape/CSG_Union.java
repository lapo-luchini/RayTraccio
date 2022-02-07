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

import it.lapo.raytraccio.EyeRays;
import it.lapo.raytraccio.Hit;
import it.lapo.raytraccio.Ray;
import it.lapo.raytraccio.Vector3D;

/**
 * Figura formata dall'unione delle figure della collezione. <br>
 * Esiste quindi solo dove esistono <b>almeno una </b> delle sottofigure.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class CSG_Union extends CSG_Collection {

    /**
     * Calcola l'intersezione tra un dato raggio e la figura (questa versione
     * non è ottimizzata).
     *
     * @param a il raggio voluto
     */
    public Hit hit(EyeRays a) {
        Hit l = s[0].hit(a), z;
        int i;
        for (i = 1; i < n; i++) {
            z = s[i].hit(a);
            if (z.h)
                if (z.t > 1E-10)
                    if ((z.t < l.t) || (!l.h))
                        l = z;
        }
        return (l);
    }

    public Hit hit(Ray a) {
        Hit l = s[0].hit(a), z;
        int i;
        for (i = 1; i < n; i++) {
            z = s[i].hit(a);
            if (z.h)
                if (z.t > 1E-10)
                    if ((z.t < l.t) || (!l.h))
                        l = z;
        }
        return (l);
    }

    /**
     * Valore della funzione generatrice della figura. <br>
     *
     * @return <code>-1.0</code> all'interno, <code>+1.0</code> altrimenti.
     */
    public double value(Vector3D p) {
        // non sono sicuro che vada
        double a = 1.0; // di defualt è esterno
        for (int i = 0; i < n; i++)
            // per ogni sottofigura
            if (s[i].value(p) * ot < 0.0) // se questa figura è interna
                a = -1.0; // allora è interno
        return (a);
    }

}
