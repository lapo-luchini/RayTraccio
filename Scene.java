import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/** Singola scena da visualizzare.
  * La scena contiene un solo oggetto (questo non è un limite in quanto esiste l'oggetto CSG_Union)
  */
class Scene {
	private Shape3D s;
	private Light l[] = new Light[3];
	private int numl = 0;
	protected Vector eye, c, h, v;
/** Crea una scena dati gli elementi.
  * @params a oggetto contenuto nella scena
  * @params ex posizione dell'occhio
  * @params ox (???)
  * @params hx (???)
  * @params ratio (???)
  */
Scene(Shape3D a, Vector ex, Vector ox, Vector hx, double ratio) {
	s = a;
	eye = ex;
	c = ox;
	if (hx.dot(ox.sub(ex)) != 0.0)
		h = hx.sub(ox.sub(ex).mul(1.0 / (hx.dot(ox.sub(ex)))));
	else
		h = hx;
	v = ox.sub(ex).versU().cross(h).mulU(1.0 / ratio);
	//System.out.println("SCENE INIT");
	//System.out.println("E: "+eye);
	//System.out.println("C: "+c);
	//System.out.println("H: "+hx+" DOT:"+(1.0/hx.dot(ox.sub(ex)))+" -> "+h);
	//System.out.println("V: "+v);
}
public void addLight(Light a) {
	if (numl == l.length) {
		Light old[] = l;
		l = new Light[ (l.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
		System.arraycopy(old, 0, l, 0, numl);
	}
	l[numl++] = a;
}
// non ottimizzato, ma conserva il tipo
public Color hit(EyeRays a) {
	Hit h = s.hit(a);
	Color c;
	if (!h.h)
		c = new Color(Color.BLACK); // mettere qua il cielo
	else {
		if (h.reflect() > 0.999)
			c = new Color(Color.BLACK);
		else
			c = lighting(h.point(), h.normal());
		if (h.reflect() > 0.001) {
			Color nc = Color.BLACK; // anche qua il cielo
			Ray rr = new Ray(h.point(), Vector.ORIGIN /*una cosa a caso tanto c lo sovrascrivo*/);
			rr.c = a.c.mirror(h.normal()); // sovrascrivo c: ok, ok, è un po' sporco!!
			Hit hs = s.hit(rr);
			if (hs.h && (hs.t > 1E-10))
				nc = hs.color().mul(lighting(hs.point(), hs.normal()));
			c.mulU(1.0 - h.reflect()).addU(nc.mul(h.reflect()));
		}
		c.mulU(h.color()); // messo dopo per filtrare anche lo specchio
	}
	return (c);
}
public Color hit(Ray a) {
	Hit h = s.hit(a);
	Color c;
	if (!h.h)
		c = new Color(Color.BLACK); // mettere qua il cielo
	else {
		if (h.reflect() > 0.999)
			c = new Color(Color.BLACK);
		else
			c = lighting(h.point(), h.normal());
		if (h.reflect() > 0.001) {
			Color nc = Color.BLACK; // anche qua il cielo
			Ray rr = new Ray(h.point(), Vector.ORIGIN /*una cosa a caso tanto c lo sovrascrivo*/);
			rr.c = a.c.mirror(h.normal()); // sovrascrivo c: ok, ok, è un po' sporco!!
			Hit hs = s.hit(rr);
			if (hs.h && (hs.t > 1E-10))
				nc = hs.color().mul(lighting(hs.point(), hs.normal()));
			c.mulU(1.0 - h.reflect()).addU(nc.mul(h.reflect()));
		}
		c.mulU(h.color()); // messo dopo per filtrare anche lo specchio
	}
	return (c);
}
public Color lighting(Vector p, Vector n) {
	if (numl == 0) // flat shading
		return (Color.WHITE);
	Hit hl;
	Vector t;
	double dist;
	Color c = new Color(Color.BLACK);
	for (int i = 0; i < numl; i++) { // scorre le luci
		hl = s.hit(new Ray(p, l[i].o)); // traccia raggi verso tutte le luci
		if ((!hl.h) || (hl.t >= 1.0)) { // non c'è niente prima della luce o il primo hit è dopo la luce
			t = hl.r.c;
			dist = t.mod2();
			t.mulU(1.0 / Math.sqrt(dist)); // lo rendo versore io, per non ricalcolare mod2()
			c.addU(l[i].c.mul(n.dot(t)).mulU(l[i].p / dist));
		}
	}
	return (c);
}
}
