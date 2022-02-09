// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio;

/**
 * Raggio visuale. <br>
 * Il raggio ha una coordinata parametrica <code>t</code> che vale
 * <code>0.0</code> nel punto di partenza e <code>1.0</code> nel punto di
 * arrivo.
 *
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class Ray {

	/** Punto di origine */
	public Vector3D o;

	/** Punto di direzione (coseni direttori) */
	public Vector3D c;

	/**
	 * Crea un raggio copiandolo da un altro.
	 *
	 * @params r raggio da cui copiare
	 */
	public Ray(Ray r) {
		this.o = r.o;
		this.c = r.c;
	}

	/**
	 * Crea un raggio da un punto verso un altro.
	 *
	 * @params o origine del raggio (coordinata parametrica <code>t=0.0</code>)
	 * @params a punto verso quale il raggio si dirige (coordinata parametrica <code>t=1.0</code>)
	 */
	public Ray(Vector3D o, Vector3D a) {
		this.o = o;
		this.c = a.sub(o);
	}

	/**
	 * Trova un punto sul raggio, data la cooridnata parametrica.
	 *
	 * @params a coordinata parametrica (<code>0.0</code> per l'origine, <code>1.0</code> per il punto di arrivo)
	 * @returns vettore posizione richiesto
	 */
	public final Vector3D point(double a) {
		return new Vector3D(o.x + a * c.x, o.y + a * c.y, o.z + a * c.z);
	}

	/**
	 * Crea un raggio deformando lo spazio del raggio originale.
	 *
	 * @param t la matrice di trasformazione da usare
	 * @return il raggio trasformato
	 */
	public final Ray transform(TransformMatrix t) {
		return new Ray(t.transformVector(o), t.transformVector(o.add(c)));
	}

	/**
	 * Calcola il punto del raggio che passa più vicino al punto dato.
	 *
	 * @param a il punto dal quale calcolare la distanza
	 * @return la distanza dal punto dato
	 */
	public final double dist(Vector3D a) {
		return o.sub(a).dot(c) / c.mod2();
	}

	/**
	 * Rappresentazione testuale dell'oggetto. <br>
	 * Esempio: <code>Ray[Vector3D[1.0,2.0,3.0]+t*Vector3D[1.0,0.0,0.0]]</code>
	 * <br>
	 */
	public String toString() {
		return "Ray[" + o + "+t*" + c + "]";
	}

}
