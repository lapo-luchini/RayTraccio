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
 * Materiale tridimensionale. <br>
 * Questa classe definisce tutte i metodi necessari per la definizione di un materiale 3D.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract public class Texture {

  /**
   * Colore del materiale calcolato nel punto richiesto.
   * @param p vettore posizione del punto voluto
   * @return colore voluto
   */
  abstract public Color color(Vector3D p);

  /**
   * Coefficiente di riflessione del materiale calcolato nel punto richiesto.
   * @param p vettore posizione del punto voluto
   * @return coefficiente di riflessione voluto
   */
  abstract public double reflect(Vector3D p);

}
