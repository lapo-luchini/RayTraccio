/** Vettore nello spazio tridimensionale.
  */
class Vector {
	protected double x, y, z;
	public static final Vector ORIGIN = new Vector(0.0, 0.0, 0.0),
	                           VERS_X = new Vector(1.0, 0.0, 0.0),
	                           VERS_Y = new Vector(0.0, 1.0, 0.0),
	                           VERS_Z = new Vector(0.0, 0.0, 1.0);
Vector(double x, double y, double z) {
	this.x = x;
	this.y = y;
	this.z = z;
}
Vector(Vector a) {
	this.x = a.x;
	this.y = a.y;
	this.z = a.z;
}
public Vector add(Vector a) {
	return (new Vector(x + a.x, y + a.y, z + a.z));
}
public Vector addU(Vector a) {
	x += a.x;
	y += a.y;
	z += a.z;
	return (this);
}
public Vector cross(Vector a) {
	return (new Vector(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x));
}
public double dot(Vector a) {
	return (x * a.x + y * a.y + z * a.z);
}
public boolean equals(Object a) {
	if (!(a instanceof Vector))
		return (false);
	return ((x == ((Vector) a).x) && (x == ((Vector) a).y) && (x == ((Vector) a).z));
}
public Vector mirror(Vector a) {
	return (this.add(a.mul(-2.0 * a.dot(this))));
}
public double mod() {
	return (Math.sqrt(x * x + y * y + z * z));
}
public double mod2() {
	return (x * x + y * y + z * z);
}
public Vector mul(double a) {
	return (new Vector(x * a, y * a, z * a));
}
public Vector mulU(double a) {
	x *= a;
	y *= a;
	z *= a;
	return (this);
}
public Vector sub(Vector a) {
	return (new Vector(x - a.x, y - a.y, z - a.z));
}
public Vector subU(Vector a) {
	x -= a.x;
	y -= a.y;
	z -= a.z;
	return (this);
}
public String toString() {
	return ("Vector[" + x + "," + y + "," + z + "]");
}
public Vector transform(TransformMatrix t) { // moltiplica A·v
	return (new Vector(
		t.a[ 0]*x+t.a[ 1]*y+t.a[ 2]*z+t.a[ 3],
		t.a[ 4]*x+t.a[ 5]*y+t.a[ 6]*z+t.a[ 7],
		t.a[ 8]*x+t.a[ 9]*y+t.a[10]*z+t.a[11]
		/* 1 */
	));
}
public Vector vers() {
	return (this.mul(1.0 / this.mod()));
}
public Vector versU() {
	double im = 1.0 / this.mod();
	this.x *= im;
	this.y *= im;
	this.z *= im;
	return (this);
}
}
