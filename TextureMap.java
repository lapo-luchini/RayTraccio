/**
 * Materiale che utilizza un {@link Pattern} per scegliere colori da una {@link Colormap}.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureMap extends Texture {
	/** Pattern usato per come valore nella {@link Colormap} */
  private Pattern pat;
  /** Array di valori */
	private double v[];
	/** Array di sottotexture */
	private Texture c[];
	/** Numero di elementi nei due array */
	private int n;
/**
 * Crea una mappa con due posti vuoti.
 */
public TextureMap(Pattern p) {
	pat = p;
	v = new double[2];
	c = new Texture[2];
	n = 0;
}
/**
 * Crea una mappa con alcuni posti vuoti.
 * @param min numero di posti da creare
 */
public TextureMap(Pattern p, int num) {
	pat = p;
	v = new double[num];
	c = new Texture[num];
	n = 0;
}
/**
 * Insert the method's description here.
 * Creation date: (21/04/2001 19:26:41)
 * @return int
 * @param value double
 * @param color Color
 */
public void add(double value, Texture tex) {
	if (n == v.length) {
		double ov[] = v;
		Texture oc[] = c;
		v = new double[ (v.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
		c = new Texture[v.length];
		System.arraycopy(ov, 0, v, 0, n);
		System.arraycopy(oc, 0, c, 0, n);
	}
	if (n > 0)
		if (value < v[n - 1])
			throw new RuntimeException("value must be >= of the previous value");
	if ((value < 0.0) || (value > 1.0))
		throw new RuntimeException("value must be in the [0.0-1.0] range");
	v[n] = value;
	c[n++] = tex;
}
public Color color(Vector p) {
	double d = pat.scalar(p);
	if (d <= v[0])
		return (c[0].color(p));
	int i = 1;
	while ((i < n) && (d > v[i]))
		i++;
	if (i == n)
		return (c[n - 1].color(p));
	d = (d - v[i - 1]) / (v[i] - v[i - 1]);
	return (c[i - 1].color(p).mul(1.0 - d).addU(c[i].color(p).mul(d)));
}
/**
 * Ottimizza l'occupazione di memoria (da usare dopo aver aggiunto tutti i valori).
 */
public void optimize() {
	if (v.length > n) {
		double ov[] = v;
		Texture oc[] = c;
		v = new double[n];
		c = new Texture[n];
		System.arraycopy(ov, 0, v, 0, n);
		System.arraycopy(oc, 0, c, 0, n);
	}
}
public double reflect(Vector p) {
	double d = pat.scalar(p);
	if (d <= v[0])
		return (c[0].reflect(p));
	int i = 1;
	while ((i < n) && (d > v[i]))
		i++;
	if (i == n)
		return (c[n - 1].reflect(p));
	d = (d - v[i - 1]) / (v[i] - v[i - 1]);
	return (c[i - 1].reflect(p) * (1.0 - d) + c[i].reflect(p) * d);
}
}
