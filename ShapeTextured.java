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
 * Insert the type's description here.
 * Creation date: (27/02/2001 22:35:12)
 * @author:
 */
abstract class ShapeTextured extends Shape3D {
	/** Materiale della figura */
	protected Texture c;
/**
 * Crea una figura che usa il dato materiale.
 * @param c materiale
 */
public ShapeTextured(Texture c) {
	this.c = c;
}
public Color color(Vector p) {
	return (c.color(p));
}
public double reflect(Vector p) {
	return(c.reflect(p));
}
public void texture(Texture c) {
	this.c = c;
}
}
