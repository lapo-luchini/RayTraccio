import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

class Vector {
  protected double x, y, z;
  Vector(double x, double y, double z) {
    this.x=x;
    this.y=y;
    this.z=z;
  }
  public static Vector ORIGIN=new Vector(0.0, 0.0, 0.0);
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
  public String toString() { 
    return("Vector["+x+","+y+","+z+"]");
  }
}

class Ray {
  protected Vector o, c;
  Ray(Vector o, Vector a) {
    this.o=o;
    this.c=a.sub(o).vers();
  }
  public String toString() { 
    return("("+o+"+t*"+c+")");
  }
  public Vector point(double a) {
    return(new Vector(o.x+a*c.x, o.y+a*c.y, o.z+a*c.z));
  }
}

class Color {
  protected int r, g, b;
  Color(int r, int g, int b) {
    this.r=r;
    this.g=g;
    this.b=b;
  }
  public static Color BLACK=new Color(0, 0, 0),
                      RED=new Color(255, 0, 0),
                      GREEN=new Color(0, 255, 0),
                      BLUE=new Color(0, 0, 255),
                      YELLOW=new Color(255, 255, 0),
                      CYAN=new Color(0, 255, 255),
                      PURPLE=new Color(255, 0, 255),
                      WHITE=new Color(255, 255, 255);
  public Color add(Color a) {
    return(new Color(r+a.r, g+a.g, b+a.b));
  }
  public Color sub(Color a) {
    return(new Color(r-a.r, g-a.g, b-a.b));
  }
  public Color mul(int a) {
    return(new Color(r*a, g*a, b*a));
  }
  public Color mul(double a) {
    return(new Color((int)(r*a), (int)(g*a), (int)(b*a)));
  }
  public Color mul(Color a) {
    return(new Color(r*a.r>>8, g*a.g>>8, b*a.b>>8));
  }
  public int getRGB() {
    int u=0;
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
  Light(Vector o, Color c) {
    this.o=o;
    this.c=c;
  }
}

class Hit {
  public boolean h=false; // ha colpito o no?
  public double t;  // punto parametrico del raggio
  public Shape3D g; // oggetto geenratore
  public Color c;   // colore
  public Ray r;     // raggio generatore
  public Vector normal() {
    if(h)
      return(r.point(t));
    else
      return(Vector.ORIGIN);
  }
}

abstract class Shape3D {
  abstract public Hit hit(Ray a);
  abstract public Vector normal(Vector p);
}

class Quadrica extends Shape3D {
  private double k[];
  private Color c;
  Quadrica(double a[], Color b) {
    if(a.length!=10)
      throw(new NumberFormatException("Requires a 10 elements array"));      
    c=b;
    k=new double[10];
    System.arraycopy(a, 0, k, 0, 10);
  }
  public static double SFERA[]={1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_X[]={0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_Y[]={1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_Z[]={1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,-1.0},
                       IPE_RIG_Y[]={1.0, 0.0, -1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_X_SML[]={0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-0.25},
                       SFERA_BIG[]={1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.5};
  public String toString() {
    String u="Quadrica[";
    for(int i=0; i<10; i++)
      u+=k[i]+",";
    u+="color:"+c+"]";
    return(u);
  }
  public Hit hit(Ray a) {
    Hit u=new Hit();
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
    if (delta>=0.0) {
      double rdelta=Math.sqrt(delta);
      u.h=true;
      ta*=2.0;
      if (ta>0.0) // voglio ritorni l'hit più vicino
        u.t=(-tb-rdelta)/ta;
      else
        u.t=(-tb+rdelta)/ta;
      u.g=this;
      u.c=c;
      u.r=a;
    } else {
      u.h=false;
    }
    return(u);
  }
  public Vector normal(Vector p) {
    return(new Vector(
      2.0*k[0]*p.x+k[1]*p.y+k[3]*p.z+k[6],
      k[1]*p.x+2.0*k[2]*p.y+k[4]*p.z+k[7],
      k[3]*p.x+k[4]*p.y+2.0*k[5]*p.z+k[8]).vers());
  }
}

class CSG_Union extends Shape3D {
  private Shape3D[] s=new Shape3D[10];
  private int n=0;
  public void add(Shape3D a) {
    s[n++]=a;
  }
  public Hit hit(Ray a) {
    Hit l=s[0].hit(a), z;
    int i;
    for(i=1; i<n; i++) {
      z=s[i].hit(a);
      if(z.h)
        if((z.t<l.t)||(!l.h))
          l=z;
    }
    return(l);
  }
  public Vector normal(Vector p) {
    System.out.println("ILLEGAL NORMAL");
    return(Vector.ORIGIN);
  }
}

class RayTracer extends Component implements Runnable {
  private Dimension size;
  private Image img;
  private int buff[];
  private MemoryImageSource src;
  private Thread t;
  private long timer;
  public void init() {
    int i, j;
    size=getSize();
    buff=new int[size.width*size.height];
    src=new MemoryImageSource(size.width, size.height, buff, 0, size.width);
    src.setAnimated(true);
    img=createImage(src);
    for(j=0; j<size.height; j++)
      for(i=0; i<size.width; i++)
        buff[j*size.width+i]=0x7F000000;
  }
  public void start() {
    t=new Thread(this);
    t.start();
    timer=System.currentTimeMillis();
  }
  public void stop() {
    t=null;
  }
  public void run() {
    //Quadrica q=new Quadrica(Quadrica.SFERA, Color.CYAN);
    //Quadrica q=new Quadrica(Quadrica.CIL_X_SML, Color.CYAN);
    CSG_Union q=new CSG_Union();
    q.add(new Quadrica(Quadrica.SFERA_BIG, Color.CYAN));
    q.add(new Quadrica(Quadrica.CIL_X_SML, Color.RED));
    q.add(new Quadrica(Quadrica.CIL_Y, Color.BLUE));
    Light l=new Light(new Vector(1.0, 3.0, -5.0), Color.WHITE);
    Vector eye=new Vector(0.0, 0.0, -5.0);
    Color c;
    int i, j;
    for (j=0; j<size.height; j++) {
      for (i=0; i<size.width; i++) {
        Vector v=new Vector((size.width/2.0-i)*4.0/size.width, (size.height/2.0-j)*4.0/size.height, 0.0);
        Ray r=new Ray(eye, v);
        Hit h=q.hit(r);
        if(h.h) {
          Vector p=r.point(h.t);
          c=h.c.mul(h.g.normal(p).dot(l.o.sub(p).vers())).mul(l.c);
        } else
          c=Color.BLACK;
        buff[j*size.width+i]=c.getARGB();
      }
      src.newPixels(0, j, size.width, 1);
    }
    System.out.println("Timer:"+(System.currentTimeMillis()-timer));
  }
  public void paint(Graphics g) {
    g.drawImage(img, 0, 0, this);
  }
}

public class RayTraccio extends Applet {
  private RayTracer rt;
  private int dimX=500, dimY=500;
  public void setRenderSize(int x, int y) {
    dimX=x;
    dimY=y;
  }
  public void init() {
    setLayout(new BorderLayout());
    rt=new RayTracer();
    rt.setSize(dimX, dimY);
    rt.init();
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
    public void processEvent(AWTEvent e) {
        if (e.getID() == Event.WINDOW_DESTROY) {
            System.exit(0);
        }
    }
  public static void main(String as[]) {
    int dimX=500, dimY=500;
    if(as.length==2) {
      dimX=Integer.parseInt(as[0]);
      dimY=Integer.parseInt(as[1]);
    } else if(as.length!=0) {
      System.out.println("USO: java RayTraccio [dimX dimY]");
      System.exit(1);
    }
    Frame f=new Frame("RayTraccio");
    RayTraccio RT=new RayTraccio();
    RT.setRenderSize(dimX, dimY);
    RT.init();
    RT.start();
    f.add("Center", RT);
    f.setSize(dimX+40, dimY+40);
    f.show();
  }
  public String getAppletInfo() {
    return("RayTraccio 0.7\r\n"+
           "(c)1999 Lapo Luchini");
  }
}
