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
 * Tipo blob sperimentale, non ancora sviluppato.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Blob extends Shape3D {
	protected Vector[] o = new Vector[10];
	protected int n = 0;
	private Texture c;
	private double r;
public Blob(double d, Texture b) {
	c = b;
	r = d;
}
public void add(Vector a) {
	if (n == o.length) {
		Vector old[] = o;
		o = new Vector[ (o.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
		System.arraycopy(old, 0, o, 0, n);
	}
	o[n++] = a;
}
public Color color(Vector p) {
	return(c.color(p));
}
/**
 * hit method comment.
 */
public Hit hit(EyeRays a) {
	return hit((Ray)a);
}
/**
 * hit method comment.
 */
public Hit hit(Ray a) {
	//(cx²)t²+(2*ox*cx-2cx*x0)t+(ox²-2ox*x0+x0²)+
	//(cy²)t²+(2*oy*cy-2cy*y0)t+(oy²-2oy*y0+y0²)+
	//(cz²)t²+(2*oz*cz-2cz*z0)t+(oz²-2oz*z0+z0²)=
	//1
	double ta=0.0, tb=0.0, tc=0.0;
	ta=n*a.c.mod2();
	tb=n*2*(a.o.x*a.c.x+a.o.y*a.c.y+a.o.z*a.c.z);
	tc=n*a.o.mod2()-r;
	for(int i=0; i<n; i++) {
		tb+=-2*a.c.x*o[i].x+
		    -2*a.c.y*o[i].y+
		    -2*a.c.z*o[i].z;
		tc+=-2*a.o.x*o[i].x+o[i].x*o[i].x+
		    -2*a.o.y*o[i].y+o[i].y*o[i].y+
		    -2*a.o.z*o[i].z+o[i].z*o[i].z;
	}
	double delta=tb*tb-4.0*ta*tc;
	Hit u=new Hit(this, a);
	if (delta>=0.0) {
	  double rdelta=Math.sqrt(delta);
	  ta=0.5/ta;
	  u.addT((-tb-rdelta)*ta);
	  u.addT((-tb+rdelta)*ta);
	}
	return(u);
}
/**
 * normal method comment.
 */
public Vector normal(Vector p) {
	//df/dx=2x-2a=2*2x-2*(a+b)
	Vector u=Vector.ORIGIN;
	for(int i=0; i<n; i++) {
		u.addU(p.sub(o[i]).mul(2));
	}
	return(u.versU());
}
/**
 * overturn method comment.
 */
public void overturn() {
}
/**
 * reflect method comment.
 */
public double reflect(Vector p) {
	return(c.reflect(p));
}
/**
 * scale method comment.
 */
public void scale(Vector i) {
}
public void texture(Texture t) {
	c = t;
}
/**
 * translate method comment.
 */
public void translate(Vector i) {
}
/**
 * Figura blob sperimentale (non funzionante).
 */
public double value(Vector p) {
	double u=0.0;
	for(int i=0; i<n; i++) {
		u+=o[i].sub(p).mod();
	}
	return(u);
}
}
