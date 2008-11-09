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

package it.lapo.raytraccio.pattern;

import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Modifica un altro pattern perturbandolo. <br>
 * La turbolenza da applicare &egrave; data da un'altro pattern, spesso si usa
 * un {@link PatternPerlin}per produrre effetti molto realistici.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Turbulence extends Pattern {

    /** {@link Pattern} da perturbare */
    protected Pattern pat;

    /** {@link Pattern} usato per perturbare */
    protected Pattern n;

    /** Turbolenza della perturbazione nella direzione X */
    protected double tx;

    /** Turbolenza della perturbazione nella direzione Y */
    protected double ty;

    /** Turbolenza della perturbazione nella direzione Z */
    protected double tz;

    /**
     * Applica una turbulenza a un altro pattern.
     *
     * @param pattern
     *            pattern da usare come base
     * @param noise
     *            rumore da usare come turbulenza
     * @param turbulence
     *            quanto il rumore perturba il pattern (isotropo)
     */
    public Turbulence(Pattern pattern, Pattern noise, double turbulence) {
        pat = pattern;
        n = noise;
        tx = ty = tz = turbulence;
    }

    /**
     * Applica una turbulenza a un altro pattern.
     *
     * @param pattern
     *            pattern da usare come base
     * @param noise
     *            rumore da usare come turbulenza
     * @param turbulence
     *            quanto il rumore perturba il pattern (anisotropo)
     */
    public Turbulence(Pattern pattern, Pattern noise, Vector3D turbulence) {
        pat = pattern;
        n = noise;
        tx = turbulence.x;
        ty = turbulence.y;
        tz = turbulence.z;
    }

    public double scalar(Vector3D p) {
        double vn[] = n.vectorial(p, (byte) 3);
        return (pat.scalar(new Vector3D(p.x + tx * vn[0], p.y + ty * vn[1], p.z
                + tz * vn[2])));
    }

    public double[] vectorial(Vector3D p, byte dim) {
        double vn[] = n.vectorial(p, (byte) 3);
        return (pat.vectorial(new Vector3D(p.x + tx * vn[0], p.y + ty * vn[1],
                p.z + tz * vn[2]), dim));
    }

}
