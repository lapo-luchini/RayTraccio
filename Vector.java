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
 * Vettore nello spazio tridimensionale.<br>
 * Le tre componenti sono valori <code>double</code> sui tre assi x, y, z.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Vector {
	/** Componente X */
	protected double x;
	/** Componente Y */
	protected double y;
	/** Componente Z */
	protected double z;
	/** Vettore origine &lt;0.0, 0.0, 0.0&gt; */
	public static final Vector ORIGIN = new Vector(0.0, 0.0, 0.0);
	/** Versore X &lt;1.0, 0.0, 0.0&gt; */
	public static final Vector VERS_X = new Vector(1.0, 0.0, 0.0);
	/** Versore Y &lt;0.0, 1.0, 0.0&gt; */
	public static final Vector VERS_Y = new Vector(0.0, 1.0, 0.0);
	/** Versore Z &lt;0.0, 0.0, 1.0&gt; */
	public static final Vector VERS_Z = new Vector(0.0, 0.0, 1.0);
/**
 * Crea un vettore date le componenti.
 * @param x componente X
 * @param y componente Y
 * @param z componente Z
 */
Vector(double x, double y, double z) {
	this.x = x;
	this.y = y;
	this.z = z;
}
/**
 * Crea un vettore copiandolo da un altro.
 * @param a vettore da usare
 */
Vector(Vector a) {
	this.x = a.x;
	this.y = a.y;
	this.z = a.z;
}
/**
 * Somma di vettori.
 * @param a vettore da usare
 * @return un nuovo oggetto <code>Vector</code>
 */
public Vector add(Vector a) {
	return (new Vector(x + a.x, y + a.y, z + a.z));
}
/**
 * Somma di vettori.
 * @param a vettore da usare
 * @return questo stesso oggetto <code>Vector</code>
 */
public Vector addU(Vector a) {
	x += a.x;
	y += a.y;
	z += a.z;
	return (this);
}
/**
 * Prodotto vettoriale di vettori.
 * @param a vettore da usare
 * @return un nuovo oggetto <code>Vector</code>
 */
public Vector cross(Vector a) {
	return (new Vector(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x));
}
/**
 * Prodotto scalare di vettori.
 * @param a vettore da usare
 * @return un nuovo oggetto <code>Vector</code>
 */
public double dot(Vector a) {
	return (x * a.x + y * a.y + z * a.z);
}
/**
 * Confronto di vettori.
 * @param a vettore da usare
 * @return <code>true</code> se <code>a</code> è un <code>Vector</code> con uguali componenti<br><code>false</code> in caso contrario
 */
public boolean equals(Object a) {
	if (!(a instanceof Vector))
		return (false);
	return ((x == ((Vector) a).x) && (x == ((Vector) a).y) && (x == ((Vector) a).z));
}
/**
 * Riflette il vettore intorno al vettore passato.<br>
 * Equivalente a <code>this.add(a.mul(-2.0 * a.dot(this)))</code>.
 * @param a vettore da usare
 * @return un nuovo oggetto <code>Vector</code>
 */
public Vector mirror(Vector a) {
	return (this.add(a.mul(-2.0 * a.dot(this))));
}
/**
 * Modulo del vettore.
 * @return modulo
 */
public double mod() {
	return (Math.sqrt(x * x + y * y + z * z));
}
/**
 * Modulo quadro del vettore.<br>
 * Più veloce di {@link #mod() mod()} in quanto non ha bisogno di fare {@link Math.sqrt() Math.sqrt()}.
 * @return modulo^2
 */
public double mod2() {
	return (x * x + y * y + z * z);
}
/**
 * Moltiplicazione per uno scalare.
 * @param a scalare da usare
 * @return un nuovo oggetto <code>Vector</code>
 */
public Vector mul(double a) {
	return (new Vector(x * a, y * a, z * a));
}
/**
 * Moltiplicazione per uno scalare.
 * @param a scalare da usare
 * @return questo stesso oggetto <code>Vector</code>
 */
public Vector mulU(double a) {
	x *= a;
	y *= a;
	z *= a;
	return (this);
}
/**
 * Sottrazione di vettori.
 * @param a vettore da usare
 * @return un nuovo oggetto <code>Vector</code>
 */
public Vector sub(Vector a) {
	return (new Vector(x - a.x, y - a.y, z - a.z));
}
/**
 * Sottrazione di vettori.
 * @param a vettore da usare
 * @return questo stesso oggetto <code>Vector</code>
 */
public Vector subU(Vector a) {
	x -= a.x;
	y -= a.y;
	z -= a.z;
	return (this);
}
public String toString() {
	return ("Vector[" + x + "," + y + "," + z + "]");
}
/**
 * Versore parallelo a questo vettore.
 * @return un nuovo oggetto <code>Vector</code> di modulo unitario
 */
public Vector vers() {
	return (this.mul(1.0 / this.mod()));
}
/**
 * Trasforma in versore questo vettore.
 * @return questo stesso oggetto <code>Vector</code> (di modulo ora unitario)
 */
public Vector versU() {
	double im = 1.0 / this.mod();
	this.x *= im;
	this.y *= im;
	this.z *= im;
	return (this);
}
}
