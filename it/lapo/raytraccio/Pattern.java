// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/Pattern.java,v 1.7 2002/02/23 16:41:16 lapo Exp $

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
