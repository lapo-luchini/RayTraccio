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
 * Scacchiera 3D di due sottomateriali. <br>
 * Lo spazio viene diviso un cubi unitari alternativamente di due sottomateriali diversi.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureChecker extends Texture {
	/** Array contenente i due sottomateriali */
  private Texture c[];
TextureChecker(Texture a, Texture b) {
	c = new Texture[2];
	c[0] = a;
	c[1] = b;
}
public Color color(Vector p) {
	return (c[ (((int) Math.floor(p.x)) ^ ((int) Math.floor(p.y)) ^ ((int) Math.floor(p.z))) & 1].color(p));
}
public double reflect(Vector p) {
	return (c[ (((int) Math.floor(p.x)) ^ ((int) Math.floor(p.y)) ^ ((int) Math.floor(p.z))) & 1].reflect(p));
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[Texture[...],Texture[...]]</code> <br>
 */
public String toString() {
	return ("TextureChecker[" + c[0] + "," + c[1] + "]");
}
}
