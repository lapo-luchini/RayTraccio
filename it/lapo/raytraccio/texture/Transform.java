package it.lapo.raytraccio.texture;
import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.TransformMatrix;
import it.lapo.raytraccio.Vector3D;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/TextureTransform.java,v 1.9 2002/02/23 16:41:17 lapo Exp $

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
 * Materiale ottenuto trasformandone un altro.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Transform extends Texture {

  /** Materiale da ridimensionare */
  private Texture c;
  /** Matrice di trasformazione */
  private TransformMatrix t;

  public Transform(Texture a, TransformMatrix b) {
    c = a;
    t = b.inv();
  }

  public Color color(Vector3D p) {
    return (c.color(t.transformVector(p)));
  }

  public double reflect(Vector3D p) {
    return (c.reflect(t.transformVector(p)));
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>TextureChecker[Texture[...],TransformMatrix[...]]</code> <br>
   */
  public String toString() {
    return ("TextureTransform[" + c + "," + t + "]");
  }

}
