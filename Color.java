/** Colore RGB con precisione <code>double</code>. <br>
  * I tre valori variano da <code>0.0</code> a <code>1.0</code>. <br>
  * I valori negativi vengono annullati.
  */
class Color {
	/** Componente Red */
	protected double r;
	/** Componente Green */
	protected double g;
	/** Componente Blue */
	protected double b;
	/** Colore (0, 0, 0) nero */
	public static Color BLACK = new Color(0.0, 0.0, 0.0);
	/** Colore (1, 0, 0) rosso */
	public static Color RED = new Color(1.0, 0.0, 0.0);
	/** Colore (0, 1, 0) verde */
	public static Color GREEN = new Color(0.0, 1.0, 0.0);
	/** Colore (0, 0, 1) blu */
	public static Color BLUE = new Color(0.0, 0.0, 1.0);
	/** Colore (1, 1, 0) giallo */
	public static Color YELLOW = new Color(1.0, 1.0, 0.0);
	/** Colore (0, 1, 1) ciano */
	public static Color CYAN = new Color(0.0, 1.0, 1.0);
	/** Colore (1, 0, 1) viola */
	public static Color PURPLE = new Color(1.0, 0.0, 1.0);
	/** Colore (1, 1, 1) bianco */
	public static Color WHITE = new Color(1.0, 1.0, 1.0);
/**
 * Crea un colore date le componenti.
 * @param r componente Red
 * @param g componente Green
 * @param b componente Blue
 */
Color(double r, double g, double b) {
	this.r = r;
	this.g = g;
	this.b = b;
	this.check();
}
/**
 * Crea un colore copiandolo da un altro.
 * @param a colore da usare
 */
Color(Color a) {
	this.r = a.r;
	this.g = a.g;
	this.b = a.b;
}
/**
 * Somma di colori.
 * @param a colore da usare
 * @return un nuovo oggetto
 */
public Color add(Color a) {
	return (new Color(r + a.r, g + a.g, b + a.b));
}
/**
 * Somma di colori.
 * @param a colore da usare
 * @return questo stesso oggetto
 */
public Color addU(Color a) {
	r += a.r;
	g += a.g;
	b += a.b;
	return (this);
}
/**
 * Valida il colore. <br>
 * Le componenti negative vengono azzerate.
 */
public void check() {
	if (this.r < 0.0)
		this.r = 0.0;
	if (this.g < 0.0)
		this.g = 0.0;
	if (this.b < 0.0)
		this.b = 0.0;
}
/**
 * Trasforma il colore in formato 32 bit ARGB (Alpha, Red, Green, Blue). <br>
 * Il valore di Alpha è fissato a 255.
 * @return colore in formato ARGB
 */
public int getARGB() {
	return (0xFF000000 | getRGB());
}
/**
 * Trasforma il colore in formato 24 bit RGB (Red, Green, Blue). <br>
 * Gli 8 bit più significativi sono fissati a zero.
 * @return colore in formato RGB
 */
public int getRGB() {
	int u = 0, r = (int) (this.r * 255), g = (int) (this.g * 255), b = (int) (this.b * 255);
	if (r > 0)
		if (r < 255)
			u = r;
		else
			u = 255;
	u = u << 8;
	if (g > 0)
		if (g < 255)
			u |= g;
		else
			u |= 255;
	u = u << 8;
	if (b > 0)
		if (b < 255)
			u |= b;
		else
			u |= 255;
	return (u);
}
/**
 * Moltiplicazione componente-per-costante del colore. <br>
 * Valori negativi del parametro generano un avvertimento su <code>System.err</code>.
 * @param a costante da usare
 * @return un nuovo oggetto
 */
public Color mul(double a) {
	if (a < 0.0)
		System.err.println("Invalid parameter 'a' [" + a + "]: it must be >= 0.0");
	return (new Color(r * a, g * a, b * a));
}
/**
 * Moltiplicazione componente-per-componente di colori.
 * @param a colore da usare
 * @return un nuovo oggetto
 */
public Color mul(Color a) {
	return (new Color(r * a.r, g * a.g, b * a.b));
}
/**
 * Moltiplicazione componente-per-costante del colore.
 * Valori negativi del parametro generano un avvertimento su <code>System.err</code>.
 * @param a costante da usare
 * @return questo stesso oggetto
 */
public Color mulU(double a) {
	if (a < 0.0)
		System.err.println("Invalid parameter 'a' [" + a + "]: it must be >= 0.0");
	r *= a;
	g *= a;
	b *= a;
	return (this);
}
/**
 * Moltiplicazione componente-per-componente di colori.
 * @param a colore da usare
 * @return questo stesso oggetto
 */
public Color mulU(Color a) {
	r *= a.r;
	g *= a.g;
	b *= a.b;
	return (this);
}
/**
 * Sottrazione di colori.
 * @param a colore da usare
 * @return un nuovo oggetto
 */
public Color sub(Color a) {
	return (new Color(r - a.r, g - a.g, b - a.b));
}
/**
 * Sottrazione di colori.
 * @param a colore da usare
 * @return questo stesso oggetto
 */
public Color subU(Color a) {
	r -= a.r;
	g -= a.g;
	b -= a.b;
	if (r < 0.0)
		r = 0.0;
	if (g < 0.0)
		g = 0.0;
	if (b < 0.0)
		b = 0.0;
	return (this);
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>Color[0.1,0.23,1.0]</code>
 */
public String toString() {
	return ("Color[" + r + "," + g + "," + b + "]");
}
}
