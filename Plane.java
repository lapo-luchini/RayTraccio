class Plane extends Shape3D {
	// ax+by+cz+d=0
	private Vector n; // (a, b, c)
	private double d;
	private Texture c;
	private Vector b_o=new Vector(1E30, 1E30, 1E30);
	private double b_t;
	protected static transient int stat_numrays;
	protected static transient int stat_numeyerays;
	protected static transient int stat_numcachedrays;
	protected static transient int stat_numhits;
Plane(Vector a, double e, Texture b) {
	c = b;
	n = a;
	d = e / n.mod(); // scala la costante perché non cambi nulla se n è versore
	n.versU();
}
public Color color(Vector p) {
	return (c.color(p));
}
// per ora NON ottimizzato
public Hit hit(EyeRays a) {
	// ax+by+cz+d=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	stat_numeyerays++;
	Hit u = new Hit(this, a);
	double tmp = n.dot(a.c);
	if (tmp != 0.0) {
		if (a.o == b_o)
			stat_numcachedrays++;
		else {
			b_o = a.o;
			b_t = - (d + n.dot(a.o));
		}
		u.addT(b_t / tmp);
	}
	if(u.h)
		stat_numhits++;
	return (u);
}
public Hit hit(Ray a) {
	// ax+by+cz+d=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	stat_numrays++;
	Hit u = new Hit(this, a);
	double tmp = n.dot(a.c);
	if (tmp != 0.0)
		u.addT(- (d + n.dot(a.o)) / tmp);
	if(u.h)
		stat_numhits++;
	return (u);
}
public Vector normal(Vector p) {
	return (n);
}
public void overturn() {
	n = Vector.ORIGIN.sub(n);
	d = -d;
}
public double reflect(Vector p) {
	return(c.reflect(p));
}
public void scale(Vector i) {
	n.x /= i.x;
	n.y /= i.x;
	n.z /= i.y;
}
public static String stats() {
	return("PLANE\n"+
	       "Number of rays:        "+stat_numrays+"\n"+
	       "Number of eye-rays:    "+stat_numeyerays+"\n"+
	       "Number of cached rays: "+stat_numcachedrays+" ("+((stat_numcachedrays*100.0)/(stat_numrays+stat_numeyerays))+"%)\n"+
	       "Number of hits:        "+stat_numhits+" ("+((stat_numhits*100.0)/(stat_numrays+stat_numeyerays))+"%)");
}
public String toString() {
	return ("Plane[" + n + "," + d + "]");
}
public void translate(Vector i) {
	d += n.dot(i);
}
public double value(Vector p) {
	// ax+by+cz+d
	return (n.dot(p) + d);
}
}
