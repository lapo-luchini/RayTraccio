/**
 * Figura formata dall'intersezione delle figure della collezione. <br>
 * Esiste quindi solo dove esistono <b>tutte</b> le sottofigure.
 */
class CSG_Intersection extends CSG_Collection {
/**
 * Calcola l'intersezione tra un dato raggio e la figura (questa versione non è ottimizzata).
 * @param a il raggio voluto
 */
public Hit hit(EyeRays a) {
	Hit l = new Hit(this, a), z;
	int i;
	for (i = 0; i < n; i++) {
		z = s[i].hit(a);
		if (z.h)
			if (z.t > 1E-10)
				if ((z.t < l.t) || (!l.h)) { // è un hit più vicino, ora controllo se tutti gli altri sono "dentro"
					boolean v = true;
					int i2;
					for (i2 = 0; (i2 < n) && v; i2++)
						if (i2 != i)
							if (s[i2].value(z.point()) > 0.0)
								v = false;
					if (v)
						l = z;
				}
	}
	return (l);
}
public Hit hit(Ray a) {
	Hit l = new Hit(this, a), z;
	int i;
	for (i = 0; i < n; i++) {
		z = s[i].hit(a);
		if (z.h)
			if (z.t > 1E-10)
				if ((z.t < l.t) || (!l.h)) { // è un hit più vicino, ora controllo se tutti gli altri sono "dentro"
					boolean v = true;
					int i2;
					for (i2 = 0; (i2 < n) && v; i2++)
						if (i2 != i)
							if (s[i2].value(z.point()) > 0.0)
								v = false;
					if (v)
						l = z;
				}
	}
	return (l);
}
/**
 * Valore della funzione generatrice della figura. <br>
 * @return <code>+1.0</code> all'esterno, <code>-1.0</code> altrimenti.
 */
public double value(Vector p) {
	// non sono sicuro che vada
	double a = -1.0; // di default è interno
	for (int i = 0; i < n; i++)
		if (s[i].value(p) * ot > 0.0)
			a = 1.0;
	return (a);
}
}
