// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

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
