/**
 * Materiale ottenuto trasformandone un'altro.
 */
class TextureTransform extends Texture {
	/** Materiale da ridimensionare */
  private Texture c;
	/** Matrice di trasformazione */
  private TransformMatrix t;
TextureTransform(Texture a, TransformMatrix b) {
	c = a;
	t = b.inv();
}
public Color color(Vector p) {
	return (c.color(t.transformVector(p)));
}
public double reflect(Vector p) {
	return (c.reflect(t.transformVector(p)));
}
public String toString() {
	return ("TextureTransform[" + c + "," + t + "]");
}
}
