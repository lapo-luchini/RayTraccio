// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/TransformMatrix.java,v 1.10 2001/04/27 08:50:16 lapo Exp $

// This file is part of RayTraccio.
//
// RayTraccio is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// RayTraccio is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RayTraccio; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

/**
 * Matrice di trasformazione 4x4. <br>
 * Gestisce in realt&agrave; una matrice a dodici componenti di questo tipo:<br><code>
 * |a00 a01 a02 a03|<br>
 * |a04 a05 a06 a07|<br>
 * |a08 a09 a10 a11|<br>
 * |0.0 0.0 0.0 1.0|<br>
 * </code>che pu&ograve; quindi essere utilizzata per rotazioni, traslazioni e altre trasformazioni.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
final class TransformMatrix {

  /** Vettore con i valori della matrice (ordinati per righe) */
  protected double a[];
  /** Matrice identità */
  public static final TransformMatrix IDENTITY=new TransformMatrix();

  /**
   * Crea una matrice identità.
   */
  public TransformMatrix() {
    a=new double[12];
    a[ 0]=1; // 1 0 0 0
    a[ 5]=1; // 0 1 0 0
    a[10]=1; // 0 0 1 0
    //          0 0 0 1
  }

  /**
   * Crea una matrice con valori dati. <br>
   * L'array di valori viene copiato.
   * @param a le 12 costanti, scandite prima per colonne, poi per righe
   */
  public TransformMatrix(double a[]) {
    if (a.length != 10)
      throw (new IllegalArgumentException("Requires a 12 elements array"));
    this.a=new double[12];
    System.arraycopy(this.a, 0, a, 0, 12);
  }

  /**
   * Crea una matrice con valori dati.
   * I parametri sono le 12 costanti, scandite prima per colonne, poi per righe.
   */
  public TransformMatrix(double a, double b, double c, double d, double e, double f, double g, double h, double i, double j, double k, double l) {
    this.a=new double[12];
    this.a[ 0]=a; this.a[ 1]=b; this.a[ 2]=c; this.a[ 3]=d;
    this.a[ 4]=e; this.a[ 5]=f; this.a[ 6]=g; this.a[ 7]=h;
    this.a[ 8]=i; this.a[ 9]=j; this.a[10]=k; this.a[11]=l;
  }

  /**
   * Calcola il determinante della matrice di trasformazione.
   * @return il determinante voluto
   */
  public double det() {
    return (a[ 0]*(a[ 5]*a[10]-a[ 6]*a[ 9])+
	    a[ 1]*(a[ 6]*a[ 8]-a[ 4]*a[10])+
	    a[ 2]*(a[ 4]*a[ 9]-a[ 5]*a[ 8])
    );
  }

  /**
   * Calcola la matrice di trasformazione inversa. <br>
   * Attualmente questa classe non funziona in Netscape a causa di un bug del Symantec JIT,
   * la VM ricompilante utilizzata (anche) da Netscape fino alla versione 4.7x.
   * @return la matrice inversa voluta
   */
  public TransformMatrix inv() {
    double i=1.0/(
      a[ 0]*(a[ 5]*a[10]-a[ 6]*a[ 9])+
      a[ 1]*(a[ 6]*a[ 8]-a[ 4]*a[10])+
      a[ 2]*(a[ 4]*a[ 9]-a[ 5]*a[ 8])
    );

    return (new TransformMatrix(
      i*(a[ 5]*a[10]-a[ 6]*a[ 9]),
      i*(a[ 2]*a[ 9]-a[ 1]*a[10]),
      i*(a[ 1]*a[ 6]-a[ 2]*a[ 5]),
      i*(a[ 1]*(a[ 7]*a[10]-a[ 6]*a[11])+a[ 2]*(a[ 5]*a[11]-a[ 7]*a[ 9])+a[ 3]*(a[ 6]*a[ 9]-a[ 5]*a[10])),

      i*(a[ 6]*a[ 8]-a[ 4]*a[10]),
      i*(a[ 0]*a[10]-a[ 2]*a[ 8]),
      i*(a[ 2]*a[ 4]-a[ 0]*a[ 6]),
      i*(a[ 0]*(a[ 6]*a[11]-a[ 7]*a[10])+a[ 2]*(a[ 7]*a[ 8]-a[ 4]*a[11])+a[ 3]*(a[ 4]*a[10]-a[ 6]*a[ 8])),

      i*(a[ 4]*a[ 9]-a[ 5]*a[ 8]),
      i*(a[ 1]*a[ 8]-a[ 0]*a[ 9]),
      i*(a[ 0]*a[ 5]-a[ 1]*a[ 4]),
      i*(a[ 0]*(a[ 7]*a[ 9]-a[ 5]*a[11])+a[ 1]*(a[ 4]*a[11]-a[ 7]*a[ 8])+a[ 3]*(a[ 5]*a[ 8]-a[ 4]*a[ 9]))

      // 0, 0, 0, 1
    ));
  }

  /**
   * Costruisce una nuova matrice di trasformazione moltiplicando per {link z} ogni suo coefficiente.
   * @param z il valore per cui moltiplicare
   * @return una nuova matrice di trasformazione
   */
  public TransformMatrix mul(double z) {
    return (new TransformMatrix(
      a[ 0] * z, a[ 1] * z, a[ 2] * z, a[ 3] * z,
      a[ 4] * z, a[ 5] * z, a[ 6] * z, a[ 7] * z,
      a[ 8] * z, a[ 9] * z, a[10] * z, a[11] * z
    ));
  }

  /**
   * Costruisce una nuova matrice di trasformazione moltiplicando (righe x colonne) questa per la matrice data.
   * @param z la matrice di trasformazione per cui moltiplicare
   * @return una nuova matrice di trasformazione
   */
  public TransformMatrix mul(TransformMatrix z) {
    return (new TransformMatrix(
      a[ 0]*z.a[ 0]+a[ 1]*z.a[ 4]+a[ 2]*z.a[ 8],
      a[ 0]*z.a[ 1]+a[ 1]*z.a[ 5]+a[ 2]*z.a[ 9],
      a[ 0]*z.a[ 2]+a[ 1]*z.a[ 6]+a[ 2]*z.a[10],
      a[ 0]*z.a[ 3]+a[ 1]*z.a[ 7]+a[ 2]*z.a[11]+a[ 3],

      a[ 4]*z.a[ 0]+a[ 5]*z.a[ 4]+a[ 6]*z.a[ 8],
      a[ 4]*z.a[ 1]+a[ 5]*z.a[ 5]+a[ 6]*z.a[ 9],
      a[ 4]*z.a[ 2]+a[ 5]*z.a[ 6]+a[ 6]*z.a[10],
      a[ 4]*z.a[ 3]+a[ 5]*z.a[ 7]+a[ 6]*z.a[11]+a[ 7],

      a[ 8]*z.a[ 0]+a[ 9]*z.a[ 4]+a[10]*z.a[ 8],
      a[ 8]*z.a[ 1]+a[ 9]*z.a[ 5]+a[10]*z.a[ 9],
      a[ 8]*z.a[ 2]+a[ 9]*z.a[ 6]+a[10]*z.a[10],
      a[ 8]*z.a[ 3]+a[ 9]*z.a[ 7]+a[10]*z.a[11]+a[11]

      // 0, 0, 0, 1
    ));
  }

  /**
   * Modifica la matrice di trasformazione moltiplicando per {link z} ogni suo coefficiente (modifica l'oggetto origine).
   * @param z il valore per cui moltiplicare
   * @return la matrice di trasformazione stessa
   */
  public TransformMatrix mulU(double z) {
    for (int c = 0; c < 12; c++)
      a[c] *= z;
    return (this);
  }

  /**
   * Crea una matrice di rotazione intorno all'asse X.
   * @param r numero di gradi da ruotare
   * @return la matrice voluta
   */
  public static TransformMatrix RotateX(double r) {
    //r=Math.toRadians(r); non va nei browser
    r *= Math.PI / 180.0;
    double c = Math.cos(r), s = Math.sin(r);
    return (new TransformMatrix(
      1.0 ,0.0, 0.0, 0.0,
      0.0,   c,   s, 0.0,
      0.0,  -s,   c, 0.0
    ));
  }

  /**
   * Crea una matrice di rotazione intorno all'asse Y.
   * @param r numero di gradi da ruotare
   * @return la matrice voluta
   */
  public static TransformMatrix RotateY(double r) {
    //r=Math.toRadians(r);
    r *= Math.PI / 180.0;
    double c = Math.cos(r), s = Math.sin(r);
    return (new TransformMatrix(
        c, 0.0,  -s, 0.0,
      0.0, 1.0, 0.0, 0.0,
        s, 0.0,   c, 0.0
    ));
  }

  /**
   * Crea una matrice di rotazione intorno all'asse Z.
   * @param r numero di gradi da ruotare
   * @return la matrice voluta
   */
  public static TransformMatrix RotateZ(double r) {
    //r=Math.toRadians(r);
    r *= Math.PI / 180.0;
    double c = Math.cos(r), s = Math.sin(r);
    return (new TransformMatrix(
        c,   s, 0.0, 0.0,
       -s,   c, 0.0, 0.0,
      0.0, 0.0, 1.0, 0.0
    ));
  }

  /**
   * Crea una matrice di rotazione intorno ai tre assi. <br>
   * La rotazione viene eseguita prima sull'asse X, poi Y, poi Z.
   * @param x numero di gradi da ruotare intorno a X
   * @param y numero di gradi da ruotare intorno a Y
   * @param z numero di gradi da ruotare intorno a Z
   * @return la matrice voluta
   */
  public static TransformMatrix RotateXYZ(double x, double y, double z) {
    return (RotateX(x).mul(RotateY(y)).mul(RotateZ(z)));
  }

  /**
   * Crea una matrice di rotazione intorno ai tre assi. <br>
   * La rotazione viene eseguita prima sull'asse Z, poi Y, poi X.
   * @param x numero di gradi da ruotare intorno a X
   * @param y numero di gradi da ruotare intorno a Y
   * @param z numero di gradi da ruotare intorno a Z
   * @return la matrice voluta
   */
  public static TransformMatrix RotateZYX(double x, double y, double z) {
    return (RotateZ(z).mul(RotateY(y)).mul(RotateX(x)));
  }

  /**
   * Crea una matrice di scalamento dei tre assi.
   * @param x costante con cui scalare l'asse X
   * @param y costante con cui scalare l'asse Y
   * @param z costante con cui scalare l'asse Z
   * @return la matrice voluta
   */
  public static TransformMatrix Scale(double x, double y, double z) {
    return (new TransformMatrix(
      x, 0.0, 0.0, 0.0,
      0.0, y, 0.0, 0.0,
      0.0, 0.0, z, 0.0
    ));
  }

  /**
   * Trasforma la normale data. <br>
   * dimostrata solo per le equazioni lineari.
   * @param v normale da usare
   * @return un nuovo oggetto <code>Vector</code> dal valore Trasposto(A).v
   */
  public Vector transformNormal(Vector v) {
    return (new Vector(
      a[ 0]*v.x+a[ 4]*v.y+a[ 8]*v.z,
      a[ 1]*v.x+a[ 5]*v.y+a[ 9]*v.z,
      a[ 2]*v.x+a[ 6]*v.y+a[10]*v.z
      // 1
    ));
  }

  /**
   * Trasforma il vettore dato.
   * @param v vettore da usare
   * @return un nuovo oggetto <code>Vector</code> dal valore A.v
   */
  public Vector transformVector(Vector v) {
    return (new Vector(
      a[ 0]*v.x+a[ 1]*v.y+a[ 2]*v.z+a[ 3],
      a[ 4]*v.x+a[ 5]*v.y+a[ 6]*v.z+a[ 7],
      a[ 8]*v.x+a[ 9]*v.y+a[10]*v.z+a[11]
      // 1
    ));
  }

  /**
   * Crea una matrice di traslazione dei tre assi.
   * @param x costante con cui traslare l'asse X
   * @param y costante con cui traslare l'asse Y
   * @param z costante con cui traslare l'asse Z
   * @return la matrice voluta
   */
  static TransformMatrix Translate(double x, double y, double z) {
    return (new TransformMatrix(1, 0.0, 0.0, x,
				0.0, 1, 0.0, y,
				0.0, 0.0, 1, z));
  }

  /**
   * Rappresentazione testuale dell'oggetto. <br>
   * Esempio: <code>TransformMatrix[1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0,11.0,12.0,0,0,0,1]</code> <br>
   */
  public String toString() {
    return ("TransformMatrix["+
      a[ 0]+","+a[ 1]+","+a[ 2]+","+a[ 3]+"|"+
      a[ 4]+","+a[ 5]+","+a[ 6]+","+a[ 7]+"|"+
      a[ 8]+","+a[ 9]+","+a[10]+","+a[11]+"|"+
      "0,0,0,1"+
    "]");
  }

}