/**
 * Modifica un'alto pattern invertendo il suo range <code>(1.0-x)</code>.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternSine extends Pattern {
	// pattern da modificare
	Pattern pat;
public PatternSine(Pattern p) {
	pat=p;
}
public double scalar(Vector p) {
	return(0.5+0.5*Math.sin(Math.PI*(pat.scalar(p)-0.5)));
}
public double[] vectorial(Vector p, byte dim) {
	double u[]=pat.vectorial(p, dim);
	while(dim-->0)
		u[dim]=Math.sin(Math.PI*u[dim]);
	return(u);
}
}
