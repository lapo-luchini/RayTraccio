// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio.texture;

import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale di colore e coefficiente di riflessione uniformi.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Plain extends Texture {

  /** Colore */
  private Color c;
  /** Coefficiente di riflessione */
  private double r;
  /** Texture standard bianca non riflettente */
  public static Plain WHITE = new Plain(Color.WHITE, 0.0);

  public Plain(Color a, double b) {
    c = a;
    r = b;
  }

  public Color color(Vector3D p) {
    return (c);
  }

  public double reflect(Vector3D p) {
    return (r);
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>TextureChecker[Color[1.0,0.0,0.0],0.7]</code> <br>
   */
  public String toString() {
    return ("TexturePlain[" + c + "," + r + "]");
  }

}
