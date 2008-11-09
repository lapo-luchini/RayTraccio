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
import it.lapo.raytraccio.TransformMatrix;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale ottenuto trasformandone un altro.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Transform extends Texture {

  /** Materiale da ridimensionare */
  private Texture c;
  /** Matrice di trasformazione */
  private TransformMatrix t;

  public Transform(Texture a, TransformMatrix b) {
    c = a;
    t = b.inv();
  }

  public Color color(Vector3D p) {
    return (c.color(t.transformVector(p)));
  }

  public double reflect(Vector3D p) {
    return (c.reflect(t.transformVector(p)));
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>TextureChecker[Texture[...],TransformMatrix[...]]</code> <br>
   */
  public String toString() {
    return ("TextureTransform[" + c + "," + t + "]");
  }

}
