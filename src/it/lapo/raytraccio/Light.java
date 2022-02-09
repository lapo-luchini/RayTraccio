// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio;

/**
 * Luce puntiforme.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Light {

    /** Vettore posizione */
    protected Vector3D o;

    /** Colore della luce (alla distanza <code>p</code>) */
    protected Color c;

    /**
     * Potenza della luce (distanza alla quale usare il colore <code>c</code>
     * invariato)
     */
    protected double p;

    /**
     * Crea una luce puntiforme cno parametri dati. <br>
     *
     * @param o Vettore posizione
     * @param c Colore
     * @param p Potenza
     */
    public Light(Vector3D o, Color c, double p) {
        this.o = o;
        this.c = c;
        this.p = p;
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio: <code>Light[Vector3D[1.0,2.0,3.0],Color[1.0,0.0,0.0],5.0]</code>
     * <br>
     */
    public String toString() {
        return ("Light[" + o + "," + c + "," + p + "]");
    }

}
