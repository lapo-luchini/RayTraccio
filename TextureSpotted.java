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
 * Materiale sperimentale che macchietta un materiale dato (è brutto!).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureSpotted extends Texture {
	/** Colore da macchiettare */
  private Texture c;
	/** Prbabilità di macchiettamento */
  private double z;
  TextureSpotted(Texture a, double b) {
	c=a;
	z=b;
  }
  public Color color(Vector p) {
	if(p.mod()-Math.floor(p.mod())<z)
	  return(Color.BLACK);
	else
	  return(c.color(p));
  }
  public double reflect(Vector p) {
	return(c.reflect(p));
  }
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>TextureChecker[Texture[...],0.001]</code> <br>
 */
  public String toString() {
	return("TextureSpotted["+c+","+z+"]");
  }
}
