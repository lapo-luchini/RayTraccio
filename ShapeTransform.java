/**
 * Figura virtuale che deforma lo spazio intorno ad una figura data.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class ShapeTransform extends Shape3D {
	/** Figura originale */
	private Shape3D s;
	/** Trasformazione da applicare, oppure <code>null</code> per non applicarne */
	private TransformMatrix t;
	/** Materiale da usare, oppure <code>null</code> per usare quello della figura originale */
	private Texture c;
	/** Valore di overturn (<code>+1</code>=non capovolgere, <code>-1</code>=capovolgi) */
	private int ot=1;
/**
 * Crea un wrapper per l'oggetto passato senza specificare trasformazioni.
 * @param a l'oggetto originale da usare
 */
public ShapeTransform(Shape3D a) {
	s = a;
	t = TransformMatrix.IDENTITY;
	c = null;
}
/**
 * Crea un wrapper per l'oggetto passato applicando una trasformazione.
 * @param a l'oggetto originale da usare
 * @param b la trasformazioneda usare
 */
public ShapeTransform(Shape3D a, TransformMatrix b) {
	s = a;
	t = b.inv();
	c = null;
}
/**
 * Calcola l'intersezione tra un dato raggio e la figura (questa versione non è ottimizzata).
 * @param a il raggio voluto
 */
public Hit hit(EyeRays a) {
	Hit h;
	if(t == TransformMatrix.IDENTITY)
		h=s.hit(a);
	else
		h=new HitTransform(s.hit(a.transform(t)), a, t, c);
	return(h);
}
public Hit hit(Ray a) {
	Hit h;
	if(t == TransformMatrix.IDENTITY)
		h=s.hit(a);
	else
		h=new HitTransform(s.hit(a.transform(t)), a, t, c);
	return(h);
}
public void overturn() {
	ot = -ot;
}
public void rotate(Vector i) {
	t=t.mul(TransformMatrix.RotateZYX(-i.x, -i.y, -i.z));
}
public void scale(Vector i) {
	t=t.mul(TransformMatrix.Scale(1.0/i.x, 1.0/i.y, 1.0/i.z));
}
public void texture(Texture t) {
	c = t;
}
public void translate(Vector i) {
	t=t.mul(TransformMatrix.Translate(-i.x, -i.y, -i.z));
}
public double value(Vector p) {
	if(t == TransformMatrix.IDENTITY)
		return(ot*s.value(p));
	else
		return(ot*s.value(t.transformVector(p)));
}
}
