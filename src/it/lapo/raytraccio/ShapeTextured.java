// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio;

/**
 * Figura astratta che definisce delle primitive per figure con {@link Texture}.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract public class ShapeTextured extends Shape3D {
  /** Materiale della figura */
  protected Texture c;

  /**
   * Crea una figura che usa il dato materiale.
   * @param c materiale
   */
  public ShapeTextured(Texture c) {
    this.c = c;
  }

  public Color color(Vector3D p) {
    return (c.color(p));
  }

  public double reflect(Vector3D p) {
    return(c.reflect(p));
  }

  public void texture(Texture c) {
    this.c = c;
  }

}
