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

package it.lapo.raytraccio;

import it.lapo.raytraccio.shape.CSG_Union;

/**
 * Single scene to render. <br>
 * Contains all the parameters that define a 'virtual world' that is to be
 * rendered: a shape, a POV, some lights.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Scene {

    /**
     * A single shape (take a look at {@link CSG_Union} if you think this is a
     * limitation)
     */
    private Shape3D s;

    /** Lights array */
    private Light l[] = new Light[3];

    /** Numer of used lights in array */
    private int numl = 0;

    /** Position vector of observer's <i>eye</i> */
    protected Vector3D eye;

    /** Position vector of the <i>looked at</i> spot */
    protected Vector3D c;

    /** Horizontal vector */
    protected Vector3D h;

    /** Vertical vector */
    protected Vector3D v;

    /**
     * Create a scene from its elements.
     *
     * @params a a single shape
     * @params ex position vector of observer's <i>eye </i>
     * @params ox position vector of the <i>looked at </i> spot
     * @params hx horizon vector
     * @params ratio horizontal/vertical aspect ratio
     */
    public Scene(Shape3D a, Vector3D ex, Vector3D ox, Vector3D hx, double ratio) {
        s = a;
        eye = ex;
        c = ox;
        if (hx.dot(ox.sub(ex)) != 0.0)
            h = hx.sub(ox.sub(ex).mul(1.0 / (hx.dot(ox.sub(ex)))));
        else
            h = hx;
        v = ox.sub(ex).vers().cross(h).mul(1.0 / ratio);
        // System.out.println("SCENE INIT");
        // System.out.println("E: "+eye);
        // System.out.println("C: "+c);
        // System.out.println("H: "+hx+" DOT:"+(1.0/hx.dot(ox.sub(ex)))+" ->
        // "+h);
        // System.out.println("V: "+v);
    }

    /**
     * Adds a light to the scene. <br>
     * Size of the aray that contains the lights is dynamically increased upon
     * necessity (by 50% plus one).
     *
     * @param a light to be added
     */
    public void addLight(Light a) {
        if (numl == l.length) {
            Light old[] = l;
            l = new Light[(l.length * 3) / 2 + 1]; // new dimension inspired
            // from Sun's ArrayList
            System.arraycopy(old, 0, l, 0, numl);
        }
        l[numl++] = a;
    }

    // TODO: traduzione ita->eng in corso.. continuare da qua...
    /**
     * Calcola il colore intersecato dal raggio dato. <br>
     * Identico a {@link #hit(Ray) hit(Ray)}ma richiama i metodi
     * {@link Shape3D#hit(EyeRays) hit(EyeRays)}delle figure, spesso
     * ottimizzati.
     *
     * @param a raggio da intersecare
     * @return colore del punto colpito
     */
    public Color hit(EyeRays a) {
        Hit h = s.hit(a);
        Color c;
        if (!h.h)
            c = Color.BLACK; // mettere qua il cielo
        else {
            if (h.reflect() > 0.999)
                c = Color.BLACK;
            else
                c = lighting(h.point(), h.normal());
            if (h.reflect() > 0.001) {
                Color nc = Color.BLACK; // anche qua il cielo
                Ray rr = new Ray(h.point(), Vector3D.ORIGIN /*
                                                             * una cosa a caso
                                                             * tanto c lo
                                                             * sovrascrivo
                                                             */);
                rr.c = a.c.mirror(h.normal()); // sovrascrivo c: ok, ok, è un
                // po' sporco!!
                Hit hs = s.hit(rr);
                if (hs.h && (hs.t > 1E-10))
                    nc = hs.color().mul(lighting(hs.point(), hs.normal()));
                c = c.mul(1.0 - h.reflect()).add(nc.mul(h.reflect()));
            }
            c = h.color().mul(c); // messo dopo per filtrare anche lo specchio
        }
        return (c);
    }

    /**
     * Calcola il colore intersecato dal raggio dato. <br>
     * Sono gestite le prime riflessioni e il 'cielo' (quando nessun oggetto
     * viene colpito dal raggio) viene considerato nero.
     *
     * @param a raggio da intersecare
     * @return colore del punto colpito
     */
    public Color hit(Ray a) {
        Hit h = s.hit(a);
        Color c;
        if (!h.h)
            c = Color.BLACK; // mettere qua il cielo
        else {
            if (h.reflect() > 0.999)
                c = Color.BLACK;
            else
                c = lighting(h.point(), h.normal());
            if (h.reflect() > 0.001) {
                Color nc = Color.BLACK; // anche qua il cielo
                Ray rr = new Ray(h.point(), Vector3D.ORIGIN /*
                                                             * una cosa a caso
                                                             * tanto c lo
                                                             * sovrascrivo
                                                             */);
                rr.c = a.c.mirror(h.normal()); // sovrascrivo c: ok, ok, è un
                // po' sporco!!
                Hit hs = s.hit(rr);
                if (hs.h && (hs.t > 1E-10))
                    nc = hs.color().mul(lighting(hs.point(), hs.normal()));
                c = c.mul(1.0 - h.reflect()).add(nc.mul(h.reflect()));
            }
            c = h.color().mul(c); // messo dopo per filtrare anche lo specchio
        }
        return (c);
    }

    /**
     * Calcola l'illuminazione generata da tutte le luci. <br>
     * Quando la scena non contiene luci viene calcolata un'illuminazione
     * massima uniforme.
     *
     * @param p vettore posizione del punto voluto
     * @param n vettore normale nel punto voluto
     * @return colore dell'illuminazione nel punto voluto
     */
    public Color lighting(Vector3D p, Vector3D n) {
        if (numl == 0) // se non ci sono luci
            return (Color.WHITE); // flat shading
        Ray rl;
        Hit hl;
        Color c = Color.BLACK;
        double direz;
        for (int i = 0; i < numl; i++) {
            rl = new Ray(p, l[i].o);
            direz = n.dot(rl.c.vers());
            if (direz > 0.0) {
                hl = s.hit(rl);
                if ((!hl.h) || (hl.t >= 1.0))
                    c = c.add(l[i].c.mul(direz).mul(l[i].p / rl.c.mod2()));
            }
        }
        return (c);
    }

}
