class Quadric extends Shape3D {
  // ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
  // | a ½b ½d|
  // |½b  c ½e|
  // |½d ½e  f|
  public static double SFERA[]={ 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
	                     CIL_X[]={ 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
	                     CIL_Y[]={ 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
	                     CIL_Z[]={ 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,-1.0},
	                    CONO_X[]={-1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
	                    CONO_Y[]={ 1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
	                    CONO_Z[]={ 1.0, 0.0, 1.0, 0.0, 0.0,-1.0, 0.0, 0.0, 0.0, 0.0},
	                    PARA_X[]={ 0.0, 0.0, 1.0, 0.0, 0.0, 1.0,-1.0, 0.0, 0.0, 0.0},
	                    PARA_Y[]={ 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,-1.0, 0.0, 0.0},
	                    PARA_Z[]={ 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,-1.0, 0.0},
	                     IPE_Y[]={ 1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0};
  private double k[]; // i 10 coefficenti della quadrica k[0-9]={a, b, c, d, e, f, g, h, i, l}
  private Texture c; // texure della quadrica
	private Vector b_o=new Vector(1E30, 1E30, 1E30);
	private double b_tc, b_tbA, b_tbB, b_tbC, b_tbINC;
	protected static transient int stat_numrays;
	protected static transient int stat_numeyerays;
	protected static transient int stat_numcachedrays;
	protected static transient int stat_numhits;
Quadric(double a[], Texture b) {
	if (a.length != 10)
		throw (new IllegalArgumentException("Requires a 10 elements array"));
	c = b;
	k = new double[10];
	System.arraycopy(a, 0, k, 0, 10); // copia locale dei coefficenti
}
public Color color(Vector p) {
	return (c.color(p));
}
/** Versione ottimizzata di hit con un raggio di telecamera.
  */
public Hit hit(EyeRays a) {
	// ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	double tc, ta;
	byte i;
	stat_numeyerays++;
	if(b_o==a.o) {
		stat_numcachedrays++;
	  tc=b_tc;
	  //tb=b_tbx*a.c.x+b_tby*a.c.y+b_tbz*a.c.z;
	  if(a.i==0)
	  	b_tbINC=(b_tbA+=b_tbC);
	  else
	  	b_tbINC+=b_tbB;
	} else {
	  b_o=a.o;
	  b_tc=tc=k[0]*(a.o.x*a.o.x)+
		        k[1]*(a.o.x*a.o.y)+
		        k[2]*(a.o.y*a.o.y)+
		        k[3]*(a.o.x*a.o.z)+
		        k[4]*(a.o.y*a.o.z)+
		        k[5]*(a.o.z*a.o.z)+
		        k[6]*a.o.x+
		        k[7]*a.o.y+
		        k[8]*a.o.z+
		        k[9];
	  double
	  	b_tbx=k[0]*2.0*a.o.x+k[1]*a.o.y+k[3]*a.o.z+k[6],
	  	b_tby=k[1]*a.o.x+k[2]*2.0*a.o.y+k[4]*a.o.z+k[7],
	  	b_tbz=k[3]*a.o.x+k[4]*a.o.y+k[5]*2.0*a.o.z+k[8];
	  //tb=b_tbx*a.c.x+b_tby*a.c.y+b_tbz*a.c.z; // e qui sfrutto il fatto che è un EyeRays
		b_tbA=(b_tbx*a.d.x+b_tby*a.d.y+b_tbz*a.d.z);
		b_tbB=(b_tbx*a.h.x+b_tby*a.h.y+b_tbz*a.h.z);
		b_tbC=(b_tbx*a.v.x+b_tby*a.v.y+b_tbz*a.v.z);
		b_tbINC=b_tbA;
	}
	ta=k[0]*(a.c.x*a.c.x)+
	   k[1]*(a.c.x*a.c.y)+
	   k[2]*(a.c.y*a.c.y)+
	   k[3]*(a.c.x*a.c.z)+
	   k[4]*(a.c.y*a.c.z)+
	   k[5]*(a.c.z*a.c.z);
	double delta=b_tbINC*b_tbINC-4.0*ta*tc;
	Hit u=new Hit(this, a);
	if (delta>=0.0) {
	  double rdelta=Math.sqrt(delta);
	  ta=0.5/ta;
	  u.addT((-b_tbINC-rdelta)*ta);
	  u.addT((-b_tbINC+rdelta)*ta);
	}
	if(u.h)
		stat_numhits++;
	return(u);
}
public Hit hit(Ray a) {
	// ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
	// x=o.x+c.x*t
	// y=o.y+c.y*t
	// z=o.z+c.z*t
	/*if(a instanceof EyeRays)
		return(hit((EyeRays)a));*/
	double tc, tb, ta;
	byte i;
	stat_numrays++;
  tc=k[0]*(a.o.x*a.o.x)+
	   k[1]*(a.o.x*a.o.y)+
	   k[2]*(a.o.y*a.o.y)+
	   k[3]*(a.o.x*a.o.z)+
	   k[4]*(a.o.y*a.o.z)+
	   k[5]*(a.o.z*a.o.z)+
	   k[6]*a.o.x+
	   k[7]*a.o.y+
	   k[8]*a.o.z+
	   k[9];
	tb=(k[0]*2.0*a.o.x+k[1]*a.o.y+k[3]*a.o.z+k[6])*a.c.x+
	 (k[1]*a.o.x+k[2]*2.0*a.o.y+k[4]*a.o.z+k[7])*a.c.y+
	 (k[3]*a.o.x+k[4]*a.o.y+k[5]*2.0*a.o.z+k[8])*a.c.z;
	ta=k[0]*(a.c.x*a.c.x)+
	   k[1]*(a.c.x*a.c.y)+
	   k[2]*(a.c.y*a.c.y)+
	   k[3]*(a.c.x*a.c.z)+
	   k[4]*(a.c.y*a.c.z)+
	   k[5]*(a.c.z*a.c.z);
	double delta=tb*tb-4.0*ta*tc;
	Hit u=new Hit(this, a);
	if (delta>=0.0) {
	  double rdelta=Math.sqrt(delta);
	  ta=0.5/ta;
	  u.addT((-tb-rdelta)*ta);
	  u.addT((-tb+rdelta)*ta);
	}
	if(u.h)
		stat_numhits++;
	return(u);
}
public Vector normal(Vector p) {
	// ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
	// f'x=2ax+by+dz+g
	// f'y=bx+2cy+ez+h
	// f'z=dx+ey+2fz+i
	return(new Vector(
	  2.0*k[0]*p.x+k[1]*p.y+k[3]*p.z+k[6],
	  k[1]*p.x+2.0*k[2]*p.y+k[4]*p.z+k[7],
	  k[3]*p.x+k[4]*p.y+2.0*k[5]*p.z+k[8]).versU());
}
public void overturn() {
	for (int i = 0; i < 10; i++)
		k[i] = -k[i];
}
public double reflect(Vector p) {
	return (c.reflect(p));
}
public void scale(Vector i) {
	// ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
	// X=zx*x     x=X/zx
	// Y=zy*y ==> y=Y/zy
	// Z=zz*z     z=Z/zz
	i.x = 1.0 / i.x;
	i.y = 1.0 / i.y;
	i.z = 1.0 / i.z;
	k[0] *= i.x * i.x;
	k[1] *= i.x * i.y;
	k[2] *= i.y * i.y;
	k[3] *= i.x * i.z;
	k[4] *= i.y * i.z;
	k[5] *= i.z * i.z;
	k[6] *= i.x;
	k[7] *= i.y;
	k[8] *= i.z;
}
public static String stats() {
	return("QUADRIC\n"+
	       "Number of rays:        "+stat_numrays+"\n"+
	       "Number of eye-rays:    "+stat_numeyerays+"\n"+
	       "Number of cached rays: "+stat_numcachedrays+" ("+((stat_numcachedrays*100.0)/(stat_numrays+stat_numeyerays))+"%)\n"+
	       "Number of hits:        "+stat_numhits+" ("+((stat_numhits*100.0)/(stat_numrays+stat_numeyerays))+"%)");
}
public String toString() {
	String u = "Quadric[";
	for (int i = 0; i < 10; i++)
		u += k[i] + ",";
	u += "tx:" + c + "]";
	return (u);
}
public void translate(Vector i) {
	// ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
	// X=zx+x     x=X-zx
	// Y=zy+y ==> y=Y-zy
	// Z=zz+z     z=Z-zz
	// a(X+x)²+     b(X+x)(Y+y)+    c(Y+y)²+     d(X+x)(Z+z)+    e(Y+y)(Z+z)+    f(Z+z)²+     g(X+x)+h(Y+y)+i(Z+z)+l=0
	// aX²+2aXx+ax²+bXY+bXy+bYx+bxy+cY²+2cYy+cy²+dXZ+dXz+dZx+dzx+eYZ+eYz+eZy+ezy+fZ²+2fZz+fz²+gX+gx+ hY+hy +iZ+iz +l=0
	// X²=a XY=b Y²=c XZ=d YZ=e Z²=f
	// X =2ax+by+dz+g
	// Y =bx+2cy+ez+h
	// Z =dx+ey+2fz+i
	//   =ax²+bxy+cy²+dzx+ezy+fz²+gx+hy+iz+l
	i=Vector.ORIGIN.sub(i); // i=i.mul(-1.0) dev'essere più lento
	k[9]+=(k[0]*i.x*i.x)+(k[2]*i.y*i.y)+(k[5]*i.z*i.z)+ // prima perché‚ usa 678
	      (k[1]*i.x*i.y)+(k[3]*i.x*i.z)+(k[4]*i.y*i.z)+
	      (k[6]*i.x)+(k[7]*i.y)+(k[8]*i.z);
	k[6]+=(2*k[0]*i.x)+(k[1]*i.y)+(k[3]*i.z);
	k[7]+=(k[1]*i.x)+(2*k[2]*i.y)+(k[4]*i.z);
	k[8]+=(k[3]*i.x)+(k[4]*i.y)+(2*k[5]*i.z);
}
public double value(Vector p) {
	// ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l
	return(k[0]*p.x*p.x+k[1]*p.x*p.y+k[2]*p.y*p.y+k[3]*p.x*p.z+k[4]*p.y*p.z+k[5]*p.z*p.z+k[6]*p.x+k[7]*p.y+k[8]*p.z+k[9]);
}
}
