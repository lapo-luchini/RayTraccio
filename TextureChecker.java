/**
 * Scacchiera 3D di due sottomateriali. <br>
 * Lo spazio viene diviso un cubi unitari alternativamente di due sottomateriali diversi.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureChecker extends Texture {
	/** Array contenente i due sottomateriali */
  private Texture c[];
TextureChecker(Texture a, Texture b) {
	c = new Texture[2];
	c[0] = a;
	c[1] = b;
}
public Color color(Vector p) {
	return (c[ (((int) Math.floor(p.x)) ^ ((int) Math.floor(p.y)) ^ ((int) Math.floor(p.z))) & 1].color(p));
}
public double reflect(Vector p) {
	return (c[ (((int) Math.floor(p.x)) ^ ((int) Math.floor(p.y)) ^ ((int) Math.floor(p.z))) & 1].reflect(p));
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[Texture[...],Texture[...]]</code> <br>
 */
public String toString() {
	return ("TextureChecker[" + c[0] + "," + c[1] + "]");
}
}
