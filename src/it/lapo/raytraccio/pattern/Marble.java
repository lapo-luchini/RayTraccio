// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

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
