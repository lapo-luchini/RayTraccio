class TextureStripes extends Texture {
  private Texture c[];
TextureStripes(Texture a, Texture b) {
	c = new Texture[2];
	c[0] = a;
	c[1] = b;
}
public Color color(Vector p) {
	return (c[ ((int) Math.floor(p.y)) & 1].color(p));
}
public double reflect(Vector p) {
	return (c[ ((int) Math.floor(p.y)) & 1].reflect(p));
}
public String toString() {
	return ("TextureStripes[" + c[0] + "," + c[1] + "]");
}
}
