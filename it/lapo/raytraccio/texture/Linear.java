// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/it/lapo/raytraccio/texture/Linear.java,v 1.1 2004/12/16 23:09:56 lapo Exp $

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

package it.lapo.raytraccio.texture;

import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Materiale che mixa due materiali secondo un pattern dato, sfumando dal primo
 * al secondo in modo lineare.
 * 
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Linear extends Texture {

    /** Array contenente i due sottomateriali */
    private Texture c[];

    /** Pattern usato per mixare i due sottomateriali */
    private Pattern pat;

    public Linear(Texture a, Texture d, Pattern p) {
        c = new Texture[2];
        c[0] = a;
        c[1] = d;
        pat = p;
    }

    public Color color(Vector3D p) {
        double r = pat.scalar(p);
        return c[0].color(p).mul(r).addU(c[1].color(p).mul(1.0 - r));
    }

    public double reflect(Vector3D p) {
        double r = pat.scalar(p);
        return c[0].reflect(p) * r + c[1].reflect(p) * (1.0 - r);
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio:
     * <code>TextureLinear[Pattern[...],Texture[...],Texture[...]]</code><br>
     */
    public String toString() {
        return "TextureLinear[" + pat + "," + c[0] + "," + c[1] + "]";
    }

}
