/**
 * Campo scalare che vale <code>0.0</code> e <code>1.0</code> alternativamente in strato unitario lungo l'asse X.
 * @author: Lapo Luchini
 */
class PatternStripes extends Pattern {
public double scalar(Vector p) {
	return(((int)Math.floor(p.x))&1);
}
public double[] vectorial(Vector p, byte dim) {
	double[] v=new double[dim];
	double nv=((int)Math.floor(p.x))&1;
	while(dim-->0)
		v[dim]=nv;
	return(v);
}
}
