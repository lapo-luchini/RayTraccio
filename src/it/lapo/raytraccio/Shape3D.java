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
 * Figura tridimensionale. <br>
 * Questa classe definisce tutte i metodi necessari per la definizione di una figura 3D.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract public class Shape3D {

  /**
   * Colore della figura calcolato nel punto richiesto.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL COLOR"</code> su <code>System.err</code>
   * @param p vettore posizione del punto voluto
   * @return Colore {@link Color#BLACK nero}
   */
  public Color color(Vector3D p) {
    System.err.println("ILLEGAL COLOR");
    return (Color.BLACK);
  }

  /**
   * Calcola l'intersezione tra un dato raggio e la figura (versione possibilmente ottimizzata).
   * @param a il raggio voluto
   */
  abstract public Hit hit(EyeRays a);

  /**
   * Calcola l'intersezione tra un dato raggio e la figura.
   * @param a il raggio voluto
   */
  abstract public Hit hit(Ray a);

  /**
   * Normale alla figura nel punto richiesto.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL NORMAL"</code> su <code>System.err</code>
   * @param p vettore posizione del punto voluto
   * @return Versore {@link Vector3D#VERS_X X}
   */
  public Vector3D normal(Vector3D p) {
    System.err.println("ILLEGAL NORMAL");
    return (Vector3D.VERS_X);
  }

  /**
   * Capovolge la figura, scambiando interno ed esterno.
   */
  abstract public void overturn();

  /**
   * Coefficiente di riflessione della figura calcolato nel punto richiesto.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL REFLECT"</code> su <code>System.err</code>
   * @param p vettore posizione del punto voluto
   * @return <code>0.0</code>
   */
  public double reflect(Vector3D p) {
    System.err.println("ILLEGAL REFLECT");
    return (0.0);
  }

  /**
   * Ruota la figura secondo il vettore di rotazione dato. <br>
   * La figura viene ruotata prima intorno all'asse X, poi intorno a Y, infine intorno a Z.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL ROTATE"</code> su <code>System.err</code>
   * @param p vettore rotazione voluto (in gradi)
   */
  public void rotate(Vector3D i) {
    System.err.println("ILLEGAL ROTATE");
  }

  /**
   * Ridimensiona la figura secondo il vettore di ridimensionamento dato.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL SCALE"</code> su <code>System.err</code>
   * @param p vettore di scalamento voluto
   */
  public void scale(Vector3D i) {
    System.err.println("ILLEGAL SCALE");
  }

  /**
   * Assegna un materiale alla figura.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL TEXTURE"</code> su <code>System.err</code>
   * @param t materiale voluto
   */
  public void texture(Texture t) {
    System.err.println("ILLEGAL TEXTURE");
  }

  /**
   * Trasla la figura secondo il vettore di traslazione dato.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL TRANSLATE"</code> su <code>System.err</code>
   * @param p vettore di traslazione voluto
   */
  public void translate(Vector3D i) {
    System.err.println("ILLEGAL TRANSLATE");
  }

  /**
   * Valore della funzione generatrice della figura. <br>
   * @return Valori negativi all'interno, nulli sulla superficie e positivi all'esterno della figura.
   */
  abstract public double value(Vector3D p);

}
