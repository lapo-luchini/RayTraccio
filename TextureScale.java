// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header$

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
 * Materiale ottenuto ridimensionandone un altro.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureScale extends Texture {
	/** Materiale da ridimensionare */
  private Texture c;
  /** Valore di ridimensionamento */
  private double z;
TextureScale(Texture a, double b) {
	c = a;
	z = 1.0 / b;
}
public Color color(Vector p) {
	return (c.color(p.mul(z)));
}
public double reflect(Vector p) {
	return (c.reflect(p.mul(z)));
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[Texture[...],3.0]</code> <br>
 */
public String toString() {
	return ("TextureScale[" + c + "," + 1.0 / z + "]");
}
}
