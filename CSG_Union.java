// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>

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
// along with Foobar; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

// This file is $Revision$, released by $Author$ on $Date$

/**
 * Figura formata dall'unione delle figure della collezione. <br>
 * Esiste quindi solo dove esistono <b>almeno una</b> delle sottofigure.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class CSG_Union extends CSG_Collection {
/**
 * Calcola l'intersezione tra un dato raggio e la figura (questa versione non � ottimizzata).
 * @param a il raggio voluto
 */
public Hit hit(EyeRays a) {
	return (hit((Ray) a));
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
 * @return <code>-1.0</code> all'interno, <code>+1.0</code> altrimenti.
 */
public double value(Vector p) {
	// non sono sicuro che vada
	double a = 1.0;                 // di defualt � esterno
	for (int i = 0; i < n; i++)     // per ogni sottofigura
		if (s[i].value(p) * ot < 0.0) // se questa figura � interna
			a = -1.0;                   // allora � interno
	return (a);
}
}