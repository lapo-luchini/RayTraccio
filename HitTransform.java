/**
 * Intersezione speciale usata per deformare lo spazio.
 * @see ShapeTransform
 * @see TransformMatrix
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class HitTransform extends Hit {
	/** Trasformazione da usare (se <code>null</code> non viene applicata trasformazione) */
	private TransformMatrix tm;
	/** Determinante(tm)^(-1/3) usato in {@link normal() normal()} */
	private double tmid;
	/** {@link Texture Texture} da usare (se <code>null</code> viene usata quella dell'oggetto originale) */
	private Texture tx;
	/** Vettore posizione del punto dell'intersezione nello spazio trasformato (autocalcolato al primo richiamo di {@link pointTransform() pointTransform()}) */
	protected Vector pt;
/**
 * Prepara un nuovo oggetto {@link HitTransform HitTransform} deformando lo spazio di un {@link Hit Hit} già esistente.
 * @param h {@link Hit Hit} da deformare
 * @param r vero raggio da usare
 * @param tm matrice di trasformazione da applicare (se <code>null</code> non viene applicata trasformazione)
 * @param tx {@link Texture Texture} da usare (se <code>null</code> viene usata quella dell'oggetto originale)
 */
HitTransform(Hit h, Ray r, TransformMatrix tm, Texture tx) {
	super(h.g, r);
	this.h = h.h;
	this.t = h.t;
	this.tm = tm;
	if (tm != null)
		this.tmid = Math.pow(tm.det(), -1.0 / 3.0);
	this.tx = tx;
}
public Color color() {
	if (c == null) {
		if (tx == null)
			c = g.color(pointTransform());
		else
			c = tx.color(point());
	}
	return (c);
}
public Vector normal() {
	if (n == null)
		n = tm.transformNormal(g.normal(pointTransform())).mul(tmid);
	return (n);
}
public Vector pointTransform() {
	if (pt == null)
		pt = tm.transformVector(point());
	return (pt);
}
public double reflect() {
	if (Double.isNaN(rr))
		if (tx == null)
			rr = g.reflect(pointTransform());
		else
			rr = tx.reflect(point());
	return (rr);
}
}
