/**
 * Strati alternati di due sottomateriali. <br>
 * Lo spazio viene diviso in strati di spessore unitario lungo l'asse Y alternativamente di due sottomateriali diversi.
 */
class TextureStripes extends Texture {
	/** Array contenente i due sottomateriali */
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
