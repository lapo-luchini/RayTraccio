// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

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
