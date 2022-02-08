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

import it.lapo.raytraccio.PRNG;
import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Campo di rumore tridimensionale a spettro |1/f|. <br>
 * Ha gli stessi problemi di anisotropia di {@link Perlin}.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class PerlinAbs extends Pattern {

    /** Seme dello spazio casuale */
    private long seed;

    /** Numero di ottave da calcolare */
    protected int octaves;

    /** Persistenza (ampiezza di un'ottava rispetto alla precedente) */
    protected double persistence;

    /** Valore usato internamente per scalare il valore di uscita in [0.0,1.0) */
    protected double amplitude;

    /**
     * Definisce un pattern di tipo rumore di Perlin.
     *
     * @param octaves
     *            numero di ottave da calcolare
     * @param persistence
     *            ampiezza dell'ottava successiva rispetto alla precedente
     * @param seed
     *            seme del generatore pseudocasuale
     */
    public PerlinAbs(byte octaves, double persistence, long seed) {
        this.octaves = octaves;
        this.persistence = persistence;
        this.seed = seed;
        if (persistence == 1.0)
            amplitude = 1.0 / octaves;
        else {
            double po = persistence;
            while (--octaves > 0)
                po *= persistence;
            amplitude = (1 - persistence) / (1 - po);
        }
    }

    /**
     * Genera un campo vettoriale di rumore.
     *
     * @return double[dim] valore del rumore [0.0,+1.0)
     * @param p
     *            vettore posizione nel campo di rumore
     */
    public double scalar(Vector3D p) {
        // vettore a cui applicare PRNG: inizializzato con le 3 long della
        // posizione e la long di seed
        long[] vect = new long[4];
        // 1.0/2147483648 trasforma un int in un reale in [-1.0,+1.0)
        double amp = 0.0000000004656612873077392578125000000000;
        double x = p.x, y = p.y, z = p.z;
        double noises[] = new double[8];
        double noise = 0.0;
        long lx, ly, lz;
        double fx, fy, fz;
        double intr, ints;
        vect[3] = seed;
        for (int o = 0; o < octaves; o++) {
            // prende parte intera e frazionaria delle coordinate
            lx = (long) Math.floor(x);
            fx = x - lx;
            ly = (long) Math.floor(y);
            fy = y - ly;
            lz = (long) Math.floor(z);
            fz = z - lz;
            // prende gli 8 punti ai vertici del cubo unitario
            vect[0] = lx;
            vect[1] = ly;
            vect[2] = lz;
            noises[0] = PRNG.getInt(vect) * amp;
            vect[0] = lx + 1;
            noises[1] = PRNG.getInt(vect) * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            noises[2] = PRNG.getInt(vect) * amp;
            vect[0] = lx + 1;
            noises[3] = PRNG.getInt(vect) * amp;
            vect[0] = lx;
            vect[1] = ly;
            vect[2] = lz + 1;
            noises[4] = PRNG.getInt(vect) * amp;
            vect[0] = lx + 1;
            noises[5] = PRNG.getInt(vect) * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            noises[6] = PRNG.getInt(vect) * amp;
            vect[0] = lx + 1;
            noises[7] = PRNG.getInt(vect) * amp;
            // interpola sull'asse X
            intr = 0.5 * (1.0 - Math.cos(fx * Math.PI));
            ints = 1.0 - intr;
            noises[0] = noises[0] * ints + noises[1] * intr;
            noises[2] = noises[2] * ints + noises[3] * intr;
            noises[4] = noises[4] * ints + noises[5] * intr;
            noises[6] = noises[6] * ints + noises[7] * intr;
            // interpola sull'asse Y
            intr = 0.5 * (1.0 - Math.cos(fy * Math.PI));
            ints = 1.0 - intr;
            noises[0] = noises[0] * ints + noises[2] * intr;
            noises[4] = noises[4] * ints + noises[6] * intr;
            // interpola sull'asse Z
            intr = 0.5 * (1.0 - Math.cos(fz * Math.PI));
            ints = 1.0 - intr;
            noises[0] = noises[0] * ints + noises[4] * intr;
            // aggiunge l'ottava al rumore
            if (noises[0] > 0.0)
                noise += noises[0];
            else
                noise -= noises[0];
            // prepara la prossima ottava
            x *= 2;
            y *= 2;
            z *= 2;
            amp *= persistence;
        }
        return (noise * amplitude);
    }

    /**
     * Genera un campo vettoriale di rumore.
     *
     * @return double[dim] valore del rumore [0.0,+1.0)
     * @param p
     *            vettore posizione nel campo di rumore
     * @param dim
     *            numero di dimensioni (massimo 5)
     */
    public double[] vectorial(Vector3D p, byte dim) {
        // vettore a cui applicare PRNG: inizializzato con le 3 long della
        // posizione e la long di seed
        long[] vect = new long[4];
        // vettore di output di SHA1: 20 byte
        int[] out = new int[dim];
        // 1.0/2147483648 trasforma un int in un reale in [-1.0,+1.0)
        double amp = 0.0000000004656612873077392578125000000000;
        double x = p.x, y = p.y, z = p.z;
        double noises[][] = new double[dim][8];
        double noise[] = new double[dim];
        long lx, ly, lz;
        double fx, fy, fz;
        double intr, ints;
        int dims;
        vect[3] = seed;
        for (int o = 0; o < octaves; o++) {
            // prende parte intera e frazionaria delle coordinate
            lx = (long) Math.floor(x);
            fx = x - lx;
            ly = (long) Math.floor(y);
            fy = y - ly;
            lz = (long) Math.floor(z);
            fz = z - lz;
            // prende gli 8 punti ai vertici del cubo unitario
            vect[0] = lx;
            vect[1] = ly;
            vect[2] = lz;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][0] = out[dims] * amp;
            vect[0] = lx + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][1] = out[dims] * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][2] = out[dims] * amp;
            vect[0] = lx + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][3] = out[dims] * amp;
            vect[0] = lx;
            vect[1] = ly;
            vect[2] = lz + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][4] = out[dims] * amp;
            vect[0] = lx + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][5] = out[dims] * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][6] = out[dims] * amp;
            vect[0] = lx + 1;
            PRNG.getInt3(vect, out);
            for (dims = 0; dims < dim; dims++)
                noises[dims][7] = out[dims] * amp;
            for (dims = 0; dims < dim; dims++) {
                // interpola sull'asse X
                intr = 0.5 * (1.0 - Math.cos(fx * Math.PI));
                ints = 1.0 - intr;
                noises[dims][0] = noises[dims][0] * ints + noises[dims][1]
                        * intr;
                noises[dims][2] = noises[dims][2] * ints + noises[dims][3]
                        * intr;
                noises[dims][4] = noises[dims][4] * ints + noises[dims][5]
                        * intr;
                noises[dims][6] = noises[dims][6] * ints + noises[dims][7]
                        * intr;
                // interpola sull'asse Y
                intr = 0.5 * (1.0 - Math.cos(fy * Math.PI));
                ints = 1.0 - intr;
                noises[dims][0] = noises[dims][0] * ints + noises[dims][2]
                        * intr;
                noises[dims][4] = noises[dims][4] * ints + noises[dims][6]
                        * intr;
                // interpola sull'asse Z
                intr = 0.5 * (1.0 - Math.cos(fz * Math.PI));
                ints = 1.0 - intr;
                noises[dims][0] = noises[dims][0] * ints + noises[dims][4]
                        * intr;
                // aggiunge l'ottava al rumore
                if (noises[dims][0] > 0.0)
                    noise[dims] += noises[dims][0];
                else
                    noise[dims] -= noises[dims][0];
            }
            // prepara la prossima ottava
            x *= 2;
            y *= 2;
            z *= 2;
            amp *= persistence;
        }
        for (dims = 0; dims < dim; dims++)
            noise[dims] *= amplitude;
        return (noise);
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio: <code>NoisePerlin[10,0.5,562746835683345476]</code><br>
     */
    public String toString() {
        return ("NoisePerlin[" + octaves + "," + persistence + "," + seed + "]");
    }

}
