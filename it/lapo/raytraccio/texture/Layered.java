// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/TextureLayered.java,v 1.7 2002/02/23 16:41:17 lapo Exp $

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

package it.lapo.raytraccio.texture;
import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale formato da vari strati di altri materiali. <br>
 * I sotto-materiali vengono passati in ordine di "profondit�": il primo passato
 * � il pi� esterno. <br>
 * Si suppone che tutti i materiali tranne l'ultimo abbiano una trasparenza
 * da qualche parte (alpha &lt; 1.0), in caso contrario i materiali 'sottostanti'
 * resterebbero inutilizzati (non produrrebbe comunque errori, solo uno 'spreco'). <br>
 * Il coefficiente di riflessione viene dal primo materiale.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Layered extends Texture {

  /** Array di sotto-texture */
  protected Texture[] t = new Texture[2];
  /** Numero di texture contenute nell'array */
  protected int n = 0;

  /**
   * Aggiunge una sotto-texture. <br>
   * Le figure sono contenute in un array la cui lunghezza � aumentata in modo dinamico
   * per ottimizzare spazio occupato e velocit� di aggiunta (in caso manchi spazio
   * l'array viene aumentato del 50%+1).
   * @param a {@link Texture} da aggiungere
   */
  public void add(Texture a) {
    if (n == t.length) {
      Texture old[] = t;
      t = new Texture[ (t.length * 3) / 2 + 1 ]; // dimensione ispirata da ArrayList.java
      System.arraycopy(old, 0, t, 0, n);
    }
    t[n++] = a;
  }

  public Color color(Vector3D p) {
    if(n<1)
      throw new RuntimeException("no layers defined");
    Color u=t[0].color(p), tmp;
    double frac=1.0-u.a;
    u=u.mul(u.a);
    for(int i=1; (i<n)&&(frac>1E-4); i++) {
      tmp=t[i].color(p);
      u=u.addU(tmp.mul(frac*tmp.a));
      frac*=1.0-tmp.a;
    }
    u.a=1.0-frac;
    return(u);
  }

  /**
   * Ottimizza l'occupazione di memoria (da usare dopo aver aggiunto tutti i valori).
   */
  public void optimize() {
    if (t.length > n) {
      Texture old[] = t;
      t = new Texture[n];
      System.arraycopy(old, 0, t, 0, n);
    }
  }

  public double reflect(Vector3D p) {
    if(n<1)
      throw new RuntimeException("no layers defined");
    return(t[0].reflect(p));
  }

}