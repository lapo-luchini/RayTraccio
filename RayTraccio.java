import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

class Vector {
  protected double x, y, z;
  Vector(double x, double y, double z) {
    this.x=x;
    this.y=y;
    this.z=z;
  }
  public static Vector ORIGIN=new Vector(0.0, 0.0, 0.0),
                       VERS_X=new Vector(1.0, 0.0, 0.0),
                       VERS_Y=new Vector(0.0, 1.0, 0.0),
                       VERS_Z=new Vector(0.0, 0.0, 1.0);
  public Vector add(Vector a) {
    return(new Vector(x+a.x, y+a.y, z+a.z));
  }
  public Vector sub(Vector a) {
    return(new Vector(x-a.x, y-a.y, z-a.z));
  }
  public Vector mul(double a) {
    return(new Vector(x*a, y*a, z*a));
  }
  public double dot(Vector a) {
    return(x*a.x+y*a.y+z*a.z);
  }
  public double mod2() {
    return(this.dot(this));
  }
  public double mod() {
    return(Math.sqrt(this.mod2()));
  }
  public Vector vers() {
    return(this.mul(1.0/this.mod()));
  }
  public Vector mirror(Vector a) {
    return(this.add(a.mul(-2.0*a.dot(this))));
  }
  public String toString() { 
    return("Vector["+x+","+y+","+z+"]");
  }
}

class Ray {
  protected Vector o, c;
  Ray(Vector o, Vector a) {
    this.o=o;
    this.c=a.sub(o); //.vers();
  }
  public String toString() { 
    return("("+o+"+t*"+c+")");
  }
  public Vector point(double a) {
    return(new Vector(o.x+a*c.x, o.y+a*c.y, o.z+a*c.z));
  }
}

class EyeRays extends Ray { // porta il rendering da 8 a 6 secondi
  private Vector d, h, v;
  private int i, maxx;
  EyeRays(Vector o, Vector d, Vector h, Vector v, int x, int y) {
    super(o, d);
    this.h=h.mul(1.0/x);
    this.v=v.mul(1.0/y);
    this.d=this.c.sub(this.h);
    maxx=x;
    i=0;
  }
  public void next() {
    if(i==maxx-1) {
      i=0;
      d=d.add(v);
      c=d;
    } else {
      c=c.add(h);
      i++;
    }
  }
}

class Color {
  protected double r, g, b;
  Color(double r, double g, double b) {
    this.r=r;
    this.g=g;
    this.b=b;
    if(this.r<0.0)
      this.r=0.0;
    if(this.g<0.0)
      this.g=0.0;
    if(this.b<0.0)
      this.b=0.0;
  }
  public static Color BLACK=new Color(0.0, 0.0, 0.0),
                      RED=new Color(1.0, 0.0, 0.0),
                      GREEN=new Color(0.0, 1.0, 0.0),
                      BLUE=new Color(0.0, 0.0, 1.0),
                      YELLOW=new Color(1.0, 1.0, 0.0),
                      CYAN=new Color(0.0, 1.0, 1.0),
                      PURPLE=new Color(1.0, 0.0, 1.0),
                      WHITE=new Color(1.0, 1.0, 1.0);
  public Color add(Color a) {
    return(new Color(r+a.r, g+a.g, b+a.b));
  }
  public Color sub(Color a) {
    return(new Color(r-a.r, g-a.g, b-a.b));
  }
  public Color mul(double a) {
    return(new Color(r*a, g*a, b*a));
  }
  public Color mul(Color a) {
    return(new Color(r*a.r, g*a.g, b*a.b));
  }
  public int getRGB() {
    int u=0,
        r=(int)(this.r*255),
        g=(int)(this.g*255),
        b=(int)(this.b*255);
    if(r>0)
      if(r<255)
        u=r;
      else
        u=255;
    u=u<<8;
    if(g>0)
      if(g<255)
        u|=g;
      else
        u|=255;
    u=u<<8;
    if(b>0)
      if(b<255)
        u|=b;
      else
        u|=255;
    return(u);
  }
  public int getARGB() {
    return(0xFF000000|getRGB());
  }
  public String toString() { 
    return("["+r+","+g+","+b+"]");
  }
}

