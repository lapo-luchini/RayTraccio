package it.lapo.raytraccio;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/Light.java,v 1.9 2002/02/23 16:41:16 lapo Exp $

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
 * Luce puntiforme.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Light {

  /** Vettore posizione */
  protected Vector3D o;
  /** Colore della luce (alla distanza <code>p</code>) */
  protected Color c;
  /** Potenza della luce (distanza alla quale usare il colore <code>c</code> invariato) */
  protected double p;

  /**
   * Crea una luce puntiforme cno parametri dati. <br>
   * @param o Vettore posizione
   * @param c Colore
   * @param p Potenza
   */
  Light(Vector3D o, Color c, double p) {
    this.o = o;
    this.c = c;
    this.p = p;
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>Light[Vector3D[1.0,2.0,3.0],Color[1.0,0.0,0.0],5.0]</code> <br>
   */
  public String toString() {
    return ("Light[" + o + "," + c + "," + p + "]");
  }

}
