// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>

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
// along with Foobar; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

// This file is $Revision$, released by $Author$ on $Date$

/**
 * Materiale di colore e coefficente di riflessione uniformi.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TexturePlain extends Texture {
	/** Colore */
  private Color c;
	/** Coefficente di riflessione */
  private double r;
	/** Texture standard bianca non riflettente */
  public static TexturePlain WHITE = new TexturePlain(Color.WHITE, 0.0);
TexturePlain(Color a, double b) {
	c = a;
	r = b;
}
public Color color(Vector p) {
	return (c);
}
public double reflect(Vector p) {
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