class Light {
  protected Vector o;
  protected Color c;
  protected double p;
  Light(Vector o, Color c, double p) {
    this.o=o;
    this.c=c;
    this.p=p;
  }
}

abstract class Texture {
  abstract public Color color(Vector p);
  abstract public double reflect(Vector p);
}

class TexturePlain extends Texture {
  private Color c;
  private double r;
  TexturePlain(Color a, double b) {
    c=a;
    r=b;
  }
  public Color color(Vector p) {
    return(c);
  }
  public double reflect(Vector p) {
    return(r);
  }
}

class TextureChecker extends Texture {
  private Texture c[];
  TextureChecker(Texture a, Texture b) {
    c=new Texture[2];
    c[0]=a;
    c[1]=b;
  }
  public Color color(Vector p) {
    return(c[(((int)(p.x))^((int)p.y)^((int)p.z))&1].color(p));
  }
  public double reflect(Vector p) {
    return(c[(((int)p.x)^((int)p.y)^((int)p.z))&1].reflect(p));
  }
}

class TextureStrip extends Texture {
  private Texture c[];
  TextureStrip(Texture a, Texture b) {
    c=new Texture[2];
    c[0]=a;
    c[1]=b;
  }
  public Color color(Vector p) {
    return(c[((int)p.y)&1].color(p));
  }
  public double reflect(Vector p) {
    return(c[((int)p.y)&1].reflect(p));
  }
}

class TextureScale extends Texture {
  private Texture c;
  private double z;
  TextureScale(Texture a, double b) {
    c=a;
    z=1.0/b;
  }
  public Color color(Vector p) {
    return(c.color(p.mul(z)));
  }
  public double reflect(Vector p) {
    return(c.reflect(p.mul(z)));
  }
}

class Hit {
  public boolean h=false; // ha colpito o no?
  public double t;  // punto parametrico del raggio
  public Ray r;     // raggio generatore
  public Shape3D g; // oggetto generatore
  private Vector p; // [autocalcolato] punto colpito
  private Vector n; // [autocalcolato] normale
  private Color c;  // [autocalcolato] colore
  public void addT(double a) {
    if(a>1E-10) {
      if(h) {
        if(a<t)
          t=a;
      } else
        t=a;
      h=true;
    }
  }
  public Vector point() {
    if(p==null)
      p=r.point(t);
    return(p);
  }
  public Vector normal() {
    if(n==null)
      n=g.normal(point());
    return(n);
  }
  public Color color() {
    if(c==null)
      c=g.color(point());
    return(c);
  }
  public double reflect() {
    return(g.reflect(point()));
  }
}

