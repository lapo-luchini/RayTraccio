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

package it.lapo.raytraccio.texture;
import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale formato da vari strati di altri materiali. <br>
 * I sotto-materiali vengono passati in ordine di "profondità": il primo passato
 * è il più esterno. <br>
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
   * Le figure sono contenute in un array la cui lunghezza è aumentata in modo dinamico
   * per ottimizzare spazio occupato e velocità di aggiunta (in caso manchi spazio
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
