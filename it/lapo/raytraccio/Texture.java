package it.lapo.raytraccio;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/Texture.java,v 1.9 2002/02/23 16:41:17 lapo Exp $

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
