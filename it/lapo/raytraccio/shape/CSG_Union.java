// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/it/lapo/raytraccio/shape/CSG_Union.java,v 1.1 2004/12/16 23:09:56 lapo Exp $

// This file is part of RayTraccio.
//
// RayTraccio is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// RayTraccio is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RayTraccio; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

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
