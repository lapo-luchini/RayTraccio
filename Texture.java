/**
 * Materiale tridimensionale. <br>
 * Questa classe definisce tutte i metodi necessari per la definizione di un materiale 3D.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract class Texture {
/**
 * Colore del materiale calcolato nel punto richiesto.
 * @param p vettore posizione del punto voluto
 * @return colore voluto
 */
abstract public Color color(Vector p);
/**
 * Coefficente di riflessione del materiale calcolato nel punto richiesto.
 * @param p vettore posizione del punto voluto
 * @return coefficente di riflessione voluto
 */
abstract public double reflect(Vector p);
}
