/** Oggetto tridimensionale.
  */
abstract class Shape3D {
abstract public Color color(Vector p);
abstract public Hit hit(EyeRays a);
abstract public Hit hit(Ray a);
abstract public Vector normal(Vector p);
abstract public void overturn(); // capovolge la figura (scambia interno ed esterno)  
abstract public double reflect(Vector p);
abstract public void scale(Vector i); // cambia la figura di dimensioni  
abstract public void translate(Vector i); // trasla la figura  
abstract public double value(Vector p);
}
