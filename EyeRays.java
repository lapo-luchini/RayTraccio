/** Genera i raggi visuali (ottimizzati) che scandiscono un area rettangolare.
  */
class EyeRays extends Ray {
	/** Punto di angolo in alto a sinistra
	  */
	protected Vector d;
	/** Punto di delta orizzontale
	  */
	protected Vector h;
	/** Punto di delta verticale
	  */
	protected Vector v;
	protected int i, maxx;
EyeRays(Vector o, Vector d, Vector h, Vector v, int x, int y) {
	super(o, d.sub(h.mul(0.5)).sub(v.mul(0.5)));
	this.h = h.mul(1.0 / x);
	this.v = v.mul(1.0 / y);
	this.c.addU(this.h.mul(0.5)).addU(this.v.mul(0.5));
	this.d = new Vector(this.c);
	maxx = x;
	i = 0;
	//System.out.println("EYERAY INIT");
	//System.out.println("O: "+this.o+" "+o);
	//System.out.println("C: "+this.c+" "+d);
	//System.out.println("H: "+this.h+" "+h);
	//System.out.println("V: "+this.v+" "+v);
}
public void next() {
	if (i == maxx - 1) {
		i = 0;
		d.addU(v);
		c = new Vector(d); // per usare dopo l'operatore unario
	} else {
		c.addU(h);
		i++;
	}
}
}
