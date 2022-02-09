// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

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