abstract class Shape3D {
  abstract public Hit hit(Ray a);
  abstract public Vector normal(Vector p);
  abstract public double value(Vector p);
  abstract public Color color(Vector p);
  abstract public double reflect(Vector p);
  abstract public void scale(Vector i);     // cambia la figura di dimensioni
  abstract public void translate(Vector i); // trasla la figura
  abstract public void overturn();          // capovolge la figura (scambia interno ed esterno)
}

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
                   IPE_Y[]={ 1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       PIPPO[]={ 1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0};
  private double k[];
  private Texture c;
  Quadric(double a[], Texture b) {
    if(a.length!=10)
      throw(new NumberFormatException("Requires a 10 elements array"));      
    c=b;
    k=new double[10];
    //for(int i=0; i<10; i++)
    //  k[i]=a[i];
    System.arraycopy(a, 0, k, 0, 10);
  }
  public String toString() {
    String u="Quadric[";
    for(int i=0; i<10; i++)
      u+=k[i]+",";
    u+="texture:"+c+"]";
    return(u);
  }
  public Hit hit(Ray a) {
    // ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
    // x=o.x+c.x*t
    // y=o.y+c.y*t
    // z=o.z+c.z*t
    // ta²+tb+tc=0
    double tc=k[0]*(a.o.x*a.o.x)+
              k[1]*(a.o.x*a.o.y)+
              k[2]*(a.o.y*a.o.y)+
              k[3]*(a.o.x*a.o.z)+
              k[4]*(a.o.y*a.o.z)+
              k[5]*(a.o.z*a.o.z)+
              k[6]*a.o.x+
              k[7]*a.o.y+
              k[8]*a.o.z+
              k[9],
           tb=k[0]*(2*a.o.x*a.c.x)+
              k[1]*(a.o.x*a.c.y+a.o.y*a.c.x)+
              k[2]*(2*a.o.y*a.c.y)+
              k[3]*(a.o.x*a.c.z+a.o.z*a.c.x)+
              k[4]*(a.o.y*a.c.z+a.o.z*a.c.y)+
              k[5]*(2*a.o.z*a.c.z)+
              k[6]*a.c.x+
              k[7]*a.c.y+
              k[8]*a.c.z,
           ta=k[0]*(a.c.x*a.c.x)+
              k[1]*(a.c.x*a.c.y)+
              k[2]*(a.c.y*a.c.y)+
              k[3]*(a.c.x*a.c.z)+
              k[4]*(a.c.y*a.c.z)+
              k[5]*(a.c.z*a.c.z),
           delta=tb*tb-4.0*ta*tc;
    Hit u=new Hit();
    if (delta>=0.0) {
      double rdelta=Math.sqrt(delta);
      ta*=2.0;
      u.addT((-tb-rdelta)/ta);
      u.addT((-tb+rdelta)/ta);
      u.g=this;
      u.r=a;
    }
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
      k[3]*p.x+k[4]*p.y+2.0*k[5]*p.z+k[8]).vers());
  }
  public double value(Vector p) {
    // ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l
    return(k[0]*p.x*p.x+k[1]*p.x*p.y+k[2]*p.y*p.y+k[3]*p.x*p.z+k[4]*p.y*p.z+k[5]*p.z*p.z+k[6]*p.x+k[7]*p.y+k[8]*p.z+k[9]);
  }
  public Color color(Vector p) {
    return(c.color(p));
  }
  public double reflect(Vector p) {
    return(c.reflect(p));
  }
  public void scale(Vector i) {
    // ax²+bxy+cy²+dxz+eyz+fz²+gx+hy+iz+l=0
    // X=zx*x     x=X/zx
    // Y=zy*y ==> y=Y/zy
    // Z=zz*z     z=Z/zz
    i.x=1.0/i.x;
    i.y=1.0/i.y;
    i.z=1.0/i.z;
    k[0]*=i.x*i.x;
    k[1]*=i.x*i.y;
    k[2]*=i.y*i.y;
    k[3]*=i.x*i.z;
    k[4]*=i.y*i.z;
    k[5]*=i.z*i.z;
    k[6]*=i.x;
    k[7]*=i.y;
    k[8]*=i.z;
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
    k[9]+=(k[0]*i.x*i.x)+(k[2]*i.y*i.y)+(k[5]*i.z*i.z)+ // prima perché‚ usa 678
          (k[1]*i.x*i.y)+(k[3]*i.x*i.z)+(k[4]*i.y*i.z)-
          (k[6]*i.x)+(k[7]*i.y)+(k[8]*i.z);
    k[6]-=(2*k[0]*i.x)+(k[1]*i.y)+(k[3]*i.z);
    k[7]-=(k[1]*i.x)+(2*k[2]*i.y)+(k[4]*i.z);
    k[8]-=(k[3]*i.x)+(k[4]*i.y)+(2*k[5]*i.z);
  }
  public void overturn() {
    for(int i=0; i<10; i++)
      k[i]=-k[i];
  }
}

