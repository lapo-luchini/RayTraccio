// RayTraccio ray-tracing library Copyright (c) 2001-2008 Lapo Luchini <lapo@lapo.it>

// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

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

import java.util.ArrayList;
import java.util.TreeMap;

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

    protected ArrayList<Focus> foci;

    private Texture c;

    private double thr;

    /** Entità per la statistica */
    private static byte stat = ShapeStats.register("Blob");

    public Blob(double threshold, Texture tex) {
        c = tex;
        thr = threshold;
        foci = new ArrayList<Focus>();
    }

    public void add(Vector3D center, double strength) {
        Focus t = new Focus();
        t.center = center;
        t.strength = strength;
        foci.add(t);
    }

    /**
     * Ottimizza l'occupazione di memoria (da usare dopo aver aggiunto tutti i
     * valori).
     */
    public void optimize() {
        foci.trimToSize();
    }

    public Color color(Vector3D p) {
        return c.color(p);
    }

    public Hit hit(EyeRays a) {
        return hit((Ray) a);
    }

    public Hit hit(Ray a) {
        // TODO
        // SUM(f:foci,f.s/(p-f.c))
        // k=f1f/((px-f1x)²+(py-f1y)²+(pz-f1z)²)+f2f/((px-f2x)²+(py-f2y)²+(pz-f2z)²)
        // k=f1f*f1l(p)+f2f*f2l(p) v p(t)
        /*
         * Vector3D p = new Vector3D(0.0, 0.0, 0.0); p.x = a.o.x + t * a.c.x;
         * p.y = a.o.y + t * a.c.y; p.z = a.o.z + t * a.c.z;
         */
        ShapeStats.count(stat, ShapeStats.TYPE_RAY);
        TreeMap<Double, Focus> max = new TreeMap<Double, Focus>();
        double dist;
        for (Focus f : foci) {
            dist = a.dist(f.center);
            if (dist >= 1E-10) // se è "davanti"
                max.put(new Double(dist), f);
            //System.out.println("Dist " + dist_current + '/' + dist_closest);
        }
        Hit u = new Hit(this, a);
        double x, y, x1, x2, y1, y2;
        x2 = 0.0; y2 = value(a.point(x2));
        for(Double d : max.keySet()) { // abbiamo almeno un focus di fronte
            // Regula Falsi
            x1 = x2; y1 = y2;
            x2 = d.doubleValue(); y2 = value(a.point(x2));
            if (y1 * y2 < 0.0) { // x2 is already "inside", the first zero gotta be inside
                //System.out.println("Isx=" + x1 + "," + y1 + " Idx=" + x2 + "," + y2);
                int n;
                for (n = 0; (n < 10000) && (Math.abs(y1) >= 1E-10)
                        && (Math.abs(y2) >= 1E-10); ++n) {
                    x = x1 - y1 * (x1 - x2) / (y1 - y2);
                    y = value(a.point(x));
                    if (y * y1 > 0) {
                        x1 = x;
                        y1 = y;
                    } else {
                        x2 = x;
                        y2 = y;
                    }
                }
                //System.out.println("N=" + n + " Tsx=" + x1 + "," + y1 + " Tdx=" + x2 + "," + y2);
                //System.out.print("," + n);
                if (Math.abs(y1) < 1E-10)
                    u.addT(x1);
                else if (Math.abs(y2) < 1E-10)
                    u.addT(x2);
            }
        }
        if (u.h)
            ShapeStats.count(stat, ShapeStats.TYPE_HIT);
        return u;
    }

    public Vector3D normal(Vector3D p) {
        // ma sarà "corretto", così, o semplicemente ci si accontenta? ^_^
        Vector3D u = p.mul(foci.size());
        for (Focus f : foci)
            u.subU(f.center);
        return u.versU();
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
        double u = -thr, d;
        for (Focus f : foci) {
            d = p.sub(f.center).mod2();
            if ((d > -1E-10) && (d < +1E-10))
                return 1E10;
            u += f.strength / d;
        }
        return u;
    }

}
