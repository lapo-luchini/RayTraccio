package it.lapo.raytraccio.texture;
import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/TexturePlain.java,v 1.9 2002/02/23 16:41:17 lapo Exp $

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
 * Materiale di colore e coefficiente di riflessione uniformi.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Plain extends Texture {

  /** Colore */
  private Color c;
  /** Coefficiente di riflessione */
  private double r;
  /** Texture standard bianca non riflettente */
  public static Plain WHITE = new Plain(Color.WHITE, 0.0);

  public Plain(Color a, double b) {
    c = a;
    r = b;
  }

  public Color color(Vector3D p) {
    return (c);
  }

  public double reflect(Vector3D p) {
    return (r);
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>TextureChecker[Color[1.0,0.0,0.0],0.7]</code> <br>
   */
  public String toString() {
    return ("TexturePlain[" + c + "," + r + "]");
  }

}