class Plane extends Shape3D {
  // ax+by+cz+d=0
  private Vector n; // (a, b, c)
  private double d;
  private Texture c;
  Plane(Vector a, double e, Texture b) {
    c=b;
    n=a;
    d=e/n.mod(); // scala la costante perché non cambi nulla se n è versore
    n=n.vers();
  }
  public Hit hit(Ray a) {
    // ax+by+cz+d=0
    // x=o.x+c.x*t
    // y=o.y+c.y*t
    // z=o.z+c.z*t
    Hit u=new Hit();
    double t=n.dot(a.c);
    if(t!=0.0) {
      u.addT(-(d+n.dot(a.o))/t);
      u.g=this;
      u.r=a;
    }
    return(u);  
  }
  public Vector normal(Vector p) {
    return(n);
  }
  public double value(Vector p) {
    // ax+by+cz+d
    return(n.dot(p)+d);
  }
  public Color color(Vector p) {
    return(c.color(p));
  }
  public double reflect(Vector p) {
    return(c.reflect(p));
  }
  public void scale(Vector i) {
    System.out.println("ILLEGAL SCALE");
  }
  public void translate(Vector i) {
    System.out.println("ILLEGAL TRANSLATE");
  }
  public void overturn() {
    n=n.mul(-1.0);
    d=-d;
  }
}

abstract class CSG_Collection extends Shape3D {
  protected Shape3D[] s=new Shape3D[10];
  protected int n=0;
  public void add(Shape3D a) {
    s[n++]=a;
  }
}

class CSG_Union extends CSG_Collection {
  public Hit hit(Ray a) {
    Hit l=s[0].hit(a), z;
    int i;
    for(i=1; i<n; i++) {
      z=s[i].hit(a);
      if(z.h)
        if(z.t>1E-10)
          if((z.t<l.t)||(!l.h))
            l=z;
    }
    return(l);
  }
  public Vector normal(Vector p) {
    System.out.println("ILLEGAL NORMAL");
    return(Vector.ORIGIN);
  }
  public double value(Vector p) {
    System.out.println("ILLEGAL VALUE");
    return(0); // FARE!!
  }
  public Color color(Vector p) {
    System.out.println("ILLEGAL COLOR");
    return(Color.BLACK);
  }
  public double reflect(Vector p) {
    System.out.println("ILLEGAL REFLECT");
    return(0);
  }
  public void scale(Vector i) {
    System.out.println("ILLEGAL SCALE");
  }
  public void translate(Vector i) {
    System.out.println("ILLEGAL TRANSLATE");
  }
  public void overturn() {
    System.out.println("ILLEGAL OVERTURN");
  }
}

class CSG_Intersection extends CSG_Collection {
  public Hit hit(Ray a) {
    Hit l=new Hit(), z;
    int i;
    for(i=0; i<n; i++) {
      z=s[i].hit(a);
      if(z.h)
        if(z.t>1E-10) { // è un hit, ora controllo se tutti gli altri sono "dentro"
          boolean v=true;
          int i2;
          for(i2=0; (i2<n)&&v; i2++)
            if(i2!=i)
              if(s[i2].value(z.point())>0.0)
                v=false;
          if(v)
            if((z.t<l.t)||(!l.h))
              l=z;
        }
    }
    return(l);
  }
  public Vector normal(Vector p) {
    System.out.println("ILLEGAL NORMAL");
    return(Vector.ORIGIN);
  }
  public double value(Vector p) {
    System.out.println("ILLEGAL VALUE");
    return(0); // FARE!!
  }
  public Color color(Vector p) {
    System.out.println("ILLEGAL COLOR");
    return(Color.BLACK);
  }
  public double reflect(Vector p) {
    System.out.println("ILLEGAL REFLECT");
    return(0);
  }
  public void scale(Vector i) {
    System.out.println("ILLEGAL SCALE");
  }
  public void translate(Vector i) {
    System.out.println("ILLEGAL TRANSLATE");
  }
  public void overturn() {
    System.out.println("ILLEGAL OVERTURN");
  }
}

