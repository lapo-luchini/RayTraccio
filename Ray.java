/** Raggio visuale.
  */
class Ray {
	/** Punto di origine
	  */
	protected Vector o;
	/** Punto di direzione
	  */
	protected Vector c;
/** Crea un raggio da un punto verso un altro.
  * @params o origine del raggio (coordinata parametrica 0.0)
  * @params a punto verso quale il raggio si dirige (coordinata parametrica 1.0)
  */
Ray(Vector o, Vector a) {
	this.o = o;
	this.c = a.sub(o);
}
/** Trova un punto sul raggio, data la cooridnata parametrica.
  * @params a coordinata parametrica (0.0 per l'origine, 1.0 per il punto di direzione)
  * @returns vettore posizione
  */
public Vector point(double a) {
	return (new Vector(o.x + a * c.x, o.y + a * c.y, o.z + a * c.z));
}
public String toString() {
	return ("Ray[" + o + "+t*" + c + "]");
}
public Ray transform(TransformMatrix t) {
	return(new Ray(o.transform(t), o.add(c).transform(t)));
}
}
