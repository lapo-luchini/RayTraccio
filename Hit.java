import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
class Hit {
	public boolean h = false; // ha colpito o no?
	public double t; // punto parametrico del raggio
	public Ray r; // raggio generatore
	public Shape3D g; // oggetto generatore
	private Vector p; // [autocalcolato] punto colpito
	private Vector n; // [autocalcolato] normale
	private Color c; // [autocalcolato] colore
Hit(Shape3D a, Ray b) {
	g = a;
	r = b;
}
public void addT(double a) {
	if (a > 1E-10) {
		if (h) {
			if (a < t)
				t = a;
		} else
			t = a;
		h = true;
	}
}
public Color color() {
	if (c == null)
		c = g.color(point());
	return (c);
}
public Vector normal() {
	if (n == null)
		n = g.normal(point());
	return (n);
}
public Vector point() {
	if (p == null)
		p = r.point(t);
	return (p);
}
public double reflect() {
	return (g.reflect(point()));
}
public String toString() {
	return ("Hit[" + h + (h ? ",t:" + t + ",g:" + g + ",r:" + r : "") + "]");
}
}
