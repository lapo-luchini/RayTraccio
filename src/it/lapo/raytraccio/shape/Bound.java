// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

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
