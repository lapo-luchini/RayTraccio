import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

class Light {
  protected Vector o;
  protected Color c;
  protected double p;
Light(Vector o, Color c, double p) {
	this.o = o;
	this.c = c;
	this.p = p;
}
public String toString() {
	return ("Light[" + o + "," + c + "," + p + "]");
}
}