class Scene {
  private Shape3D s;
  private Light l;
  protected Vector eye;
  Scene(Shape3D a, Light b, Vector c) {
    s=a;
    l=b;
    eye=c;
  }
  public Color hit(Ray a) {
    Hit h=s.hit(a);
    Color c;
    if(!h.h)
      c=Color.BLACK; // mettere qua il cielo
    else {
      Ray rl=new Ray(h.point(), l.o);
      Hit hl=s.hit(rl);
      if((hl.h)&&(hl.t<1.0)) // se c'è qualcosa PRIMA della luce
        c=Color.BLACK;
      else if(h.reflect()>0.999)
        c=Color.BLACK;
      else
        c=l.c.mul(h.normal().dot(rl.c.vers())).mul(l.p/rl.c.mod2());
      if(h.reflect()>0.001) {
        Color nc=Color.BLACK; // anche qua il cielo
        Ray rr=new Ray(h.point(), Vector.ORIGIN);
        rr.c=a.c.mirror(h.normal());
        Hit hs=s.hit(rr);
        if(hs.h&&(hs.t>1E-10)) {
          Ray rls=new Ray(hs.point(), l.o);
          nc=l.c.mul(hs.normal().dot(rls.c.vers())).mul(l.p/rls.c.mod2());
          nc=hs.color().mul(nc);
        }
        c=c.mul(1.0-h.reflect()).add(nc.mul(h.reflect()));
      }
      c=h.color().mul(c); // messo dopo per filtrare anche lo specchio
      //c=h.color(); // FLAT SHADED
    }
    return(c);
  }
}

class RenderThread extends Thread {
  private Dimension size;
  private int buff[];
  private MemoryImageSource src;
  private Thread t;
  private long timer;
  private Scene scn;
  private int numCPU, actCPU;
  RenderThread(Scene s, Dimension sz, int b[], MemoryImageSource sr, int act, int num) {
    scn=s;
    size=sz;
    buff=b;
    src=sr;
    numCPU=num;
    actCPU=act;
  }
  public void run() {
    int i, j, sizY=size.height/numCPU;
    EyeRays r=new EyeRays(scn.eye, new Vector(-2.0, 2.0-(4.0/numCPU*actCPU), 0.0),
                                   new Vector( 4.0, 0.0, 0.0),
                                   new Vector( 0.0,-4.0, 0.0),
                                   size.width, size.height);
    timer=System.currentTimeMillis();
    for (j=actCPU*sizY; j<(actCPU+1)*sizY; j++) { // CPU a blocchi
    //for (j=actCPU; j<size.height; j+=numCPU) { // CPU interlacciate
      for (i=0; i<size.width; i++, r.next())
        buff[j*size.width+i]=scn.hit(r).getARGB();
      src.newPixels(0, j, size.width, 1);
    }
    //src.newPixels(0, actCPU*sizY, size.width, sizY);
    System.out.println("CPU:"+(actCPU+1)+"/"+numCPU+" Time:"+(System.currentTimeMillis()-timer));
  }
}

class RayTracer extends Component {
  private String VERSION="RayTraccio 0.99 (c)1999 Lapo Luchini";
  private Dimension size;
  private Image img;
  private int buff[];
  private MemoryImageSource src;
  private Scene scn;
  private int numCPU, scale;
  private RenderThread t[];  
  RayTracer(Scene s, int n, int sc) {
    scn=s;
    numCPU=n;
    scale=sc;
  }
  public void init() {
    int i, j;
    size=getSize();
    buff=new int[size.width*size.height];
    src=new MemoryImageSource(size.width, size.height, buff, 0, size.width);
    src.setAnimated(true);
    img=createImage(src);
    for(j=0; j<size.height; j++)
      for(i=0; i<size.width; i++)
        buff[j*size.width+i]=0xFF7F7F7F;
    t=new RenderThread[numCPU];
  }
  public void start() {
    int i;
    for(i=0; i<numCPU; i++)
      t[i]=new RenderThread(scn, size, buff, src, i, numCPU);
    for(i=0; i<numCPU; i++)
      t[i].start();
  }
  public void stop() {
    int i;
    for(i=0; i<numCPU; i++)
      t[i]=null;
  }
  public void paint(Graphics g) {
    if(scale==1)
      g.drawImage(img, 0, 0, this);
    else
      g.drawImage(img, 0, 0, size.width*scale, size.height*scale, this);
    g.drawString(VERSION, 10, size.height*scale-10);
  }
}

