/**
 * Insert the type's description here.
 * Creation date: (27/02/2001 22:35:12)
 * @author: 
 */
abstract class ShapeTextured extends Shape3D {
	/** Materiale della figura */
	protected Texture c;
/**
 * Crea una figura che usa il dato materiale.
 * @param c materiale
 */
public ShapeTextured(Texture c) {
	this.c = c;
}
public Color color(Vector p) {
	return (c.color(p));
}
public double reflect(Vector p) {
	return(c.reflect(p));
}
public void texture(Texture c) {
	this.c = c;
}
}
