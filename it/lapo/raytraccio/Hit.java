// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/Hit.java,v 1.10 2002/02/23 16:41:16 lapo Exp $

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

package it.lapo.raytraccio;

/**
 * Rappresenta il punto di intersezione in cui un {@link Ray raggio} ha colpito una {@link Shape3D figura}.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Hit {

  /** <code>true</code> se c'&egrave; stata effettivamente un intersezione valida, <code>false</code> altrimenti */
  public boolean h = false;
  /** Coordinata parametrica <code>t</code> del raggio dove &egrave; avvenuta l'intersezione */
  public double t;
  /** Raggio che ha generato l'intersezione */
  public Ray r;
  /** Figura che ha generato l'intersezione */
  public Shape3D g;
  /** Vettore posizione del punto dell'intersezione (autocalcolato al primo richiamo di {@link #point()}) */
  protected Vector3D p = null;
  /** Vettore normale della superficie nel punto colpito (autocalcolato al primo richiamo di {@link #normal()}) */
  protected Vector3D n = null;
  /** Colore della superficie nel punto colpito (autocalcolato al primo richiamo di {@link #color()}) */
  protected Color c = null;
  /** Coefficiente di riflessione della superficie nel punto colpito (autocalcolato al primo richiamo di {@link #reflect()}) */
  protected double rr = Double.NaN;

  /**
   * Prepara un nuovo oggetto {@link Hit}, pronto a ricevere intersezioni.
   * @param a figura intersecata
   * @param b raggio intersecante
   */
  public Hit(Shape3D a, Ray b) {
    g = a;
    r = b;
  }

  /**
   * Aggiunge una possibile intersezione. <br>
   * Vengono scartate quelle con coordinata non positiva (si trovano prima del punto di partenza del raggio)
   * e quelle con coordinata maggiore a quella gi� presente (viene quindi mantenuto solo la <i>prima</i>
   * intersezione del raggio, cosa che andr� cambiata se si vogliono supportare le rifrazioni semplificate
   * senza indice di rifazione).<br>
   * Se l'intersezione � accettata il valore {@link #h h} viene posto <code>true</code> e il
   * valore {@link #t t} prende il valore passato.
   * @param a cordinata parametrica della possibile intersezione
   */
  public void addT(double a) {
    if (a > 1E-10) { // se � un hit "davanti"
      if (h) {        // se c'era gi� un hit
	if (a < t)     // se questo � pi� vicino
	  t = a;        // questo � il nuovo hit
      } else          // altrimenti
	t = a;         // questo � il nuovo hit
      h = true;
    }
  }

  /**
   * Colore della figura nel punto intersecato. <br>
   * Il valore viene cachato nel campo {@link #c c}, inizialmente <code>null</code>.
   * @return oggetto {@link Color} contenete il colore desiderato
   */
  public Color color() {
    if (c == null)
      c = g.color(point());
    return (c);
  }

  /**
   * Normale della figura nel punto intersecato. <br>
   * Il valore viene cachato nel campo {@link #n n}, inizialmente <code>null</code>.
   * @return oggetto {@link Vector3D} contenete la normale desiderata
   */
  public Vector3D normal() {
    if (n == null)
      n = g.normal(point());
    return (n);
  }

  /**
   * Posizione del punto intersecato. <br>
   * Il valore viene cachato nel campo {@link #p p}, inizialmente <code>null</code>.
   * @return oggetto {@link Vector3D} contenete la posizione desiderata
   */
  public Vector3D point() {
    if (p == null)
      p = r.point(t);
    return (p);
  }

  /**
   * Coefficiente di riflessione della figura nel punto intersecato. <br>
   * Il valore viene cachato nel campo {@link #rr rr}, inizialmente <code>NaN</code>.
   * @return valore <code>double</code> contenete il coefficiente desiderato
   */
  public double reflect() {
    if (Double.isNaN(rr))
      rr = g.reflect(point());
    return (rr);
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>Hit[false]</code> <br>
   * Esempio: <code>Hit[true,t:0.234,g:Quadric@1234,r:Ray@5678]</code>
   */
  public String toString() {
    return ("Hit[" + h + (h ? ",t:" + t + ",g:" + g + ",r:" + r : "") + "]");
  }

}