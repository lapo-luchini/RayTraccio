// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.shape;

import it.lapo.raytraccio.EyeRays;
import it.lapo.raytraccio.Hit;
import it.lapo.raytraccio.Ray;
import it.lapo.raytraccio.Vector3D;

/**
 * Figura formata dall'intersezione delle figure della collezione. <br>
 * Esiste quindi solo dove esistono <b>tutte </b> le sottofigure.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class CSG_Intersection extends CSG_Collection {

    /**
     * Calcola l'intersezione tra un dato raggio e la figura (questa versione
     * non è ottimizzata).
     *
     * @param a il raggio voluto
     */
    public Hit hit(EyeRays a) {
        Hit l = new Hit(this, a), z;
        int i, i2;
        boolean v;
        for (i = 0; i < n; i++) {
            z = s[i].hit(a);
            if (z.h)
                if (z.t > 1E-10)
                    if ((z.t < l.t) || (!l.h)) { // è un hit più vicino, ora controllo se tutti gli altri sono "dentro"
                        v = true;
                        for (i2 = 0; (i2 < n) && v; i2++)
                            if (i2 != i)
                                if (s[i2].value(z.point()) > 0.0)
                                    v = false;
                        if (v)
                            l = z;
                    }
        }
        return l;
    }

    public Hit hit(Ray a) {
        Hit l = new Hit(this, a), z;
        int i, i2;
        boolean v;
        for (i = 0; i < n; i++) {
            z = s[i].hit(a);
            if (z.h)
                if (z.t > 1E-10)
                    if ((z.t < l.t) || (!l.h)) { // è un hit più vicino, ora controllo se tutti gli altri sono "dentro"
                        v = true;
                        for (i2 = 0; (i2 < n) && v; i2++)
                            if (i2 != i)
                                if (s[i2].value(z.point()) > 0.0)
                                    v = false;
                        if (v)
                            l = z;
                    }
        }
        return l;
    }

    /**
     * Valore della funzione generatrice della figura. <br>
     *
     * @return <code>+1.0</code> all'esterno, <code>-1.0</code> altrimenti.
     */
    public double value(Vector3D p) {
        // non sono sicuro che vada
        double a = -1.0; // di default è interno
        for (int i = 0; i < n; i++)
            // per ogni sittifigura
            if (s[i].value(p) * ot > 0.0) // se questa sottofigura è esterna
                a = 1.0; // allora è esterno
        return (a);
    }

}
