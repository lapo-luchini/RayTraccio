/**
 * Piano infinito. <br>
 * Equazione: <code>&lt;x,y,z&gt;.n+d=0</code>
 */
class Plane extends Shape3D {
	/** Vettore normale: definisce i coseni direttori del piano */
	private Vector n;
	/** Distanza del piano dall'origine (l'origine è quindi tenuta sulato <b>esterno</b> del piano) */
	private double d;
	/** Materiale del piano */
	private Texture c;
	/** OTTIMIZAZIONE: origine dei dati cachati */
	private Vector b_o=new Vector(1E30, 1E30, 1E30);
	/** OTTIMIZAZIONE: parametro cachato */
	private double b_t;
	/** STATISTICA: numero di raggi intersecati */
	protected static transient int stat_numrays;
	/** STATISTICA: numero di raggi intersecati */
	protected static transient int stat_numeyerays;
	/** STATISTICA: numero di raggi cachati */
	protected static transient int stat_numcachedrays;
	/** STATISTICA: numero di effettive intersezioni */
	protected static transient int stat_numhits;
/**
 * Crea un piano co parametri dati.
 * @param n vettore normale
 * @param d distanza dall'origine
 * @param c materiale
 */
Plane(Vector n, double d, Texture c) {
	this.n = n;
	this.d = d / n.mod(); // scala la costante perché non cambi nulla se n è versore
	this.c = c;
	this.n.versU();
}
public Color color(Vector p) {
	return (c.color(p));
}
/**
 * Calcola l'intersezione tra un dato raggio e la figura (versione ottimizzata).
 * @param a il raggio voluto
 */
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
/**
 * Crea una stringa contenente le statistiche di uso degli oggetti di questa classe
 * @return stringa di statistiche
 */
public static String stats() {
	return("PLANE\n"+
	       "Number of rays:        "+stat_numrays+"\n"+
	       "Number of eye-rays:    "+stat_numeyerays+"\n"+
	       "Number of cached rays: "+stat_numcachedrays+" ("+((stat_numcachedrays*100.0)/(stat_numrays+stat_numeyerays))+"%)\n"+
	       "Number of hits:        "+stat_numhits+" ("+((stat_numhits*100.0)/(stat_numrays+stat_numeyerays))+"%)");
}
public void texture(Texture t) {
	c = t;
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>Plane[Vector[1.0,0.0,0.0],3.0,TexturePlain[Color[1.0,0.0,0.0],0.0]]</code> <br>
 */
public String toString() {
	return ("Plane[" + n + "," + d + "," + c + "]");
}
public void translate(Vector i) {
	d += n.dot(i);
}
public double value(Vector p) {
	// ax+by+cz+d
	return (n.dot(p) + d);
}
}
