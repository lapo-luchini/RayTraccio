/**
 * Materiale di colore e coefficente di riflessione uniformi.
 */
class TexturePlain extends Texture {
	/** Colore */
  private Color c;
	/** Coefficente di riflessione */
  private double r;
	/** Texture standard bianca non riflettente */
  public static TexturePlain WHITE = new TexturePlain(Color.WHITE, 0.0);
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
