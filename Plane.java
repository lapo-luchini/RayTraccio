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
 * Piano infinito. <br>
 * Equazione: <code>&lt;x,y,z&gt;.n+d=0</code>
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Plane extends ShapeTextured {
	/** Vettore normale: definisce i coseni direttori del piano */
	private Vector n;
	/** Distanza del piano dall'origine (l'origine &egrave; quindi tenuta sul lato <b>esterno</b> del piano) */
	private double d;
	/** OTTIMIZAZIONE: origine dei dati cachati */
	private Vector b_o=new Vector(1E30, 1E30, 1E30);
	/** OTTIMIZAZIONE: parametro cachato */
	private double b_t;
	/** Entit&agrave; per la statistica */
	private static byte stat=ShapeStats.register("Plane");
/**
 * Crea un piano con parametri dati.
 * @param n vettore normale
 * @param d distanza dall'origine
 * @param c materiale
 */
public Plane(Vector n, double d, Texture c) {
	super(c);
	this.n = n;
	this.d = d / n.mod(); // scala la costante perché non cambi nulla se n è versore
	this.n.versU();
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
	ShapeStats.count(stat, ShapeStats.TYPE_EYERAY);
	Hit u = new Hit(this, a);
	double tmp = n.dot(a.c);
	if (tmp != 0.0) {
		if (a.o == b_o)
			ShapeStats.count(stat, ShapeStats.TYPE_CACHEDRAY);
		else {
			b_o = a.o;
			b_t = - (d + n.dot(a.o));
		}
		u.addT(b_t / tmp);
	}
	if(u.h)
		ShapeStats.count(stat, ShapeStats.TYPE_HIT);
	return (u);
}
public Hit hit(Ray a) {
	// ax+by+cz+d=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	ShapeStats.count(stat, ShapeStats.TYPE_RAY);
	Hit u = new Hit(this, a);
	double tmp = n.dot(a.c);
	if (tmp != 0.0)
		u.addT(- (d + n.dot(a.o)) / tmp);
	if(u.h)
		ShapeStats.count(stat, ShapeStats.TYPE_HIT);
	return (u);
}
public Vector normal(Vector p) {
	return (n);
}
public void overturn() {
	n = Vector.ORIGIN.sub(n);
	d = -d;
}
public void scale(Vector i) {
	n.x /= i.x;
	n.y /= i.x;
	n.z /= i.y;
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
