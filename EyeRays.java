/**
 * Genera i raggi visuali (ottimizzati) che scandiscono un area rettangolare. <br>
 * L'origine dei raggi è sempre la stessa (l'<i>occhio</i> dell'osservatore).
 */
class EyeRays extends Ray {
	/** Punto di angolo in alto a sinistra */
	protected Vector ci;
	/** Vettore di delta orizzontale (U) */
	protected Vector h;
	/** Vettore di delta verticale (V) */
	protected Vector v;
	/** Vettore che scorre le V a U minima */
	protected Vector d;
	/** Contatore di riga */
	protected int i;
	/** Contatore globale */
	protected int cnt;
	/** Dimensione di riga */
	protected int maxx;
	/** Usato da Specific Ray per non creare un oggetto ogni volta */
	private Ray sr;
/**
 * Imposta l'oggetto per generare i raggi richiesti.
 * @param o vettore posizione dell'origine dei raggi (l'<i>occhio</i>)
 * @param d vettore posizione del punto 'guardato'
 * @param h vettore 'orizzonte' (indica la direzione orizzontale ed è lungo quanto la larghezza voluta dell'immagine)
 * @param v vettore verticale (indica la direzione verticale ed è lungo quanto l'altezza voluta dell'immagine)
 * @param x numero di punti in direzione orizzontale
 * @param y numero di punti in direzione verticale
 */
EyeRays(Vector o, Vector d, Vector h, Vector v, int x, int y) {
	super(o, d.sub(h.mul(0.5)).sub(v.mul(0.5)));
	this.h = h.mul(1.0 / x);
	this.v = v.mul(1.0 / y);
	this.c.addU(this.h.mul(0.5)).addU(this.v.mul(0.5));
	this.ci = new Vector(this.c);
	this.d = new Vector(this.c);
	maxx = x;
	i = cnt = 0;
	sr = new Ray(o, d);
}
/**
 * Calcola il prossimo raggio (l'area viene scandita prima in orizzontale e poi in verticale).
 * @return il prossimo raggio
 */
public void next() {
	if (i == maxx - 1) {
		i = 0;
		d.addU(v);
		c = new Vector(d); // per usare dopo l'operatore unario
	} else {
		c.addU(h);
		i++;
	}
	cnt++;
}
/**
 * Calcola un raggio specifico date le coordinate.
 * @param x coordinata orizzontale del punto di arrivo richiesto
 * @param y coordinata verticale del punto di arrivo richiesto
 * @return il raggio richiesto
 */
public Ray specificRay(double x, double y) {
	sr.c=ci.add(h.mul(x)).addU(v.mul(y));
	return(sr);
}
}
