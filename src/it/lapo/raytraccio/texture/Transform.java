// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

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
