/**
 * Modifica un'alto pattern modificando il suo range comprimendolo sulla parte alta <code>sin((PI/2)*x)</code>.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternHalfSine extends Pattern {
	// pattern da modificare
	Pattern pat;
	public final static double PI2 = 1.5707963267948966192313216916397514420985847;
public PatternHalfSine(Pattern p) {
	pat=p;
}
public double scalar(Vector p) {
	return(Math.sin(PI2*pat.scalar(p)));
}
public double[] vectorial(Vector p, byte dim) {
	double u[]=pat.vectorial(p, dim);
	while(dim-->0)
		u[dim]=Math.sin(PI2*u[dim]);
	return(u);
}
}
