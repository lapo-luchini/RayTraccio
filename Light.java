import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * Luce puntiforme.
 */
class Light {
	/** Vettore posizione */
  protected Vector o;
  /** Colore della luce (alla distanza {@link p p}) */
  protected Color c;
  /** Potenza della luce (distanza alla quale usare il colore {@link c c} invariato) */
  protected double p;
/**
 * Crea una luce puntiforme cno parametri dati. <br>
 * @param o Vettore posizione
 * @param c Colore
 * @param p Potenza
 */
Light(Vector o, Color c, double p) {
	this.o = o;
	this.c = c;
	this.p = p;
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>Light[Vector[1.0,2.0,3.0],Color[1.0,0.0,0.0],5.0]</code> <br>
 */
public String toString() {
	return ("Light[" + o + "," + c + "," + p + "]");
}
}
