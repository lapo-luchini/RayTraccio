// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/Scene.java,v 1.9 2001/04/27 08:50:16 lapo Exp $

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

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * Singola scena da visualizzare. <br>
 * Contiene tutti i parametri che definiscono il 'mondo virtuale' che si vuole rappresentare:
 * una figura, un punto di vista, delle luci.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Scene {

  /** Una singola figura (questo non è un limite in quanto esiste la figura {@link CSG_Union}) */
  private Shape3D s;
  /** Array di luci */
  private Light l[] = new Light[3];
  /** Numero di luci contenute nell'array */
  private int numl = 0;
  /** Vettore posizione dell'<i>occhio</i> dell'osservatore */
  protected Vector eye;
  /** Vettore posizione del punto 'guardato' */
  protected Vector c;
  /** Vettore 'orizzontale' */
  protected Vector h;
  /** Vettore 'verticale' */
  protected Vector v;

  /**
   * Crea una scena dati gli elementi.
   * @params a oggetto contenuto nella scena
   * @params ex posizione dell'<i>occhio</i>
   * @params ox punto 'guardato'
   * @params hx vettore 'orizzonte'
   * @params ratio proporzione orizzontale/vertiale
   */
  Scene(Shape3D a, Vector ex, Vector ox, Vector hx, double ratio) {
    s = a;
    eye = ex;
    c = ox;
    if (hx.dot(ox.sub(ex)) != 0.0)
      h = hx.sub(ox.sub(ex).mul(1.0 / (hx.dot(ox.sub(ex)))));
    else
      h = hx;
    v = ox.sub(ex).vers().cross(h).mul(1.0 / ratio);
    //System.out.println("SCENE INIT");
    //System.out.println("E: "+eye);
    //System.out.println("C: "+c);
    //System.out.println("H: "+hx+" DOT:"+(1.0/hx.dot(ox.sub(ex)))+" -> "+h);
    //System.out.println("V: "+v);
  }

  /**
   * Aggiunge una luce alla scena. <br>
   * Le luci sono contenute in un array la cui lunghezza è aumentata in modo dinamico
   * per ottimizzare spazio occupato e velocità di aggiunta (in caso manchi spazio
   * l'array viene aumentato del 50%+1).
   * @param a la luce da aggiungere
   */
  public void addLight(Light a) {
    if (numl == l.length) {
      Light old[] = l;
      l = new Light[ (l.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
      System.arraycopy(old, 0, l, 0, numl);
    }
    l[numl++] = a;
  }

  /**
   * Calcola il colore intersecato dal raggio dato. <br>
   * Identico a {@link #hit(Ray) hit(Ray)} ma richiama i metodi
   * {@link Shape3D#hit(EyeRays) hit(EyeRays)} delle figure, spesso ottimizzati.
   * @param a raggio da intersecare
   * @return colore del punto colpito
   */
  public Color hit(EyeRays a) {
    Hit h = s.hit(a);
    Color c;
    if (!h.h)
      c = Color.BLACK; // mettere qua il cielo
    else {
      if (h.reflect() > 0.999)
	c = Color.BLACK;
      else
	c = lighting(h.point(), h.normal());
      if (h.reflect() > 0.001) {
	Color nc = Color.BLACK; // anche qua il cielo
	Ray rr = new Ray(h.point(), Vector.ORIGIN /*una cosa a caso tanto c lo sovrascrivo*/);
	rr.c = a.c.mirror(h.normal()); // sovrascrivo c: ok, ok, è un po' sporco!!
	Hit hs = s.hit(rr);
	if (hs.h && (hs.t > 1E-10))
	  nc = hs.color().mul(lighting(hs.point(), hs.normal()));
	c = c.mul(1.0 - h.reflect()).add(nc.mul(h.reflect()));
      }
      c = h.color().mul(c); // messo dopo per filtrare anche lo specchio
    }
    return (c);
  }

  /**
   * Calcola il colore intersecato dal raggio dato. <br>
   * Sono gestite le prime riflessioni e il 'cielo' (quando nessun oggetto viene colpito
   * dal raggio) viene considerato nero.
   * @param a raggio da intersecare
   * @return colore del punto colpito
   */
  public Color hit(Ray a) {
    Hit h = s.hit(a);
    Color c;
    if (!h.h)
      c = Color.BLACK; // mettere qua il cielo
    else {
      if (h.reflect() > 0.999)
	c = Color.BLACK;
      else
	c = lighting(h.point(), h.normal());
      if (h.reflect() > 0.001) {
	Color nc = Color.BLACK; // anche qua il cielo
	Ray rr = new Ray(h.point(), Vector.ORIGIN /*una cosa a caso tanto c lo sovrascrivo*/);
	rr.c = a.c.mirror(h.normal()); // sovrascrivo c: ok, ok, è un po' sporco!!
	Hit hs = s.hit(rr);
	if (hs.h && (hs.t > 1E-10))
	  nc = hs.color().mul(lighting(hs.point(), hs.normal()));
	c = c.mul(1.0 - h.reflect()).add(nc.mul(h.reflect()));
      }
      c = h.color().mul(c); // messo dopo per filtrare anche lo specchio
    }
    return (c);
  }

  /**
   * Calcola l'illuminazione generata da tutte le luci. <br>
   * Quando la scena non contiene luci viene calcolata un'illuminazione massima uniforme.
   * @param p vettore posizione del punto voluto
   * @param n vettore normale nel punto voluto
   * @return colore dell'illuminazione nel punto voluto
   */
  public Color lighting(Vector p, Vector n) {
    if (numl == 0)         // se non ci sono luci
      return (Color.WHITE); // flat shading
    Ray rl;
    Hit hl;
    Color c = Color.BLACK;
    double direz;
    for (int i = 0; i < numl; i++) {
      rl = new Ray(p, l[i].o);
      direz = n.dot(rl.c.vers());
      if (direz > 0.0) {
	hl = s.hit(rl);
	if ((!hl.h) || (hl.t >= 1.0))
	  c = c.add(l[i].c.mul(direz).mul(l[i].p / rl.c.mod2()));
      }
    }
    return (c);
  }

}