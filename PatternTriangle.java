/**
 * Modifica un'alto pattern modificando il suo range <code>1.0-2.0*abs(x-0.5)</code>.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternTriangle extends Pattern {
	// pattern da modificare
	Pattern pat;
public PatternTriangle(Pattern p) {
	pat=p;
}
public double scalar(Vector p) {
	double u=pat.scalar(p);
	if(u>0.5)
		u=1.0-u;
	u*=2;
	return(u);
}
public double[] vectorial(Vector p, byte dim) {
	double u[]=pat.vectorial(p, dim);
	while(dim-->0) {
		if(u[dim]>0.5)
			u[dim]=1.0-u[dim];
		u[dim]*=2;
	}
	return(u);
}
}
