// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/PatternStripes.java,v 1.7 2001/04/27 08:50:16 lapo Exp $

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
 * Campo scalare che vale <code>0.0</code> e <code>1.0</code> alternativamente in strato unitario lungo l'asse X.
 * @author: Lapo Luchini
 */
class PatternStripes extends Pattern {

  public double scalar(Vector p) {
    return(((int)Math.floor(p.x))&1);
  }

  public double[] vectorial(Vector p, byte dim) {
    double[] v=new double[dim];
    double nv=((int)Math.floor(p.x))&1;
    while(dim-->0)
      v[dim]=nv;
    return(v);
  }

}
