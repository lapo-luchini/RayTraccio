/**
 * Campo scalare che vale <code>0.0</code> e <code>1.0</code> alternativamente in strato unitario lungo l'asse Y.
 * @author: Lapo Luchini
 */
class PatternSawtooth extends Pattern {
public double scalar(Vector p) {
	return(p.x-Math.floor(p.x));
}
public double[] vectorial(Vector p, byte dim) {
	double[] v=new double[dim];
	double nv=p.x-Math.floor(p.x);
	while(dim-->0)
		v[dim]=nv;
	return(v);
}
}
