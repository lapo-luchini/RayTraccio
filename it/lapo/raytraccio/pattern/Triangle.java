package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/PatternTriangle.java,v 1.8 2002/02/23 16:41:16 lapo Exp $

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
 * Modifica un altro pattern modificando il suo range <code>1.0-2.0*abs(x-0.5)</code>.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class Triangle extends Pattern {

  /** pattern da modificare */
  Pattern pat;

  public Triangle(Pattern p) {
    pat=p;
  }

  public double scalar(Vector3D p) {
    double u=pat.scalar(p);
    if(u>0.5)
      u=1.0-u;
    u*=2;
    return(u);
  }

  public double[] vectorial(Vector3D p, byte dim) {
    double u[]=pat.vectorial(p, dim);
    while(dim-->0) {
      if(u[dim]>0.5)
	u[dim]=1.0-u[dim];
      u[dim]*=2;
    }
    return(u);
  }

}