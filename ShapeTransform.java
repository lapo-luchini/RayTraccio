/**
 * Inefficent but general object Transorm wrapper.
 * Creation date: (10/02/2001 18.55.16)
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class ShapeTransform extends Shape3D {
	private Shape3D s;
	private TransformMatrix t, ti;
	private int ot=1;
ShapeTransform(Shape3D a) {
	s = a;
	t = ti = TransformMatrix.IDENTITY;
}
ShapeTransform(Shape3D a, TransformMatrix b) {
	s = a;
	t = b.inv();
	ti = b;
}
/**
 * color method comment.
 */
public Color color(Vector p) {
	return(s.color(p.transform(t)));
	//return(s.color(p));
}
/**
 * hit method comment.
 */
public Hit hit(EyeRays a) {
	//return(s.hit(a.transform(t)));
	return(hit((Ray)a));
}
/**
 * hit method comment.
 */
public Hit hit(Ray a) {
	//return();
	Hit h=s.hit(a.transform(t));
	h.g=this;
	h.r=a;
	return(h);
}
/**
 * normal method comment.
 */
public Vector normal(Vector p) {
	return(s.normal(p.transform(t)));
	//return(s.normal(p));
}
/**
 * overturn method comment.
 */
public void overturn() {
	ot = -ot;
}
/**
 * reflect method comment.
 */
public double reflect(Vector p) {
	return(s.reflect(p.transform(t)));
}
/**
 * scale method comment.
 */
public void scale(Vector i) {
	t=t.mul(TransformMatrix.Scale(1.0/i.x, 1.0/i.y, 1.0/i.z));
	ti=t.inv();
}
/**
 * translate method comment.
 */
public void translate(Vector i) {
	t=t.mul(TransformMatrix.Translate(-i.x, -i.y, -i.z));
	ti=t.inv();
}
/**
 * value method comment.
 */
public double value(Vector p) {
	return(ot*s.value(p.transform(t)));
}
}
