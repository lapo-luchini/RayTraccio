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
 * Modifica un'alto pattern modificando il suo range comprimendolo agli estremi <code>0.5+0.5*sin((PI/2)*(x-0.5))</code>.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternSine extends Pattern {
	// pattern da modificare
	Pattern pat;
public PatternSine(Pattern p) {
	pat=p;
}
public double scalar(Vector p) {
	return(0.5+0.5*Math.sin(Math.PI*(pat.scalar(p)-0.5)));
}
public double[] vectorial(Vector p, byte dim) {
	double u[]=pat.vectorial(p, dim);
	while(dim-->0)
		u[dim]=Math.sin(Math.PI*u[dim]);
	return(u);
}
}
