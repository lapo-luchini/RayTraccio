/**
 * Figura formata dall'intersezione delle figure della collezione. <br>
 * Esiste quindi solo dove esistono <b>tutte</b> le sottofigure.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class CSG_Difference extends CSG_Collection {
/**
 * Calcola l'intersezione tra un dato raggio e la figura (questa versione non � ottimizzata).
 * @param a il raggio voluto
 */
public Hit hit(EyeRays a) {
	return (hit((Ray) a));
}
public Hit hit(Ray a) {
	Hit l, z;
	int i, i2;
	boolean v;
	Ray a2;
	if (n > 0) {
		l=s[0].hit(a);
		for (i = 1; (l.h) && (i < n); i++) {
			if(s[i].value(l.point()) < 0.0) {      // se questo oggetto � "dentro"
				l.h = false;                          // allora quell'hit non � pi� valido
				a2 = new Ray(a);                      // fai un nuovo raggio identico a questo
				a2.o = l.point();                     // ma lo fa partire da questo hit
				z = s[i].hit(a2);                     // cerca il "foro d'uscita"
				if (z.h)                              // se � un hit
					if (z.t > 1E-10)                     // se � "davanti"
						if (s[0].value(z.point()) < 0.0) {  // se l'oggetto principale � ancora "dentro"
							z.t += l.t;                        // aggiusta la distanza sul raggio originale
							l = new HitTransform(z, a, null, null, true); // allora � il nuovo hit
						}
			}
		}
	} else
		l = new Hit(this, a);
	return (l);
}
/**
 * Valore della funzione generatrice della figura. <br>
 * Non gestisce il capovolgimento (overturn). <br>
 * @return <code>+1.0</code> all'esterno, <code>-1.0</code> altrimenti.
 */
public double value(Vector p) {
	// non sono sicuro che vada
	if (n < 1)
		return (1.0);
	double a = s[0].value(p);
	if (a < 0.0)
		for (int i = 1; i < n; i++)
			if (s[i].value(p) < 0.0)
				a = 1.0;
	return (a);
}
}
