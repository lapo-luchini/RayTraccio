package it.lapo.raytraccio.texture;
import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/TextureMap.java,v 1.7 2002/02/23 16:41:17 lapo Exp $

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
 * Materiale che utilizza un {@link Pattern} per scegliere colori da una mappa di colori. <br>
 * Una mappa di colori assegna una {@link Texture} ad ogni valore in [0.0-1.0], definendo il valore
 * in un numero finito di punti e usando interpolazione lineare nei punti mancanti.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Map extends Texture {

  /** Pattern usato come valore da usare nella mappa di colori */
  private Pattern pat;
  /** Array di valori */
  private double v[];
  /** Array di sottotexture */
  private Texture c[];
  /** Numero di elementi nei due array */
  private int n;

  /**
   * Crea una mappa con due posti vuoti.
   */
  public Map(Pattern p) {
    pat = p;
    v = new double[2];
    c = new Texture[2];
    n = 0;
  }

  /**
   * Crea una mappa con alcuni posti vuoti.
   * @param num numero di posti da creare
   */
  public Map(Pattern p, int num) {
    pat = p;
    v = new double[num];
    c = new Texture[num];
    n = 0;
  }

  /**
   * Aggiunge una definizione di punto.
   * @param value punto della mappa dove definire la {@link Texture} data, deve essere >= del valore predecende e comunque in [0.0-1.0]
   * @param tex {@link Texture} da usare nel punto <code>value</code>
   */
  public void add(double value, Texture tex) {
    if (n == v.length) {
      double ov[] = v;
      Texture oc[] = c;
      v = new double[ (v.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
      c = new Texture[v.length];
      System.arraycopy(ov, 0, v, 0, n);
      System.arraycopy(oc, 0, c, 0, n);
    }
    if (n > 0)
      if (value < v[n - 1])
	throw new RuntimeException("value must be >= of the previous value");
    if ((value < 0.0) || (value > 1.0))
      throw new RuntimeException("value must be in the [0.0-1.0] range");
    v[n] = value;
    c[n++] = tex;
  }

  public Color color(Vector3D p) {
    double d = pat.scalar(p);
    if (d <= v[0])
      return (c[0].color(p));
    int i = 1;
    while ((i < n) && (d > v[i]))
      i++;
    if (i == n)
      return (c[n - 1].color(p));
    d = (d - v[i - 1]) / (v[i] - v[i - 1]);
    return (c[i - 1].color(p).mul(1.0 - d).addU(c[i].color(p).mul(d)));
  }

  /**
   * Ottimizza l'occupazione di memoria (da usare dopo aver aggiunto tutti i valori).
   */
  public void optimize() {
    if (v.length > n) {
      double ov[] = v;
      Texture oc[] = c;
      v = new double[n];
      c = new Texture[n];
      System.arraycopy(ov, 0, v, 0, n);
      System.arraycopy(oc, 0, c, 0, n);
    }
  }

  public double reflect(Vector3D p) {
    double d = pat.scalar(p);
    if (d <= v[0])
      return (c[0].reflect(p));
    int i = 1;
    while ((i < n) && (d > v[i]))
      i++;
    if (i == n)
      return (c[n - 1].reflect(p));
    d = (d - v[i - 1]) / (v[i] - v[i - 1]);
    return (c[i - 1].reflect(p) * (1.0 - d) + c[i].reflect(p) * d);
  }

}
