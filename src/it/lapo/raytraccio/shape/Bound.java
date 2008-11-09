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

import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.EyeRays;
import it.lapo.raytraccio.Hit;
import it.lapo.raytraccio.Ray;
import it.lapo.raytraccio.Shape3D;
import it.lapo.raytraccio.Vector3D;

/**
 * Figura virtuale utilizzata per ottimizzare la velocità di calcolo degli {@link Hit} su figure complesse. <br>
 * L'{@link Hit} viene eseguito prima sull'oggetto {@link #a} (che deve quindi essere una approssimazione per
 * eccesso dell'oggetto reale) e viene ripetuta sull'oggetto reale solo in caso che la prima abbia avuto successo. <br>
 * Questo produce un aumeto del tempo di calcolo per quei punti che colpiscono il bound (questo primo check &egrave; aggiuntivo
 * a quello necessario comunque sul vero oggetto), ma sveltisce di molto i calcoli quando il primo non viene colpito e
 * il secondo &egrave; particolarmente ostico da calcolare (sopratutto i {@link ShapePoly}). <br>
 * Tutte le altre funzioni richiamano le omonime funzioni sull'oggetto {@link #b}, tranne {@link #overturn} che ovviamente
 * non &egrave; definita.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Bound extends Shape3D {

  /** Oggetto di bound (approssimazione per eccesso) */
  private Shape3D a;
  /** Oggetto reale */
  private Shape3D b;
  /** Entità per la statistica */
  private static byte stat=ShapeStats.register("Bound");

  /**
   * Crea un oggetto {@link Bound} con parametri dati.
   * @param a oggetto"virtuale" usato come prima approssimazione
   * @param b oggetto "vero"
   */
  public Bound(Shape3D a, Shape3D b) {
    this.a=a;
    this.b=b;
  }

  public Color color(Vector3D p) {
    return(a.color(p));
  }

  public Hit hit(EyeRays r) {
    Hit u=b.hit(r);
    ShapeStats.count(stat, ShapeStats.TYPE_EYERAY);
    if(u.h) {
      u=a.hit(r);
      if(u.h)
	ShapeStats.count(stat, ShapeStats.TYPE_HIT);
    } else
      ShapeStats.count(stat, ShapeStats.TYPE_CACHEDRAY);
    return(u);
  }

  public Hit hit(Ray r) {
    Hit u=b.hit(r);
    ShapeStats.count(stat, ShapeStats.TYPE_RAY);
    if(u.h) {
      u=a.hit(r);
      if(u.h)
	ShapeStats.count(stat, ShapeStats.TYPE_HIT);
    } else
      ShapeStats.count(stat, ShapeStats.TYPE_CACHEDRAY);
    return(u);
  }

  public Vector3D normal(Vector3D p) {
    return(a.normal(p));
  }

  public void overturn() {
    System.err.println("ILLEGAL OVERTURN");
  }

  public double reflect(Vector3D p) {
    return(a.reflect(p));
  }

  public double value(Vector3D p) {
    return(a.value(p));
  }

}
