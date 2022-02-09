// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.texture;

import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale ottenuto ridimensionandone un altro.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Scale extends Texture {

  /** Materiale da ridimensionare */
  private Texture c;
  /** Valore di ridimensionamento */
  private double z;

  public Scale(Texture a, double b) {
    c = a;
    z = 1.0 / b;
  }

  public Color color(Vector3D p) {
    return (c.color(p.mul(z)));
  }

  public double reflect(Vector3D p) {
    return (c.reflect(p.mul(z)));
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>TextureChecker[Texture[...],3.0]</code> <br>
   */
  public String toString() {
    return ("TextureScale[" + c + "," + 1.0 / z + "]");
  }

}
