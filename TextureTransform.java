class TextureTransform extends Texture {
  private Texture c;
  private TransformMatrix t;
TextureTransform(Texture a, TransformMatrix b) {
	c = a;
	t = b.inv();
}
public Color color(Vector p) {
	return (c.color(p.transform(t)));
}
public double reflect(Vector p) {
	return (c.reflect(p.transform(t)));
}
public String toString() {
	return ("TextureTransform[" + c + "," + t + "]");
}
}
