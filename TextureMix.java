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
 * Mix di due sottomateriali. <br>
 * Il colore viene calcolato moltiplicando il colore dei due sottomateriali
 * per il relativo coefficente.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureMix extends Texture {
	/** Array contenente i due sottomateriali */
  private Texture c[];
	/** Array contenente i valori di frazione del sottomateriale rispettivo */
  private double v[];
TextureMix(Texture a, double b, Texture d, double e) {
	c = new Texture[2];
	c[0] = a;
	c[1] = d;
	v = new double[2];
	v[0] = b / (b + e);
	v[1] = e / (b + e);
}
public Color color(Vector p) {
	return (c[0].color(p).mul(v[0]).addU(c[1].color(p).mul(v[1])));
}
public double reflect(Vector p) {
	return (c[0].reflect(p) * v[0] + c[1].reflect(p) * v[1]);
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[0.3,Texture[...],0.7,Texture[...]]</code> <br>
 */
public String toString() {
	return ("TextureMix[" + c[0] + "," + v[0] + "," + c[1] + "," + v[1] + "]");
}
}
