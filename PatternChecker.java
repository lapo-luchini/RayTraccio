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
 * Campo scalare che vale <code>0.0</code> e <code>1.0</code> alternativamente in ogni cubo unitario.
 * @author: Lapo Luchini
 */
class PatternChecker extends Pattern {
public double scalar(Vector p) {
	return((((int)Math.floor(p.x))^((int)Math.floor(p.y))^((int)Math.floor(p.z)))&1);
}
public double[] vectorial(Vector p, byte dim) {
	double[] v=new double[dim];
	double nv=(((int)Math.floor(p.x))^((int)Math.floor(p.y))^((int)Math.floor(p.z)))&1;
	while(dim-->0)
		v[dim]=nv;
	return(v);
}
}
