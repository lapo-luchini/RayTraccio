class TextureMix extends Texture {
  private Texture c[];
  private double v[];
TextureMix(Texture a, double b, Texture d, double e) {
	c = new Texture[2];
	c[0] = a;
	c[1] = d;
	v = new double[2];
	v[0] = b / (b + e);
	v[1] = e / (b + e);
}
public Color color(Vector p) {
	return (c[0].color(p).mul(v[0]).addU(c[1].color(p).mul(v[1])));
}
public double reflect(Vector p) {
	return (c[0].reflect(p) * v[0] + c[1].reflect(p) * v[1]);
}
public String toString() {
	return ("TextureMix[" + c[0] + "," + v[0] + "," + c[1] + "," + v[1] + "]");
}
}
