// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/ShapePoly.java,v 1.7 2002/02/23 16:41:16 lapo Exp $

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

package it.lapo.raytraccio.shape;

//TODO: lavorarci su
//TODO eventualmente gestire forze che si smorzano con altre leggi che non l'inverso el quadrato della distanza

import it.lapo.raytraccio.Color;
import it.lapo.raytraccio.EyeRays;
import it.lapo.raytraccio.Hit;
import it.lapo.raytraccio.Ray;
import it.lapo.raytraccio.Shape3D;
import it.lapo.raytraccio.Texture;
import it.lapo.raytraccio.Vector3D;

/**
 * Tipo blob sperimentale, non ancora sviluppato.
 * 
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Blob extends Shape3D {
    
    private class Focus {
        public Vector3D center;
        public double strength;
    }

    protected java.util.Vector<Focus> foci;

    private Texture c;

    private double r;

    public Blob(double d, Texture b) {
        c = b;
        r = d;
        foci = new java.util.Vector<Focus>();
    }

    public void add(Vector3D center, double strength) {
        Focus t = new Focus();
        t.center = center;
        t.strength = strength;
        foci.add(t);
    }

    public Color color(Vector3D p) {
        return c.color(p);
    }

    public Hit hit(EyeRays a) {
        return hit((Ray) a);
    }

    public Hit hit(Ray a) {
        //TODO
        //SUM(f:foci,f.s/(p-f.c))
        //k=f1f/((px-f1x)²+(py-f1y)²+(pz-f1z)²)+f2f/((px-f2x)²+(py-f2y)²+(pz-f2z)²)
        //k=f1f*f1l(p)+f2f*f2l(p) v p(t)
        Vector3D p = new Vector3D(0.0, 0.0, 0.0);
        p.x = a.o.x + t * a.c.x;
        p.y = a.o.y + t * a.c.y;
        p.z = a.o.z + t * a.c.z;
    }

    public Vector3D normal(Vector3D p) {
        //TODO
    }

    public void overturn() {
    }

    public double reflect(Vector3D p) {
        return c.reflect(p);
    }

    public void scale(Vector3D i) {
    }

    public void texture(Texture t) {
        c = t;
    }

    public void translate(Vector3D i) {
    }

    public double value(Vector3D p) {
        double u = 0.0;
        for(Focus f : foci)
            u += f.strength / p.sub(f.center).mod2(); //TOFIX gestire distanza 0
        return u;
    }

}