public class RayTraccio extends Applet {
  private RayTracer rt;
  private Dimension size;
  private int scala=1;
  private Scene scn;
  public void setRenderSize(Dimension s, int sc) {
    size=s;
    scala=sc;
  }
  public void init() {
    CSG_Union u=new CSG_Union();
    Shape3D t;
    u.add(new Plane(Vector.VERS_Y, 0.5, new TexturePlain(Color.WHITE, 0.5)));
    t=new Quadric(Quadric.SFERA,
                  new TextureScale(
                    new TextureStrip(
                      new TexturePlain(Color.CYAN, 0.0),
                      new TexturePlain(Color.RED, 0.0)),
                  0.1));
    t.scale(new Vector(1.2, 1.0, 0.8));
    t.translate(new Vector(1.0, 1.0, 0.0));
    u.add(t);
    t=new Quadric(Quadric.IPE_Y,
                  new TextureScale(
                    new TextureChecker(
                      new TexturePlain(Color.BLUE, 0.0),
                      new TexturePlain(Color.YELLOW, 0.0)),
                  0.2));
    t.scale(new Vector(0.25, 0.25, 0.25));
    u.add(t);
    t=new Quadric(Quadric.SFERA,
                  new TexturePlain(Color.PURPLE, 0.0));
    t.scale(new Vector(0.2, 0.2, 0.2));
    t.translate(new Vector(0.5, 0.0, -2.0));
    u.add(t);
    CSG_Intersection u2=new CSG_Intersection();
    t=new Plane(Vector.VERS_Z.mul(-1.0),-2.75, new TexturePlain(Color.GREEN, 0.0));
    u2.add(t);
    t=new Quadric(Quadric.PARA_Y,
                  new TexturePlain(Color.GREEN, 0.0));
    t.scale(new Vector(0.5,-1.0, 0.5));
    t.translate(new Vector(-0.2, 0.0,-2.5));
    u2.add(t);
    u.add(u2);
    u2=new CSG_Intersection();
    t=new Plane((new Vector(1.0, 1.0,-0.8)).vers(),-0.5, new TexturePlain(Color.RED, 0.3));
    u2.add(t);
    t=new Quadric(Quadric.CIL_Y,
                  new TexturePlain(Color.RED, 0.3));
    t.scale(new Vector(0.25, 0.25, 0.25));
    t.translate(new Vector(-1.0, 0.0, -1.0));
    u2.add(t);
    u.add(u2);
    Light l=new Light(new Vector(1.0, 3.0, -5.0), Color.WHITE, 20.0),
          l2=new Light(new Vector(-1.0, 2.0, -4.0), Color.YELLOW, 3.0);
    Vector eye=new Vector(0.0, 0.0, -5.0);
    scn=new Scene(u, l, eye);
    if(size==null)
      size=getSize();
    rt=new RayTracer(scn, 1, scala);
    rt.setSize(size);
    rt.init();
    setLayout(new BorderLayout());
    add("Center", rt);
  }
  public void start() {
    rt.start();
  }
  public void stop() {
    rt.stop();
  }
  public void destroy() {
    remove(rt);
  }
  public void update(Graphics g) {
    // evito di cancellare prima della paint
    paint(g);
  }
  static class MyAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  }
  public static void main(String as[]) {
    int dimX=500, dimY=500, scala=1;
    if(as.length>=2) {
      dimX=Integer.parseInt(as[0]);
      dimY=Integer.parseInt(as[1]);
      if(as.length==3)
        scala=Integer.parseInt(as[2]);
    } else if(as.length!=0) {
      System.out.println("USO: java RayTraccio [dimX dimY [scala]]");
      System.exit(1);
    }
    Frame f=new Frame("RayTraccio");
    RayTraccio RT=new RayTraccio();
    RT.setRenderSize(new Dimension(dimX, dimY), scala);
    RT.init();
    RT.start();
    f.add("Center", RT);
    f.setSize(dimX*scala+20, dimY*scala+40);
    f.show();
    f.addWindowListener(new MyAdapter());
  }
  public String getAppletInfo() {
    return("RayTraccio Applet (contains only the RayTracer component)\r\n"+
           "(c)1999 Lapo Luchini");
  }
  //fare più luci!!
}
