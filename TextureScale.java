/**
 * Materiale ottenuto ridimensionandone un'altro.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureScale extends Texture {
	/** Materiale da ridimensionare */
  private Texture c;
  /** Valore di ridimensionamento */
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
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[Texture[...],3.0]</code> <br>
 */
public String toString() {
	return ("TextureScale[" + c + "," + 1.0 / z + "]");
}
}
