/** Matrice di trasformazione 4x4.
  * Gestisce in realtà una matrice a dodici componenti di questo tipo:
  * |a00 a01 a02 a03|
  * |a04 a05 a06 a07|
  * |a08 a09 a10 a11|
  * | 0   0   0   1 |
  * che può quindi essere utilizzato per rotazioni, traslazioni e altre trasformazioni.
  */
class TransformMatrix {
	/** Vettore con i valori della matrice (ordinati per righe) */
  protected double a[];
	/** Matrice identità */
	public static final TransformMatrix IDENTITY=new TransformMatrix();
TransformMatrix() {
	a=new double[12];
	a[ 0]=1; // 1 0 0 0
	a[ 5]=1; // 0 1 0 0
	a[10]=1; // 0 0 1 0
}
TransformMatrix(double a[]) {
	if (a.length != 10)
		throw (new IllegalArgumentException("Requires a 12 elements array"));
	this.a=new double[12];
	System.arraycopy(this.a, 0, a, 0, 12);
}
TransformMatrix(double a, double b, double c, double d, double e, double f, double g, double h, double i, double j, double k, double l) {
	this.a=new double[12];
	this.a[ 0]=a; this.a[ 1]=b; this.a[ 2]=c; this.a[ 3]=d;
	this.a[ 4]=e; this.a[ 5]=f; this.a[ 6]=g; this.a[ 7]=h;
	this.a[ 8]=i; this.a[ 9]=j; this.a[10]=k; this.a[11]=l;
}
public double det() {
	return (a[ 0]*(a[ 5]*a[10]-a[ 6]*a[ 9])+
	        a[ 1]*(a[ 6]*a[ 8]-a[ 4]*a[10])+
	        a[ 2]*(a[ 4]*a[ 9]-a[ 5]*a[ 8]));
}
public TransformMatrix inv() {
	double i=1.0/(a[ 0]*(a[ 5]*a[10]-a[ 6]*a[ 9])+
	              a[ 1]*(a[ 6]*a[ 8]-a[ 4]*a[10])+
	              a[ 2]*(a[ 4]*a[ 9]-a[ 5]*a[ 8]));
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

		/* 0, 0, 0, 1 */
	));
}
public TransformMatrix mul(double z) {
	return (new TransformMatrix(a[ 0] * z, a[ 1] * z, a[ 2] * z, a[ 3] * z,
		                          a[ 4] * z, a[ 5] * z, a[ 6] * z, a[ 7] * z,
		                          a[ 8] * z, a[ 9] * z, a[10] * z, a[11] * z));
}
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

		/* 0, 0, 0, 1 */
	));
}
public TransformMatrix mulU(double z) {
	for (int c = 0; c < 12; c++)
		a[c] *= z;
	return (this);
}
static TransformMatrix Rotate(double x, double y, double z) {
	return (RotateX(x).mul(RotateY(y)).mul(RotateZ(z)));
}
static TransformMatrix RotateX(double r) {
	//r=Math.toRadians(r); non va nei browser
	r *= Math.PI / 180.0;
	return (new TransformMatrix(1.0,          0.0,         0.0, 0.0,
		                          0.0,  Math.cos(r), Math.sin(r), 0.0,
		                          0.0, -Math.sin(r), Math.cos(r), 0.0));
}
static TransformMatrix RotateY(double r) {
	//r=Math.toRadians(r);
	r *= Math.PI / 180.0;
	return (new TransformMatrix(Math.cos(r), 0.0, -Math.sin(r), 0.0,
		                                  0.0, 1.0,          0.0, 0.0,
		                          Math.sin(r), 0.0,  Math.cos(r), 0.0));
}
static TransformMatrix RotateZ(double r) {
	//r=Math.toRadians(r);
	r *= Math.PI / 180.0;
	return (new TransformMatrix( Math.cos(r), Math.sin(r), 0.0, 0.0,
		                          -Math.sin(r), Math.cos(r), 0.0, 0.0,
		                                   0.0,         0.0, 1.0, 0.0));
}
static TransformMatrix Scale(double x, double y, double z) {
	return (new TransformMatrix(x, 0.0, 0.0, 0.0,
		                          0.0, y, 0.0, 0.0,
		                          0.0, 0.0, z, 0.0));
}
public String toString() {
	return ("TransformMatrix["+
		a[ 0]+","+a[ 1]+","+a[ 2]+","+a[ 3]+"|"+
		a[ 4]+","+a[ 5]+","+a[ 6]+","+a[ 7]+"|"+
		a[ 8]+","+a[ 9]+","+a[10]+","+a[11]+"|"+
		"0,0,0,1"+
	"]");
}
/**
 * Trasforma la normale data.
 * @param v normale da usare
 * @return un nuovo oggetto <code>Vector</code> dal valore Trasposto(A).v
 */
public Vector transformNormal(Vector v) {
	return (new Vector(
		a[ 0]*v.x+a[ 4]*v.y+a[ 8]*v.z,
		a[ 1]*v.x+a[ 5]*v.y+a[ 9]*v.z,
		a[ 2]*v.x+a[ 6]*v.y+a[10]*v.z
		/* 1 */
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
		/* 1 */
	));
}
static TransformMatrix Translate(double x, double y, double z) {
	return (new TransformMatrix(1, 0.0, 0.0, x,
		                          0.0, 1, 0.0, y,
		                          0.0, 0.0, 1, z));
}
}
