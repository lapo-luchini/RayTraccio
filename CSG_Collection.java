abstract class CSG_Collection extends Shape3D {
	protected Shape3D[] s = new Shape3D[10];
	protected int n = 0;
	protected int ot=1;
public void add(Shape3D a) {
	if (n == s.length) {
		Shape3D old[] = s;
		s = new Shape3D[ (s.length * 3) / 2 + 1]; // dimensione ispirata da ArrayList.java
		System.arraycopy(old, 0, s, 0, n);
	}
	s[n++] = a;
}
public Color color(Vector p) {
	System.out.println("ILLEGAL COLOR");
	return (Color.BLACK);
}
public Vector normal(Vector p) {
	System.out.println("ILLEGAL NORMAL");
	return (Vector.ORIGIN);
}
public void overturn() {
	ot = -ot;
}
public double reflect(Vector p) {
	System.out.println("ILLEGAL REFLECT");
	return (0);
}
public void scale(Vector i) {
	System.out.println("ILLEGAL SCALE");
}
public String toString() {
	return ("CSG_Collection[" + n + " shapes]");
}
public void translate(Vector i) {
	System.out.println("ILLEGAL TRANSLATE");
}
}
