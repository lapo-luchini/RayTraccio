class TextureScale extends Texture {
  private Texture c;
  private double z;
TextureScale(Texture a, double b) {
	c = a;
	z = 1.0 / b;
}
public Color color(Vector p) {
	return (c.color(p.mul(z)));
}
public double reflect(Vector p) {
	return (c.reflect(p.mul(z)));
}
public String toString() {
	return ("TextureScale[" + c + "," + 1.0 / z + "]");
}
}
