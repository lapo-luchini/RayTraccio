/**
 * Modifica un'alto pattern modificando il suo range comprimendolo agli estremi <code>0.5+0.5*sin((PI/2)*(x-0.5))</code>.
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
