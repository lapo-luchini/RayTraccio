/** Colore RGB.
  */
class Color {
	protected double r;
	protected double g;
	protected double b;
	public static Color BLACK =  new Color(0.0, 0.0, 0.0);
	public static Color RED =    new Color(1.0, 0.0, 0.0);
	public static Color GREEN =  new Color(0.0, 1.0, 0.0);
	public static Color BLUE =   new Color(0.0, 0.0, 1.0);
	public static Color YELLOW = new Color(1.0, 1.0, 0.0);
	public static Color CYAN =   new Color(0.0, 1.0, 1.0);
	public static Color PURPLE = new Color(1.0, 0.0, 1.0);
	public static Color WHITE =  new Color(1.0, 1.0, 1.0);
Color(double r, double g, double b) {
	this.r = r;
	this.g = g;
	this.b = b;
	this.check();
}
Color(Color a) {
	this.r = a.r;
	this.g = a.g;
	this.b = a.b;
}
public Color add(Color a) {
	return (new Color(r + a.r, g + a.g, b + a.b));
}
public Color addU(Color a) {
	r += a.r;
	g += a.g;
	b += a.b;
	return (this);
}
public void check() {
	if (this.r < 0.0)
		this.r = 0.0;
	if (this.g < 0.0)
		this.g = 0.0;
	if (this.b < 0.0)
		this.b = 0.0;
}
public int getARGB() {
	return (0xFF000000 | getRGB());
}
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
public Color mul(double a) {
	if (a < 0.0)
		//throw (new java.security.InvalidParameterException("a [" + a + "] must be >= 0.0"));
		System.err.println("Color multiplied by "+a);
	return (new Color(r * a, g * a, b * a));
}
public Color mul(Color a) {
	return (new Color(r * a.r, g * a.g, b * a.b));
}
public Color mulU(double a) {
	if (a < 0.0)
		//throw (new java.security.InvalidParameterException("a [" + a + "] must be >= 0.0"));
		System.err.println("Color multiplied by "+a);
	r *= a;
	g *= a;
	b *= a;
	return (this);
}
public Color mulU(Color a) {
	r *= a.r;
	g *= a.g;
	b *= a.b;
	return (this);
}
public Color sub(Color a) {
	return (new Color(r - a.r, g - a.g, b - a.b));
}
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
public String toString() {
	return ("Color[" + r + "," + g + "," + b + "]");
}
}
