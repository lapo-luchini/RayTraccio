class CSG_Union extends CSG_Collection {
// non ottimizzato, ma conserva la tipo
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
public double value(Vector p) {
	// non sono sicuro che vada
	double a = 1.0; // di defualt è esterno
	for (int i = 0; i < n; i++)
		if (s[i].value(p) * ot < 0.0)
			a = -1.0;
	return (a);
}
}
