import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
/**
 * Punto di intersezione tra {@link Ray raggio} e {@link Shape3D figura}.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Hit {
	/** <code>true</code> se c'è stata effettivamente un intersezione valida, <code>false</code> altrimenti */
	public boolean h = false;
	/** Coordinata parametrica <code>t</code> del raggio dove è avvenuta l'intersezione */
	public double t;
	/** Raggio che ha generato l'intersezione */
	public Ray r;
	/** Figura che ha generato l'intersezione */
	public Shape3D g;
	/** Vettore posizione del punto dell'intersezione (autocalcolato al primo richiamo di {@link point() point()}) */
	protected Vector p = null;
	/** Vettore normale della superficie nel punto colpito (autocalcolato al primo richiamo di {@link normal() normal()}) */
	protected Vector n = null;
	/** Colore della superficie nel punto colpito (autocalcolato al primo richiamo di {@link color() color()}) */
	protected Color c = null;
	/** Coefficente di riflessione della superficie nel punto colpito (autocalcolato al primo richiamo di {@link rflect() reflect()}) */
	protected double rr = Double.NaN;
/**
 * Prepara un nuovo oggetto {@link Hit Hit}, pronto a ricevere intersezioni.
 * @param a figura intersecata
 * @param b raggio intersecante
 */
Hit(Shape3D a, Ray b) {
	g = a;
	r = b;
}
/**
 * Aggiunge una possibile intersezione. <br>
 * Vengono scartate quelle con coordinata non positiva (si trovano prima del punto di partenza del raggio)
 * e quelle con coordinata maggiore a quella già presente (viene quindi mantenuto solo la <i>prima</i>
 * intersezione del raggio, cosa che andrà cambiata se si vogliono supportare le rifrazioni semplificate
 * senza indice di rifazione).<br>
 * Se l'intersezione è accettata il valore {@link h h} viene posto <code>true</code> e il valore {@link t t} prende il valore passato.
 * @param a cordinata parametrica della possibile intersezione
 */
public void addT(double a) {
	if (a > 1E-10) { // se è un hit "davanti"
		if (h) {        // se c'era già un hit
			if (a < t)     // se questo è più vicino
				t = a;        // questo è il nuovo hit
		} else          // altrimenti
			t = a;         // questo è il nuovo hit
		h = true;
	}
}
/**
 * Colore della figura nel punto intersecato. <br>
 * Il valore viene cachato nel campo {@link c c}, inizialmente <code>null</code>.
 * @return oggetto {@link Color Color} contenete il colore desiderato
 */
public Color color() {
	if (c == null)
		c = g.color(point());
	return (c);
}
/**
 * Normale della figura nel punto intersecato. <br>
 * Il valore viene cachato nel campo {@link n n}, inizialmente <code>null</code>.
 * @return oggetto {@link Vector Vector} contenete la normale desiderata
 */
public Vector normal() {
	if (n == null)
		n = g.normal(point());
	return (n);
}
/**
 * Posizione del punto intersecato. <br>
 * Il valore viene cachato nel campo {@link p p}, inizialmente <code>null</code>.
 * @return oggetto {@link Vector Vector} contenete la posizione desiderata
 */
public Vector point() {
	if (p == null)
		p = r.point(t);
	return (p);
}
/**
 * Coefficente di riflessione della figura nel punto intersecato. <br>
 * Il valore viene cachato nel campo {@link rr rr}, inizialmente <code>NaN</code>.
 * @return valore <code>double</code> contenete il coefficente desiderato
 */
public double reflect() {
	if (Double.isNaN(rr))
		rr = g.reflect(point());
	return (rr);
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>Hit[false]</code> <br>
 * Esempio: <code>Hit[true,t:0.234,g:Quadric@1234,r:Ray@5678]</code>
 */
public String toString() {
	return ("Hit[" + h + (h ? ",t:" + t + ",g:" + g + ",r:" + r : "") + "]");
}
}
