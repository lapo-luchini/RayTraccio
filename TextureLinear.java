/**
 * Materiale che mixa due materiali secondo un pattern dato.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureLinear extends Texture {
	/** Array contenente i due sottomateriali */
  private Texture c[];
	/** Pattern usato per mixare i due sottomateriali */
  private Pattern pat;
TextureLinear(Texture a, Texture d, Pattern p) {
	c = new Texture[2];
	c[0] = a;
	c[1] = d;
	pat = p;
}
public Color color(Vector p) {
	double r = pat.scalar(p);
	return (c[0].color(p).mul(r).addU(c[1].color(p).mul(1.0 - r)));
}
public double reflect(Vector p) {
	double r = pat.scalar(p);
	return (c[0].reflect(p) * r + c[1].reflect(p) * (1.0 - r));
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureLinear[Pattern[...],Texture[...],Texture[...]]</code> <br>
 */
public String toString() {
	return ("TextureLinear[" + pat + "," + c[0] + "," + c[1] + "]");
}
}
