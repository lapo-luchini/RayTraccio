// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header$

// This file is part of RayTraccio.
//
// RayTraccio is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// RayTraccio is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RayTraccio; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

/**
 * Raggio visuale. <br>
 * Il raggio ha una coordinata parametrica <code>t</code> che vale <code>0.0</code>
 * nel punto di partenza e <code>1.0</code> nel punto di arrivo.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Ray {
	/** Punto di origine */
	protected Vector o;
	/** Punto di direzione (coseni direttori) */
	protected Vector c;
/** Crea un raggio copiandolo da un'altro.
  * @params r raggio da cui copiare
  */
Ray(Ray r) {
	this.o = r.o;
	this.c = r.c;
}
/** Crea un raggio da un punto verso un altro.
  * @params o origine del raggio (coordinata parametrica <code>t=0.0</code>)
  * @params a punto verso quale il raggio si dirige (coordinata parametrica <code>t=1.0</code>)
  */
Ray(Vector o, Vector a) {
	this.o = o;
	this.c = a.sub(o);
}
/** Trova un punto sul raggio, data la cooridnata parametrica.
  * @params a coordinata parametrica (<code>0.0</code> per l'origine, <code>1.0</code> per il punto di arrivo)
  * @returns vettore posizione richiesto
  */
public Vector point(double a) {
	return (new Vector(o.x + a * c.x, o.y + a * c.y, o.z + a * c.z));
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>Ray[Vector[1.0,2.0,3.0]+t*Vector[1.0,0.0,0.0]]</code> <br>
 */
public String toString() {
	return ("Ray[" + o + "+t*" + c + "]");
}
/**
 * Crea un raggio deformando lo spazio del raggio originale.
 * @param t la matrice di trasformazione da usare
 * @return il raggio trasformato
 */
public Ray transform(TransformMatrix t) {
	return(new Ray(t.transformVector(o), t.transformVector(o.add(c))));
}
}
