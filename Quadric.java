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
 * Quadrica generica. <br>
 * Equazione: <code>k0*x�+k1*xy+k2*y�+k3*xz+k4*yz+k5*z�+k6*x+k7*y+k8*z+k9=0</code>
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Quadric extends ShapeTextured {
  // ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l=0
  // | a �b �d|
  // |�b  c �e|
  // |�d �e  f|
  /** Parametri standard di una sfera unitaria centrata nell'origne. */
	public static double SFERA[]= { 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0};
  /** Parametri standard di un cilindro unitario centrato sull'asse X. */
	public static double CIL_X[]= { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0};
  /** Parametri standard di un cilindro unitario centrato sull'asse Y. */
	public static double CIL_Y[]= { 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0};
  /** Parametri standard di un cilindro unitario centrato sull'asse Z. */
	public static double CIL_Z[]= { 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,-1.0};
  /** Parametri standard di un cono unitario centrato sull'asse X. */
	public static double CONO_X[]={-1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
  /** Parametri standard di un cono unitario centrato sull'asse Y. */
	public static double CONO_Y[]={ 1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
  /** Parametri standard di un cono unitario centrato sull'asse Z. */
	public static double CONO_Z[]={ 1.0, 0.0, 1.0, 0.0, 0.0,-1.0, 0.0, 0.0, 0.0, 0.0};
  /** Parametri standard di un paraboloide non rigato unitario centrato sull'asse X. */
	public static double PARA_X[]={ 0.0, 0.0, 1.0, 0.0, 0.0, 1.0,-1.0, 0.0, 0.0, 0.0};
  /** Parametri standard di un paraboloide non rigato unitario centrato sull'asse Y. */
	public static double PARA_Y[]={ 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,-1.0, 0.0, 0.0};
  /** Parametri standard di un paraboloide non rigato unitario centrato sull'asse Z. */
	public static double PARA_Z[]={ 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,-1.0, 0.0};
  /** Parametri standard di un iperboloide rigato unitario centrato sull'asse Y. */
	public static double IPE_Y[]= { 1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0};
	/** I 10 parametri della quadrica */
	private double k[];
	/** OTTIMIZAZIONE: origine dei dati cachati */
	private Vector b_o=new Vector(1E30, 1E30, 1E30);
	/** OTTIMIZAZIONE: parametro cachato */
	private double b_tc, b_tbA, b_tbB, b_tbC, b_tbINC;
	/** Entit� per la statistica */
	private static byte stat=ShapeStats.register("Quadric");
public Quadric(double a[], Texture c) {
	super(c);
	if (a.length != 10)
		throw (new IllegalArgumentException("Requires a 10 elements array"));
	k = new double[10];
	System.arraycopy(a, 0, k, 0, 10); // copia locale dei coefficenti
}
/** Versione ottimizzata di hit con un raggio di telecamera.
  */
public Hit hit(EyeRays a) {
	// ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	double tc, ta;
	byte i;
	ShapeStats.count(stat, ShapeStats.TYPE_EYERAY);
	if((b_o==a.o)&&(a.cnt!=0)) {
		ShapeStats.count(stat, ShapeStats.TYPE_CACHEDRAY);
	  tc=b_tc;
	  //tb=b_tbx*a.c.x+b_tby*a.c.y+b_tbz*a.c.z;
	  if(a.i==0)
	  	b_tbINC=(b_tbA+=b_tbC);
	  else
	  	b_tbINC+=b_tbB;
	} else {
	  b_o=a.o;
	  b_tc=tc=k[0]*(a.o.x*a.o.x)+
		        k[1]*(a.o.x*a.o.y)+
		        k[2]*(a.o.y*a.o.y)+
		        k[3]*(a.o.x*a.o.z)+
		        k[4]*(a.o.y*a.o.z)+
		        k[5]*(a.o.z*a.o.z)+
		        k[6]*a.o.x+
		        k[7]*a.o.y+
		        k[8]*a.o.z+
		        k[9];
	  double
	  	b_tbx=k[0]*2.0*a.o.x+k[1]*a.o.y+k[3]*a.o.z+k[6],
	  	b_tby=k[1]*a.o.x+k[2]*2.0*a.o.y+k[4]*a.o.z+k[7],
	  	b_tbz=k[3]*a.o.x+k[4]*a.o.y+k[5]*2.0*a.o.z+k[8];
	  //tb=b_tbx*a.c.x+b_tby*a.c.y+b_tbz*a.c.z; // e qui sfrutto il fatto che � un EyeRays
		b_tbA=(b_tbx*a.d.x+b_tby*a.d.y+b_tbz*a.d.z);
		b_tbB=(b_tbx*a.h.x+b_tby*a.h.y+b_tbz*a.h.z);
		b_tbC=(b_tbx*a.v.x+b_tby*a.v.y+b_tbz*a.v.z);
		b_tbINC=b_tbA;
	}
	ta=k[0]*(a.c.x*a.c.x)+
	   k[1]*(a.c.x*a.c.y)+
	   k[2]*(a.c.y*a.c.y)+
	   k[3]*(a.c.x*a.c.z)+
	   k[4]*(a.c.y*a.c.z)+
	   k[5]*(a.c.z*a.c.z);
	double delta=b_tbINC*b_tbINC-4.0*ta*tc;
	Hit u=new Hit(this, a);
	if (delta>=0.0) {
	  double rdelta=Math.sqrt(delta);
	  ta=0.5/ta;
	  u.addT((-b_tbINC-rdelta)*ta);
	  u.addT((-b_tbINC+rdelta)*ta);
	}
	if(u.h)
		ShapeStats.count(stat, ShapeStats.TYPE_HIT);
	return(u);
}
public Hit hit(Ray a) {
	// ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	/*if(a instanceof EyeRays)
		return(hit((EyeRays)a));*/
	double tc, tb, ta;
	byte i;
	ShapeStats.count(stat, ShapeStats.TYPE_RAY);
  tc=k[0]*(a.o.x*a.o.x)+
	   k[1]*(a.o.x*a.o.y)+
	   k[2]*(a.o.y*a.o.y)+
	   k[3]*(a.o.x*a.o.z)+
	   k[4]*(a.o.y*a.o.z)+
	   k[5]*(a.o.z*a.o.z)+
	   k[6]*a.o.x+
	   k[7]*a.o.y+
	   k[8]*a.o.z+
	   k[9];
	tb=(k[0]*2.0*a.o.x+k[1]*a.o.y+k[3]*a.o.z+k[6])*a.c.x+
	 (k[1]*a.o.x+k[2]*2.0*a.o.y+k[4]*a.o.z+k[7])*a.c.y+
	 (k[3]*a.o.x+k[4]*a.o.y+k[5]*2.0*a.o.z+k[8])*a.c.z;
	ta=k[0]*(a.c.x*a.c.x)+
	   k[1]*(a.c.x*a.c.y)+
	   k[2]*(a.c.y*a.c.y)+
	   k[3]*(a.c.x*a.c.z)+
	   k[4]*(a.c.y*a.c.z)+
	   k[5]*(a.c.z*a.c.z);
	double delta=tb*tb-4.0*ta*tc;
	Hit u=new Hit(this, a);
	if (delta>=0.0) {
	  double rdelta=Math.sqrt(delta);
	  ta=0.5/ta;
	  u.addT((-tb-rdelta)*ta);
	  u.addT((-tb+rdelta)*ta);
	}
	if(u.h)
		ShapeStats.count(stat, ShapeStats.TYPE_HIT);
	return(u);
}
public Vector normal(Vector p) {
	// ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l=0
	// f'x=2ax+by+dz+g
	// f'y=bx+2cy+ez+h
	// f'z=dx+ey+2fz+i
	return(new Vector(
	  2.0*k[0]*p.x+k[1]*p.y+k[3]*p.z+k[6],
	  k[1]*p.x+2.0*k[2]*p.y+k[4]*p.z+k[7],
	  k[3]*p.x+k[4]*p.y+2.0*k[5]*p.z+k[8]).versU());
}
public void overturn() {
	for (int i = 0; i < 10; i++)
		k[i] = -k[i];
}
public void scale(Vector i) {
	// ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l=0
	// X=zx*x     x=X/zx
	// Y=zy*y ==> y=Y/zy
	// Z=zz*z     z=Z/zz
	double ix, iy, iz;
	ix = 1.0 / i.x;
	iy = 1.0 / i.y;
	iz = 1.0 / i.z;
	k[0] *= ix * ix;
	k[1] *= ix * iy;
	k[2] *= iy * iy;
	k[3] *= ix * iz;
	k[4] *= iy * iz;
	k[5] *= iz * iz;
	k[6] *= ix;
	k[7] *= iy;
	k[8] *= iz;
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>Quadric[0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,TexturePlain[Color[1.0,0.0,0.0],0.0]]]</code> <br>
 */
public String toString() {
	String u = "Quadric[";
	for (int i = 0; i < 10; i++)
		u += k[i] + ",";
	u += c + "]";
	return (u);
}
public void translate(Vector i) {
	// ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l=0
	// X=zx+x     x=X-zx
	// Y=zy+y ==> y=Y-zy
	// Z=zz+z     z=Z-zz
	// a(X+x)�+     b(X+x)(Y+y)+    c(Y+y)�+     d(X+x)(Z+z)+    e(Y+y)(Z+z)+    f(Z+z)�+     g(X+x)+h(Y+y)+i(Z+z)+l=0
	// aX�+2aXx+ax�+bXY+bXy+bYx+bxy+cY�+2cYy+cy�+dXZ+dXz+dZx+dzx+eYZ+eYz+eZy+ezy+fZ�+2fZz+fz�+gX+gx+ hY+hy +iZ+iz +l=0
	// X�=a XY=b Y�=c XZ=d YZ=e Z�=f
	// X =2ax+by+dz+g
	// Y =bx+2cy+ez+h
	// Z =dx+ey+2fz+i
	//   =ax�+bxy+cy�+dzx+ezy+fz�+gx+hy+iz+l
	i=Vector.ORIGIN.sub(i); // i=i.mul(-1.0) dev'essere pi� lento
	k[9]+=(k[0]*i.x*i.x)+(k[2]*i.y*i.y)+(k[5]*i.z*i.z)+ // prima perch� usa 678
	      (k[1]*i.x*i.y)+(k[3]*i.x*i.z)+(k[4]*i.y*i.z)+
	      (k[6]*i.x)+(k[7]*i.y)+(k[8]*i.z);
	k[6]+=(2*k[0]*i.x)+(k[1]*i.y)+(k[3]*i.z);
	k[7]+=(k[1]*i.x)+(2*k[2]*i.y)+(k[4]*i.z);
	k[8]+=(k[3]*i.x)+(k[4]*i.y)+(2*k[5]*i.z);
}
public double value(Vector p) {
	// ax�+bxy+cy�+dxz+eyz+fz�+gx+hy+iz+l
	return(k[0]*p.x*p.x+k[1]*p.x*p.y+k[2]*p.y*p.y+k[3]*p.x*p.z+k[4]*p.y*p.z+k[5]*p.z*p.z+k[6]*p.x+k[7]*p.y+k[8]*p.z+k[9]);
}
}
