package it.lapo.raytraccio;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/HitTransform.java,v 1.8 2002/02/23 16:41:16 lapo Exp $

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
 * Intersezione speciale usata per deformare lo spazio e le proprietà della figura.
 * @see ShapeTransform
 * @see TransformMatrix
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class HitTransform extends Hit {

  /** {@link Hit} da modificare */
  private Hit ht;
  /** Trasformazione da usare (se <code>null</code> non viene applicata trasformazione) */
  private TransformMatrix tm;
  /** Determinante(tm)^(-1/3) usato in {@link #normal()} */
  private double tmid;
  /** {@link Texture} da usare (se <code>null</code> viene usata quella dell'oggetto originale) */
  private Texture tx;
  /** indica se "capovolgere" o no l'oggetto, invertendone le normali */
  private boolean ot;
  /** Vettore posizione del punto dell'intersezione nello spazio trasformato (autocalcolato al primo richiamo di {@link #pointTransform()}) */
  protected Vector3D pt;

  /**
   * Prepara un nuovo oggetto {@link HitTransform HitTransform} deformando lo spazio di un {@link Hit Hit} già esistente.
   * @param h {@link Hit Hit} da deformare
   * @param r vero raggio da usare
   * @param tm matrice di trasformazione da applicare (se <code>null</code> non viene applicata trasformazione)
   * @param tx {@link Texture Texture} da usare (se <code>null</code> viene usata quella dell'oggetto originale)
   * @param ot nidica se l'oggetto &egrave; stato o no capovolto (in caso, ribalta le normali)
   */
  public HitTransform(Hit h, Ray r, TransformMatrix tm, Texture tx, boolean ot) {
    super(h.g, (r == null ? h.r : r));
    this.ht = h;
    this.h = h.h;
    this.t = h.t;
    if (tm == null) {
      this.tm = TransformMatrix.IDENTITY;
      this.tmid = 1.0;
    } else {
      this.tm = tm;
      this.tmid = Math.pow(tm.det(), -1.0 / 3.0);
    }
    this.tx = tx;
    this.ot = ot;
  }

  public Color color() {
    if (c == null) {
      if (tx == null)
	c = g.color(pointTransform());
      else
	c = tx.color(point());
    }
    return (c);
  }

  public Vector3D normal() {
    if (n == null) {
      if (tm == TransformMatrix.IDENTITY)
	n = g.normal(pointTransform());
      else
	n = tm.transformNormal(g.normal(pointTransform())).mul(tmid);
    }
    if (ot)                     // se l'oggetto è capovolto
      n = Vector3D.ORIGIN.sub(n); // ribalta la normale
    return (n);
  }

  public Vector3D pointTransform() {
    if (pt == null) {
      if (tm == TransformMatrix.IDENTITY)
	pt = point();
      else
	pt = tm.transformVector(point());
    }
    return (pt);
  }

  public double reflect() {
    if (Double.isNaN(rr)) {
      if (tx == null)
	rr = g.reflect(pointTransform());
      else
	rr = tx.reflect(point());
    }
    return (rr);
  }

}
