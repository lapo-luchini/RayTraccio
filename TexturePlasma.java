/**
 * Materiale sperimentale che mixa due materiali secondo un frattale del tipo 'Plasma'.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TexturePlasma extends Texture {
	/** Array contenente i due sottomateriali */
  private Texture c[];
	/** Rumore di Perlin usato per mixare i due sottomateriali */
  private NoisePerlin perlin;
TexturePlasma(Texture a, Texture d, NoisePerlin n) {
	c = new Texture[2];
	c[0] = a;
	c[1] = d;
	perlin = n;
}
public Color color(Vector p) {
	double r = perlin.noiseAt(p.x, p.y, p.z);
	if (r < 0.0)
		return (Color.RED);
	return (c[0].color(p).mul(r).addU(c[1].color(p).mul(1.0 - r)));
}
public double reflect(Vector p) {
	double r = perlin.noiseAt(p.x, p.y, p.z);
	if (r < 0.0)
		return (0.0);
	return (c[0].reflect(p) * r + c[1].reflect(p) * (1.0 - r));
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[Texture[...],Texture[...],NoisePerlin[...]]</code> <br>
 */
public String toString() {
	return ("TextureNoise[" + c[0] + "," + c[1] + "," + perlin + "]");
}
}
