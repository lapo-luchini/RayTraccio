/**
 * Modifica un'alto pattern perturbandolo (applica una turbolenza pseudocasuale).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternTurbulence extends Pattern {
	/** {@link Pattern} da perturbare */
	protected Pattern pat;
	/** {@link Pattern} usato per perturbare */
	protected Pattern n;
	/** Turbolenza della perturbazione nella direzione X */
	protected double tx;
	/** Turbolenza della perturbazione nella direzione Y */
	protected double ty;
	/** Turbolenza della perturbazione nella direzione Z */
	protected double tz;
/**
 * Aplpica una turbulenza a un'altro pattern.
 * @param pattern pattern da usare come base
 * @param noise rumore da usare come turbulenza
 * @param turbulence quanto il rumore perturba il pattern (anisotropo)
 */
public PatternTurbulence(Pattern pattern, Pattern noise, double turbulence) {
	pat = pattern;
	n = noise;
	tx = ty = tz = turbulence;
}
/**
 * Aplpica una turbulenza a un'altro pattern.
 * @param pattern pattern da usare come base
 * @param noise rumore da usare come turbulenza
 * @param turbulence quanto il rumore perturba il pattern (non anisotropo)
 */
public PatternTurbulence(Pattern pattern, Pattern noise, Vector turbulence) {
	pat = pattern;
	n = noise;
	tx = turbulence.x;
	ty = turbulence.y;
	tz = turbulence.z;
}
public double scalar(Vector p) {
	double vn[]=n.vectorial(p, (byte)3);
	return(pat.scalar(new Vector(p.x+tx*vn[0], p.y+ty*vn[1], p.z+tz*vn[2])));
}
public double[] vectorial(Vector p, byte dim) {
	double vn[]=n.vectorial(p, (byte)3);
	return(pat.vectorial(new Vector(p.x+tx*vn[0], p.y+ty*vn[1], p.z+tz*vn[2]), dim));
}
}
