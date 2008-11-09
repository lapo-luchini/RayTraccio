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

import it.lapo.hash.SHA1;
import it.lapo.raytraccio.Pattern;
import it.lapo.raytraccio.Vector3D;

/**
 * Campo di rumore tridimensionale a spettro 1/f. <br>
 * &Egrave; una versione leggermente semplificata della versione di <a
 * href="http://mrl.nyu.edu/~perlin/">Ken Perlin </a>, pi� rapida da calcolare
 * ma pi� "cubettosa" se usata con poche ottave (� un pattern sommatoria di
 * componenti anisotrope, isotropo solo in somma).
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Perlin extends Pattern {

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
    public Perlin(byte octaves, double persistence, long seed) {
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
     * @param p vettore posizione nel campo di rumore
     * @return double[dim] valore del rumore [0.0,+1.0)
     */
    public double scalar(Vector3D p) {
        // funzione di hash SHA1, segue lo standard FIPS PUB 180-1
        SHA1 sha = new SHA1();
        // vettore a cui applicare SHA1: inizializzato con le 3 long della
        // posizione e la long di seed
        long[] vect = new long[4];
        // vettore di output di SHA1: 20 byte
        int[] out;
        // 1.0/4294967296 trasforma un int in un reale in [-0.5,+0.5)
        double amp = 0.00000000023283064365386962890625000000000;
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
            sha.update(vect);
            out = sha.digest32();
            noises[0] = out[0] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[1] = out[0] * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[2] = out[0] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[3] = out[0] * amp;
            vect[0] = lx;
            vect[1] = ly;
            vect[2] = lz + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[4] = out[0] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[5] = out[0] * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[6] = out[0] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            noises[7] = out[0] * amp;
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
            noise += noises[0];
            // prepara la prossima ottava
            x *= 2;
            y *= 2;
            z *= 2;
            amp *= persistence;
        }
        return (0.5 + noise * amplitude);
    }

    /**
     * Genera un campo vettoriale di rumore.
     *
     * @return double[dim] valore del rumore [0.0,+1.0)
     * @param p vettore posizione nel campo di rumore
     * @param dim numero di dimensioni (massimo 5)
     */
    public double[] vectorial(Vector3D p, byte dim) {
        // funzione di hash SHA1, segue lo standard FIPS PUB 180-1
        SHA1 sha = new SHA1();
        // vettore a cui applicare SHA1: inizializzato con le 3 long della
        // posizione e la long di seed
        long[] vect = new long[4];
        // vettore di output di SHA1: 20 byte
        int[] out;
        // 1.0/4294967296 trasforma un int in un reale in [-0.5,+0.5)
        double amp = 0.00000000023283064365386962890625000000000;
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
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][0] = out[dims] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][1] = out[dims] * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][2] = out[dims] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][3] = out[dims] * amp;
            vect[0] = lx;
            vect[1] = ly;
            vect[2] = lz + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][4] = out[dims] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][5] = out[dims] * amp;
            vect[0] = lx;
            vect[1] = ly + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][6] = out[dims] * amp;
            vect[0] = lx + 1;
            sha.update(vect);
            out = sha.digest32();
            for (dims = 0; dims < dim; dims++)
                noises[dims][7] = out[dims] * amp;
            for (dims = 0; dims < dim; dims++) {
                // interpola sull'asse X
                intr = 0.5 * (1.0 - Math.cos(fx * Math.PI));
                ints = 1.0 - intr;
                noises[dims][0] = noises[dims][0] * ints + noises[dims][1] * intr;
                noises[dims][2] = noises[dims][2] * ints + noises[dims][3] * intr;
                noises[dims][4] = noises[dims][4] * ints + noises[dims][5] * intr;
                noises[dims][6] = noises[dims][6] * ints + noises[dims][7] * intr;
                // interpola sull'asse Y
                intr = 0.5 * (1.0 - Math.cos(fy * Math.PI));
                ints = 1.0 - intr;
                noises[dims][0] = noises[dims][0] * ints + noises[dims][2] * intr;
                noises[dims][4] = noises[dims][4] * ints + noises[dims][6] * intr;
                // interpola sull'asse Z
                intr = 0.5 * (1.0 - Math.cos(fz * Math.PI));
                ints = 1.0 - intr;
                noises[dims][0] = noises[dims][0] * ints + noises[dims][4] * intr;
                // aggiunge l'ottava al rumore
                noise[dims] += noises[dims][0];
            }
            // prepara la prossima ottava
            x *= 2;
            y *= 2;
            z *= 2;
            amp *= persistence;
        }
        for (dims = 0; dims < dim; dims++)
            noise[dims] = 0.5 + noise[dims] * amplitude;
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
