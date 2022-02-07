// RayTraccio ray-tracing library Copyright (c) 2001-2008 Lapo Luchini <lapo@lapo.it>

// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

package it.lapo.raytraccio.shape;

import it.lapo.raytraccio.EyeRays;
import it.lapo.raytraccio.Hit;
import it.lapo.raytraccio.Poly;
import it.lapo.raytraccio.Ray;
import it.lapo.raytraccio.ShapeTextured;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Figura geometrica definita da un generico polinomio. <br>
 * Si appoggia alla classe {@link Poly} per trovarne le soluzioni.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class ShapePoly extends ShapeTextured {

  /** Array di addendi del polinomio */
  private Element k[] = new Element[10];
  /** Numero di addendi contenuti nell'array */
  private int n = 0;
  /** Potenza totale massima degli addendi */
  private byte maxp = 0;
  /** Entità per la statistica */
  private static byte stat=ShapeStats.register("Poly");

  /**
   * Addendo di un polinomio in (x,y,z). <br>
   * <code>v*x^px+y^py+z^pz</code>
   */
  public class Element {
    /** Potenza in X */
    public byte px;
    /** Potenza in Y */
    public byte py;
    /** Potenza in Z */
    public byte pz;
    /** Valore */
    public double v;

    Element(byte px, byte py, byte pz, double v) {
      this.px = px;
      this.py = py;
      this.pz = pz;
      this.v = v;
    }

    public boolean equalPowerTo(Element e) {
      return((px == e.px) && (py == e.py) && (pz == e.pz));
    }
  }

  public ShapePoly(Texture c) {
    super(c);
  }

  /**
   * Aggiunge un addendo al polinomio. <br>
   * Gli addendi sono contenuti in un array la cui lunghezza è aumentata in modo dinamico
   * per ottimizzare spazio occupato e velocità di aggiunta (in caso manchi spazio
   * l'array viene aumentato del 50%+1).
   * @param px potenza in X dell'addendo
   * @param py potenza in Y dell'addendo
   * @param pz potenza in Z dell'addendo
   * @param v valore
   */
  public void add(double v, byte px, byte py, byte pz) {
    Element e=new Element(px, py, pz, v);
    if(px+py+pz>maxp)
      maxp=(byte)(px+py+pz);
    for(int i=0; i<n; i++)
      if(k[i].equalPowerTo(e)) {
	k[i].v+=e.v;
	return;
      }
    if (n == k.length) {
      Element old[] = k;
      k = new Element[ (k.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
      System.arraycopy(old, 0, k, 0, n);
    }
    k[n++] = e;
  }

  /**
   * Calcola l'intersezione tra un dato raggio e la figura (questa versione non è ottimizzata).
   * @param a il raggio voluto
   */
  public Hit hit(EyeRays a) {
    double t[] = new double[maxp + 1];
    double x[] = new double[maxp + 1];
    double y[] = new double[maxp + 1];
    double z[] = new double[maxp + 1];
    double tmp;
    int i, l, j1, j2;
    ShapeStats.count(stat, ShapeStats.TYPE_RAY);
    for (i = 0; i < n; i++) {
      // k[i] = v * (a.x+c.x*t)^px * (a.y+c.y*t)^py * (a.z+c.z*t)^pz
      // X
      for (l = k[i].px; l >= 0; l--)
	x[l] = Poly.PASCAL[k[i].px][l];
      for (l = 1, tmp = a.c.x; l <= k[i].px; l++, tmp *= a.c.x)
	x[l] *= tmp;
      for (l = k[i].px - 1, tmp = a.o.x; l >= 0; l--, tmp *= a.o.x)
	x[l] *= tmp;
      // Y
      for (l = k[i].py; l >= 0; l--)
	y[l] = Poly.PASCAL[k[i].py][l];
      for (l = 1, tmp = a.c.y; l <= k[i].py; l++, tmp *= a.c.y)
	y[l] *= tmp;
      for (l = k[i].py - 1, tmp = a.o.y; l >= 0; l--, tmp *= a.o.y)
	y[l] *= tmp;
      // Z
      for (l = k[i].pz; l >= 0; l--)
	z[l] = Poly.PASCAL[k[i].pz][l];
      for (l = 1, tmp = a.c.z; l <= k[i].pz; l++, tmp *= a.c.z)
	z[l] *= tmp;
      for (l = k[i].pz - 1, tmp = a.o.z; l >= 0; l--, tmp *= a.o.z)
	z[l] *= tmp;
      // X*Y*Z
      for (l = k[i].px; l >= 0; l--)
	for (j1 = k[i].py; j1 >= 0; j1--)
	  for (j2 = k[i].pz; j2 >= 0; j2--)
	    t[l + j1 + j2] += k[i].v * x[l] * y[j1] * z[j2];
    }
    Hit u = new Hit(this, a);
    l = Poly.roots(t, x);
    for (i = 0; i < l; i++)
      u.addT(x[i]);
    if (u.h)
      ShapeStats.count(stat, ShapeStats.TYPE_HIT);
    return (u);
  }

  public Hit hit(Ray a) {
    double t[] = new double[maxp + 1];
    double x[] = new double[maxp + 1];
    double y[] = new double[maxp + 1];
    double z[] = new double[maxp + 1];
    double tmp;
    int i, l, j1, j2;
    ShapeStats.count(stat, ShapeStats.TYPE_RAY);
    for (i = 0; i < n; i++) {
      // k[i] = v * (o.x+c.x*t)^px * (o.y+c.y*t)^py * (o.z+c.z*t)^pz
      // X
      for (l = k[i].px; l >= 0; l--)
	x[l] = Poly.PASCAL[k[i].px][l];
      for (l = 1, tmp = a.c.x; l <= k[i].px; l++, tmp *= a.c.x)
	x[l] *= tmp;
      for (l = k[i].px - 1, tmp = a.o.x; l >= 0; l--, tmp *= a.o.x)
	x[l] *= tmp;
      // Y
      for (l = k[i].py; l >= 0; l--)
	y[l] = Poly.PASCAL[k[i].py][l];
      for (l = 1, tmp = a.c.y; l <= k[i].py; l++, tmp *= a.c.y)
	y[l] *= tmp;
      for (l = k[i].py - 1, tmp = a.o.y; l >= 0; l--, tmp *= a.o.y)
	y[l] *= tmp;
      // Z
      for (l = k[i].pz; l >= 0; l--)
	z[l] = Poly.PASCAL[k[i].pz][l];
      for (l = 1, tmp = a.c.z; l <= k[i].pz; l++, tmp *= a.c.z)
	z[l] *= tmp;
      for (l = k[i].pz - 1, tmp = a.o.z; l >= 0; l--, tmp *= a.o.z)
	z[l] *= tmp;
      // X*Y*Z
      for (l = k[i].px; l >= 0; l--)
	for (j1 = k[i].py; j1 >= 0; j1--)
	  for (j2 = k[i].pz; j2 >= 0; j2--)
	    t[l + j1 + j2] += k[i].v * x[l] * y[j1] * z[j2];
    }
    Hit u = new Hit(this, a);
    l = Poly.roots(t, x);
    for (i = 0; i < l; i++)
      u.addT(x[i]);
    if (u.h)
      ShapeStats.count(stat, ShapeStats.TYPE_HIT);
    return (u);
  }

  public Vector3D normal(Vector3D p) {
    double ux = 0.0, uy = 0.0, uz = 0.0, t;
    int i, l;
    for (i = 0; i < n; i++) {
      t = k[i].v * k[i].px;
      for (l = k[i].px - 1; l > 0; l--) t *= p.x;
      for (l = k[i].py; l > 0; l--)     t *= p.y;
      for (l = k[i].pz; l > 0; l--)     t *= p.z;
      ux += t;
      t = k[i].v * k[i].py;
      for (l = k[i].px; l > 0; l--)     t *= p.x;
      for (l = k[i].py - 1; l > 0; l--) t *= p.y;
      for (l = k[i].pz; l > 0; l--)     t *= p.z;
      uy += t;
      t = k[i].v * k[i].pz;
      for (l = k[i].px; l > 0; l--)     t *= p.x;
      for (l = k[i].py; l > 0; l--)     t *= p.y;
      for (l = k[i].pz - 1; l > 0; l--) t *= p.z;
      uz += t;
    }
    return ((new Vector3D(ux, uy, uz)).versU());
  }

  public void overturn() {
    for (int i = 0; i < n; i++)
      k[i].v = -k[i].v;
  }

  public double value(Vector3D p) {
    double u = 0.0, t;
    int i, l;
    for (i = 0; i < n; i++) {
      t = k[i].v;
      for (l = k[i].px; l > 0; l--)
	t *= p.x;
      for (l = k[i].py; l > 0; l--)
	t *= p.y;
      for (l = k[i].pz; l > 0; l--)
	t *= p.z;
      u += t;
    }
    return (u);
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>ShapePoly[2.0*x^2*y^2+3.0*x^4,TexturePlain[Color[1.0,0.0,0.0],0.0]]]</code> <br>
   */
  public String toString() {
    String u = "ShapePoly[";
    for (int i = 0; i < n; i++)
      u += (k[i].v < 0 ? "" : "+") + k[i].v +
	   (k[i].px > 0 ? "*x^" + k[i].px : "") +
	   (k[i].py > 0 ? "*y^" + k[i].py : "") +
	   (k[i].pz > 0 ? "*z^" + k[i].pz : "");
    u += "," + c + "]";
    return (u);
  }

}
