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
 * Pattern tridimensionale. <br>
 * Assegna a ogni punto dello spazio un valore <code>double</code> in [0.0,1.0].
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract public class Pattern {

  /**
   * Genera un campo scalare.
   * @return double valore nel punto richiesto [0.0,+1.0]
   * @param p vettore posizione nel campo
   */
  abstract public double scalar(Vector3D p);
  /**
   * Genera un campo vettoriale.
   * @return double[dim] valore nel punto richiesto [0.0,+1.0] per ogni dimensione
   * @param p vettore posizione nel campo
   * @param dim numero di dimensioni (massimo 5)
   */
  abstract public double[] vectorial(Vector3D p, byte dim);

}