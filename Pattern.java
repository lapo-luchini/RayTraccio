/**
 * Pattern tridimensionale. <br>
 * Asegna a ogni punto dello spazio un valore <code>double</code> in [0.0,1.0).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract public class Pattern {
/**
 * Genera un campo scalare.
 * @return double valore nel punto richiesto [0.0,+1.0)
 * @param p vettore posizione nel campo
 */
abstract public double scalar(Vector p);
/**
 * Genera un campo vettoriale.
 * @return double[dim] valore nel punto richiesto [0.0,+1.0) per ogni dimensione
 * @param p vettore posizione nel campo
 * @param dim numero di dimensioni (massimo 5)
 */
abstract public double[] vectorial(Vector p, byte dim);
}
