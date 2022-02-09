// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.shape;
import it.lapo.raytraccio.EyeRays;
import it.lapo.raytraccio.Hit;
import it.lapo.raytraccio.Ray;
import it.lapo.raytraccio.ShapeTextured;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Piano infinito. <br>
 * Equazione: <code>&lt;x,y,z&gt;.n+d=0</code>
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Plane extends ShapeTextured {

  /** Vettore normale: definisce i coseni direttori del piano */
  private Vector3D n;
  /** Distanza del piano dall'origine (l'origine &egrave; quindi tenuta sul lato <b>esterno</b> del piano) */
  private double d;
  /** OTTIMIZAZIONE: origine dei dati cachati */
  private Vector3D b_o=new Vector3D(1E30, 1E30, 1E30);
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
  public Plane(Vector3D n, double d, Texture c) {
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

  public Vector3D normal(Vector3D p) {
    return (n);
  }

  public void overturn() {
    n = Vector3D.ORIGIN.sub(n);
    d = -d;
  }

  public void scale(Vector3D i) {
    n.x /= i.x;
    n.y /= i.x;
    n.z /= i.y;
  }

  public void translate(Vector3D i) {
    d += n.dot(i);
  }

  public double value(Vector3D p) {
    // ax+by+cz+d
    return (n.dot(p) + d);
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>Plane[Vector3D[1.0,0.0,0.0],3.0,TexturePlain[Color[1.0,0.0,0.0],0.0]]</code> <br>
   */
  public String toString() {
    return ("Plane[" + n + "," + d + "," + c + "]");
  }

}
