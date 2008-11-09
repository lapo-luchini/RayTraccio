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

/**
 * Vettore nello spazio tridimensionale.<br>
 * Le tre componenti sono valori <code>double</code> sui tre assi x, y, z.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public final class Vector3D {

  /** Componente X */
  public double x;
  /** Componente Y */
  public double y;
  /** Componente Z */
  public double z;
  /** Vettore origine &lt;0.0, 0.0, 0.0&gt; */
  public static final Vector3D ORIGIN = new Vector3D(0.0, 0.0, 0.0);
  /** Versore X &lt;1.0, 0.0, 0.0&gt; */
  public static final Vector3D VERS_X = new Vector3D(1.0, 0.0, 0.0);
  /** Versore Y &lt;0.0, 1.0, 0.0&gt; */
  public static final Vector3D VERS_Y = new Vector3D(0.0, 1.0, 0.0);
  /** Versore Z &lt;0.0, 0.0, 1.0&gt; */
  public static final Vector3D VERS_Z = new Vector3D(0.0, 0.0, 1.0);

  /**
   * Crea un vettore date le componenti.
   * @param x componente X
   * @param y componente Y
   * @param z componente Z
   */
  public Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Crea un vettore copiandolo da un altro.
   * @param a vettore da usare
   */
  public Vector3D(Vector3D a) {
    this.x = a.x;
    this.y = a.y;
    this.z = a.z;
  }

  /**
   * Somma di vettori.
   * @param a vettore da usare
   * @return un nuovo oggetto <code>Vector3D</code>
   */
  public Vector3D add(Vector3D a) {
    return (new Vector3D(x + a.x, y + a.y, z + a.z));
  }

  /**
   * Somma di vettori (modifica l'oggetto origine).
   * @param a vettore da usare
   * @return questo stesso oggetto <code>Vector3D</code>
   */
  public Vector3D addU(Vector3D a) {
    x += a.x;
    y += a.y;
    z += a.z;
    return (this);
  }

  /**
   * Prodotto vettoriale di vettori.
   * @param a vettore da usare
   * @return un nuovo oggetto <code>Vector3D</code>
   */
  public Vector3D cross(Vector3D a) {
    return (new Vector3D(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x));
  }

  /**
   * Prodotto scalare di vettori.
   * @param a vettore da usare
   * @return un nuovo oggetto <code>Vector3D</code>
   */
  public double dot(Vector3D a) {
    return (x * a.x + y * a.y + z * a.z);
  }

  /**
   * Confronto di vettori.
   * @param a vettore da usare
   * @return <code>true</code> se <code>a</code> è un <code>Vector3D</code> con uguali componenti<br><code>false</code> in caso contrario
   */
  public boolean equals(Object a) {
    if (!(a instanceof Vector3D))
      return (false);
    return ((x == ((Vector3D) a).x) && (x == ((Vector3D) a).y) && (x == ((Vector3D) a).z));
  }

  /**
   * Riflette il vettore intorno al vettore passato.<br>
   * Equivalente a <code>this.add(a.mul(-2.0 * a.dot(this)))</code>.
   * @param a vettore da usare
   * @return un nuovo oggetto <code>Vector3D</code>
   */
  public Vector3D mirror(Vector3D a) {
    return (this.add(a.mul(-2.0 * a.dot(this))));
  }

  /**
   * Modulo del vettore.
   * @return modulo
   */
  public double mod() {
    return (Math.sqrt(x * x + y * y + z * z));
  }

  /**
   * Modulo quadro del vettore.<br>
   * Più veloce di {@link #mod() mod()} in quanto non ha bisogno di usare <code>Math.sqrt(double)</code>.
   * @return modulo^2
   */
  public double mod2() {
    return (x * x + y * y + z * z);
  }

  /**
   * Moltiplicazione per uno scalare.
   * @param a scalare da usare
   * @return un nuovo oggetto <code>Vector3D</code>
   */
  public Vector3D mul(double a) {
    return (new Vector3D(x * a, y * a, z * a));
  }

  /**
   * Moltiplicazione per uno scalare (modifica l'oggetto origine).
   * @param a scalare da usare
   * @return questo stesso oggetto <code>Vector3D</code>
   */
  public Vector3D mulU(double a) {
    x *= a;
    y *= a;
    z *= a;
    return (this);
  }

  /**
   * Sottrazione di vettori.
   * @param a vettore da usare
   * @return un nuovo oggetto <code>Vector3D</code>
   */
  public Vector3D sub(Vector3D a) {
    return (new Vector3D(x - a.x, y - a.y, z - a.z));
  }

  /**
   * Sottrazione di vettori (modifica l'oggetto origine).
   * @param a vettore da usare
   * @return questo stesso oggetto <code>Vector3D</code>
   */
  public Vector3D subU(Vector3D a) {
    x -= a.x;
    y -= a.y;
    z -= a.z;
    return (this);
  }

  /**
   * Versore parallelo a questo vettore.
   * @return un nuovo oggetto <code>Vector3D</code> di modulo unitario
   */
  public Vector3D vers() {
    return (this.mul(1.0 / this.mod()));
  }

  /**
   * Trasforma in versore questo vettore (modifica l'oggetto origine).
   * @return questo stesso oggetto <code>Vector3D</code> (di modulo ora unitario)
   */
  public Vector3D versU() {
    double im = 1.0 / this.mod();
    this.x *= im;
    this.y *= im;
    this.z *= im;
    return (this);
  }

  public String toString() {
    return ("Vector[" + x + "," + y + "," + z + "]");
  }

}