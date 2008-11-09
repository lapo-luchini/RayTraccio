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

package it.lapo.raytraccio;

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
