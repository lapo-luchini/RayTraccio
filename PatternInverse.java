/**
 * Modifica un'alto pattern invertendo il suo range <code>(1.0-x)</code>.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternInverse extends Pattern {
	// pattern da modificare
	Pattern pat;
public PatternInverse(Pattern p) {
	pat=p;
}
/**
 * scalar method comment.
 */
public double scalar(Vector p) {
	return(1.0-pat.scalar(p));
}
/**
 * vectorial method comment.
 */
public double[] vectorial(Vector p, byte dim) {
	double u[]=pat.vectorial(p, dim);
	while(dim-->0)
		u[dim]=1.0-u[dim];
	return(u);
}
}
