package it.lapo.raytraccio;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/EyeRays.java,v 1.9 2002/02/23 16:41:16 lapo Exp $

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
 * Genera i raggi visuali (ottimizzati) che scandiscono un'area rettangolare. <br>
 * L'origine dei raggi &egrave; sempre la stessa (l'<i>occhio</i> dell'osservatore).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class EyeRays extends Ray {

  /** Punto di angolo in alto a sinistra */
  public Vector3D ci;
  /** Vettore di delta orizzontale (U) */
  public Vector3D h;
  /** Vettore di delta verticale (V) */
  public Vector3D v;
  /** Vettore che scorre le V a U minima */
  public Vector3D d;
  /** Contatore di riga */
  public int i;
  /** Contatore globale */
  public int cnt;
  /** Dimensione di riga */
  protected int maxx;
  /** Usato da Specific Ray per non creare un oggetto ogni volta */
  private Ray sr;

  /**
   * Imposta l'oggetto per generare i raggi richiesti.
   * @param o vettore posizione dell'origine dei raggi (l'<i>occhio</i>)
   * @param d vettore posizione del punto 'guardato'
   * @param h vettore 'orizzonte' (indica la direzione orizzontale ed &egrave; lungo quanto la larghezza voluta dell'immagine)
   * @param v vettore verticale (indica la direzione verticale ed &egrave; lungo quanto l'altezza voluta dell'immagine)
   * @param x numero di punti in direzione orizzontale
   * @param y numero di punti in direzione verticale
   */
  EyeRays(Vector3D o, Vector3D d, Vector3D h, Vector3D v, int x, int y) {
    super(o, d.sub(h.mul(0.5)).sub(v.mul(0.5)));
    this.h = h.mul(1.0 / x);
    this.v = v.mul(1.0 / y);
    this.c.addU(this.h.mul(0.5)).addU(this.v.mul(0.5));
    this.ci = new Vector3D(this.c);
    this.d = new Vector3D(this.c);
    maxx = x;
    i = cnt = 0;
    sr = new Ray(o, d);
  }

  /**
   * Calcola il prossimo raggio (l'area viene scandita prima in orizzontale e poi in verticale).
   * @return il prossimo raggio
   */
  public void next() {
    if (i == maxx - 1) {
      i = 0;
      d.addU(v);
      c = new Vector3D(d); // per usare dopo l'operatore unario
    } else {
      c.addU(h);
      i++;
    }
    cnt++;
  }

  /**
   * Calcola un raggio specifico date le coordinate.
   * @param x coordinata orizzontale del punto di arrivo richiesto
   * @param y coordinata verticale del punto di arrivo richiesto
   * @return il raggio richiesto
   */
  public Ray specificRay(double x, double y) {
    sr.c=ci.add(h.mul(x)).addU(v.mul(y));
    return(sr);
  }

}
