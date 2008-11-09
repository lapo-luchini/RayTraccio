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
 * Campo vettoriale di rumore di tipo marmo
 * <code>0.5+0.5*cos(x+turbulence*noise.scalar(p))</code>.<br>
 * Questo tipo di marmo in realt&agrave; non &egrave; molto realistico, ci sono
 * modi migliori, come ad esempio applicare una turbolenza ad un pattern ad onda
 * triangolare (applicare {@link PatternTurbulence}a {@link PatternTriangle}
 * applicato a {@link PatternSawtooth}).
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Marble extends Pattern {

    private Pattern noise;

    private double turbulence;

    /**
     * Definisce un pattern di tipo marmo.
     *
     * @param noise
     *            rumore da usare come base
     * @param incidence
     *            quanto il rumore perturba il pattern
     */
    public Marble(Pattern noise, double turbulence) {
        this.noise = noise;
        this.turbulence = turbulence;
    }

    /**
     * Genera un campo vettoriale di rumore di tipo marmo.
     *
     * @return double[dim] valore del rumore [0.0,+1.0)
     * @param p
     *            vettore posizione nel campo
     */
    public double scalar(Vector3D p) {
        return (0.5 + 0.5 * Math.cos(p.x + turbulence * noise.scalar(p)));
    }

    /**
     * Genera un campo vettoriale di rumore di tipo marmo.
     *
     * @return double[dim] valore nel punto richiesto [0.0,+1.0) per ogni
     *         dimensione
     * @param p
     *            vettore posizione nel campo
     * @param dim
     *            numero di dimensioni (massimo 5)
     */
    public double[] vectorial(Vector3D p, byte dim) {
        double[] v = noise.vectorial(p, dim);
        for (int i = 0; i < dim; i++)
            v[i] = 0.5 + 0.5 * Math.cos(p.x + turbulence * v[i]);
        return (v);
    }

}
