class TexturePlain extends Texture {
  private Color c;
  private double r;
TexturePlain(Color a, double b) {
	c = a;
	r = b;
}
public Color color(Vector p) {
	return (c);
}
public double reflect(Vector p) {
	return (r);
}
public String toString() {
	return ("TexturePlain[" + c + "," + r + "]");
}
}
