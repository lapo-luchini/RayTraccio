// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/Shape3D.java,v 1.8 2001/04/27 08:50:16 lapo Exp $

// This file is part of RayTraccio.
//
// RayTraccio is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// RayTraccio is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RayTraccio; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

/**
 * Figura tridimensionale. <br>
 * Questa classe definisce tutte i metodi necessari per la definizione di una figura 3D.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
abstract class Shape3D {

  /**
   * Colore della figura calcolato nel punto richiesto.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL COLOR"</code> su <code>System.err</code>
   * @param p vettore posizione del punto voluto
   * @return Colore {@link Color#BLACK nero}
   */
  public Color color(Vector p) {
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
   * @return Versore {@link Vector#VERS_X X}
   */
  public Vector normal(Vector p) {
    System.err.println("ILLEGAL NORMAL");
    return (Vector.VERS_X);
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
  public double reflect(Vector p) {
    System.err.println("ILLEGAL REFLECT");
    return (0.0);
  }

  /**
   * Ruota la figura secondo il vettore di rotazione dato. <br>
   * La figura viene ruotata prima intorno all'asse X, poi intorno a Y, infine intorno a Z.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL ROTATE"</code> su <code>System.err</code>
   * @param p vettore rotazione voluto (in gradi)
   */
  public void rotate(Vector i) {
    System.err.println("ILLEGAL ROTATE");
  }

  /**
   * Ridimensiona la figura secondo il vettore di ridimensionamento dato.
   * Se non viene sovrascritto questo metodo scrive <code>"ILLEGAL SCALE"</code> su <code>System.err</code>
   * @param p vettore di scalamento voluto
   */
  public void scale(Vector i) {
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
  public void translate(Vector i) {
    System.err.println("ILLEGAL TRANSLATE");
  }

  /**
   * Valore della funzione generatrice della figura. <br>
   * @return Valori negativi all'interno, nulli sulla superficie e positivi all'esterno della figura.
   */
  abstract public double value(Vector p);

}