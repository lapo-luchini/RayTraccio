package it.lapo.raytraccio;
// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/Poly.java,v 1.7 2002/02/23 16:41:16 lapo Exp $

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
 * Classe statica utile per usare polinomi.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public final class Poly {

  /** Valore minimo sotto il quale i valori trovati vengono considerati nulli */
  public final static double ALMOST_ZERO = 1E-10;
  /** Valore costante utile nel caso di polinomi di quarto grado (due terzi di pi greco) */
  public final static double PI_2_3 = 2.094395102393195492308428922186335256131446266250;
  /** Triangolo di Pascal/Yanghui/(Tartaglia) */
  public final static byte PASCAL[][]={
    /* 0 */ {1},
    /* 1 */ {1,  1},
    /* 2 */ {1,  2,  1},
    /* 3 */ {1,  3,  3,  1},
    /* 4 */ {1,  4,  6,  4,  1},
    /* 5 */ {1,  5, 10, 10,  5,  1},
    /* 6 */ {1,  6, 15, 20, 15,  6,  1},
    /* 7 */ {1,  7, 21, 35, 35, 21,  7,  1},
    /* 8 */ {1,  8, 28, 56, 70, 56, 28,  8,  1},
    /* 9 */ {1,  9, 36, 84,126,126, 84, 36,  9,  1}
  };

  /**
   * Produce una stringa contenete la rappresentazione matematica del polinomio. <br>
   * Esempio: <code>+2.3*x^2-4.5*x^1+0.0*x^0</code>
   * Creation date: (26/02/2001 18:42:09)
   * @param a array di costanti del polinomio, in ordine di grado (es. {0.0, -4.5, 2.3})
   * @param unkn nome dell'incognita da usare (es. 'x')
   * @return la stringa voluta
   */
  public final static String print(double[] a, char unkn) {
    StringBuffer u=new StringBuffer();
    for(int i=a.length-1; i>=0; i--) {
      if(a[i]>=0.0)
	u.append('+');
      u.append(a[i]);
      u.append('*');
      u.append(unkn);
      u.append('^');
      u.append(i);
    }
    return(u.toString());
  }

  // CUBIC and QUARTIC now uses POV-ray code, I will remove it ASAP
  /**
   * Risolve il polinomio dato. <br>
   * Per ora limitata a polinomi di quarto grado.
   * @param k array di costanti del polinomio, in ordine di grado
   * @param res array di soluzioni del polinomio
   * @return numero di soluzioni valide trovate
   */
  public final static int roots(double[] k, double[] res) {
    byte degree = (byte) (k.length - 1);
    while ((k[degree] > -ALMOST_ZERO) && (k[degree] < ALMOST_ZERO) && (degree > 0))
      degree--;
    switch (degree) {
      /*
      ** LINEAR EQUATION
      ** k[1]*x+k[0]==0
      */
      case 1 : {
	res[0] = -k[0] / k[1];
	return (1);
      }
      /*
      ** QUADRATIC EQUATION
      ** k[2]*x²+k[1]*x+k[0]==0
      */
      case 2 : {
	double sb = (-0.5) * k[1];
	double d = sb * sb - k[2] * k[0];
	if (d > ALMOST_ZERO) {
	  d = Math.sqrt(d);
	  res[0] = (sb + d) / k[2];
	  res[1] = (sb - d) / k[2];
	  return (2);
	} else if (d >= -ALMOST_ZERO) {
	  res[0] = sb / k[2];
	  return (1);
	} else
	  return (0);
      }
      /*
      ** CUBIC EQUATION
      ** k[3]*x³+k[2]*x²+k[1]*x+a[0]==0
      */
      case 3 : {
	double Q, R, Q3, R2, sQ, d, an, theta;
	double A2, a0, a1, a2, a3;
	a0 = k[3];
	if (a0 != 1.0) {
	  an = 1.0 / a0;
	  a1 = k[2] * an;
	  a2 = k[1] * an;
	  a3 = k[0] * an;
	} else {
	  a1 = k[2];
	  a2 = k[1];
	  a3 = k[0];
	}
	A2 = a1 * a1;
	Q = (1.0 / 9) * (A2 - 3.0 * a2);
	R = (1.0 / 27) * (a1 * (A2 - 4.5 * a2) + 13.5 * a3);
	Q3 = Q * Q * Q;
	R2 = R * R;
	d = Q3 - R2;
	an = (1.0 / 3) * a1;
	if (d >= 0.0) {
	  d = R / Math.sqrt(Q3);
	  theta = (1.0 / 3) * Math.acos(d);
	  sQ = -2.0 * Math.sqrt(Q);
	  res[0] = sQ * Math.cos(theta) - an;
	  res[1] = sQ * Math.cos(theta + PI_2_3) - an;
	  res[2] = sQ * Math.cos(theta - PI_2_3) - an;
	  return (3);
	} else {
	  sQ = Math.pow(Math.sqrt(R2 - Q3) + Math.abs(R), 1.0 / 3.0);
	  if (R < 0)
	    res[0] = (sQ + Q / sQ) - an;
	  else
	    res[0] = - (sQ + Q / sQ) - an;
	  return (1);
	}
      }
      /*
      ** QUARTIC EQUATION
      ** k[4]*x²²+k[3]*x³+k[2]*x²+k[1]*x+a[0]==0
      */
      case 4 : {
	double cubic[] = new double[4];
	double c12, z, p, q, q1, q2, r, d1, d2;
	double c0, c1, c2, c3, c4;
	int i;
	c0 = k[4];
	if (c0 != 1.0) {
	  c12 = 1.0 / c0;
	  c1 = k[3] * c12;
	  c2 = k[2] * c12;
	  c3 = k[1] * c12;
	  c4 = k[0] * c12;
	} else {
	  c1 = k[3];
	  c2 = k[2];
	  c3 = k[1];
	  c4 = k[0];
	}
	c12 = c1 * c1;
	p = -0.375 * c12 + c2;
	q = 0.125 * c12 * c1 - 0.5 * c1 * c2 + c3;
	r = -0.01171875 * c12 * c12 + 0.0625 * c12 * c2 - 0.25 * c1 * c3 + c4;
	cubic[3] = 1.0;
	cubic[2] = -0.5 * p;
	cubic[1] = -r;
	cubic[0] = 0.5 * r * p - 0.125 * q * q;
	i = roots(cubic, res);
	if (i > 0)
	  z = res[0];
	else
	  return (0);
	d1 = 2.0 * z - p;
	if (d1 < 0.0) {
	  if (d1 > -ALMOST_ZERO)
	    d1 = 0.0;
	  else
	    return (0);
	}
	if (d1 < ALMOST_ZERO) {
	  d2 = z * z - r;
	  if (d2 < 0.0)
	    return (0);
	  d2 = Math.sqrt(d2);
	} else {
	  d1 = Math.sqrt(d1);
	  d2 = 0.5 * q / d1;
	}
	q1 = d1 * d1;
	q2 = -0.25 * c1;
	i = 0;
	p = q1 - 4.0 * (z - d2);
	if (p == 0)
	  res[i++] = -0.5 * d1 - q2;
	else if (p > 0) {
	  p = Math.sqrt(p);
	  res[i++] = -0.5 * (d1 + p) + q2;
	  res[i++] = -0.5 * (d1 - p) + q2;
	}
	p = q1 - 4.0 * (z + d2);
	if (p == 0)
	  res[i++] = 0.5 * d1 - q2;
	else if (p > 0) {
	  p = Math.sqrt(p);
	  res[i++] = 0.5 * (d1 + p) + q2;
	  res[i++] = 0.5 * (d1 - p) + q2;
	}
	return (i);
      }
    }
    System.err.println("Don't know how to solve poly " + print(k, 'x'));
    return (0);
  }

}
