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
  protected double p;
  Light(Vector o, Color c, double p) {
    this.o=o;
    this.c=c;
    this.p=p;
  }
}

abstract class Texture {
  abstract public Color color(Vector p);
}

class TexturePlain extends Texture {
  private Color c;
  TexturePlain(Color a) {
    c=a;
  }
  public Color color(Vector p) {
    return(c);
  }
}

class TextureChecker extends Texture {
  private Color c[];
  TextureChecker(Color a, Color b) {
    c=new Color[2];
    c[0]=a;
    c[1]=b;
  }
  public Color color(Vector p) {
    return(c[(((int)p.x)^((int)p.y)^((int)p.z))&0x01]);
  }
}

class TextureStrip extends Texture {
  private Color c[];
  TextureStrip(Color a, Color b) {
    c=new Color[2];
    c[0]=a;
    c[1]=b;
  }
  public Color color(Vector p) {
    return(c[((int)p.y)&0x01]);
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
}

class Hit {
  public boolean h=false; // ha colpito o no?
  public double t;  // punto parametrico del raggio
  public Ray r;     // raggio generatore
  public Shape3D g; // oggetto generatore
  private Vector p; // [autocalcolato] punto colpito
  private Vector n; // [autocalcolato] normale
  private Color c;  // [autocalcolato] colore
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
}

abstract class Shape3D {
  abstract public Hit hit(Ray a);
  abstract public Vector normal(Vector p);
  abstract public Color color(Vector p);
  abstract public void scale(Vector i);
  abstract public void translate(Vector i);
}

class Quadrica extends Shape3D {
  private double k[];
  private Texture c;
  Quadrica(double a[], Texture b) {
    if(a.length!=10)
      throw(new NumberFormatException("Requires a 10 elements array"));      
    c=b;
    k=new double[10];
    //for(int i=0; i<10; i++)
    //  k[i]=a[i];
    System.arraycopy(a, 0, k, 0, 10);
  }
  public static double SFERA[]={1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_X[]={0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_Y[]={1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       CIL_Z[]={1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,-1.0},
                   IPE_RIG_Y[]={1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-1.0},
                       PIPPO[]={1.0, 0.0,-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,-0.1};
  public String toString() {
    String u="Quadrica[";
    for(int i=0; i<10; i++)
      u+=k[i]+",";
    u+="texture:"+c+"]";
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
  public Color color(Vector p) {
    return(c.color(p));
  }
  public void scale(Vector i) {
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
    i=i.mul(-1.0);
    k[9]+=(k[0]*i.x*i.x)+(k[2]*i.y*i.y)+(k[5]*i.z*i.z)+ // prima perch‚ usa 678
          (k[1]*i.x*i.y)+(k[3]*i.x*i.z)+(k[4]*i.y*i.z)+
          (k[6]*i.x)+(k[7]*i.y)+(k[8]*i.z);
    k[6]+=(2*k[0]*i.x)+(k[1]*i.y)+(k[3]*i.z);
    k[7]+=(k[1]*i.x)+(2*k[2]*i.y)+(k[4]*i.z);
    k[8]+=(k[3]*i.x)+(k[4]*i.y)+(2*k[5]*i.z);
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
  public Color color(Vector p) {
    System.out.println("ILLEGAL COLOR");
    return(Color.BLACK);
  }
  public void scale(Vector i) {
    System.out.println("ILLEGAL SCALE");
  }
  public void translate(Vector i) {
    System.out.println("ILLEGAL TRANSLATE");
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
    if(h.h) {
      Ray rl=new Ray(h.point(), l.o);
      Hit hl=s.hit(rl);
      if(hl.h) {
        if(hl.t>1E-10)
          c=Color.BLACK;
        else
          c=h.color().mul(h.normal().dot(rl.c.vers())).mul(l.c).mul(l.p/rl.c.mod());
      } else
        c=h.color().mul(h.normal().dot(rl.c.vers())).mul(l.c).mul(l.p/rl.c.mod());
    } else
      c=Color.BLACK;
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
    int i, j;
    EyeRays r=new EyeRays(scn.eye, new Vector(-2.0, 2.0-(4.0/numCPU*actCPU), 0.0),
                                   new Vector( 4.0, 0.0, 0.0),
                                   new Vector( 0.0,-4.0, 0.0),
                                   size.width, size.height);
    timer=System.currentTimeMillis();
    for (j=actCPU*(size.height/numCPU); j<(actCPU+1)*(size.height/numCPU); j++) { // CPU a blocchi
    //for (j=actCPU; j<size.height; j+=numCPU) { // CPU interlacciate
      for (i=0; i<size.width; i++, r.next())
        buff[j*size.width+i]=scn.hit(r).getARGB();
      src.newPixels(0, j, size.width, 1);
    }
    //src.newPixels(0, actCPU*(size.height/numCPU), size.width, size.height/numCPU);
    System.out.println("CPU"+(actCPU+1)+"/"+numCPU+" Time:"+(System.currentTimeMillis()-timer));
  }
}

class RayTracer extends Component {
  private Dimension size;
  private Image img;
  private int buff[];
  private MemoryImageSource src;
  private Scene scn;
  private int numCPU;
  private RenderThread t[];
  RayTracer(Scene s, int n) {
    scn=s;
    numCPU=n;
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
    g.drawImage(img, 0, 0, this);
  }
}

public class RayTraccio extends Applet {
  private RayTracer rt;
  private Dimension size;
  private Scene scn;
  public void setRenderSize(Dimension s) {
    size=s;
  }
  public void init() {
    Quadrica q1=new Quadrica(Quadrica.SFERA, new TextureScale(new TextureStrip(Color.CYAN, Color.WHITE), 0.1)),
             q2=new Quadrica(Quadrica.CIL_X, new TexturePlain(Color.RED)),
             q3=new Quadrica(Quadrica.IPE_RIG_Y, new TextureScale(new TextureChecker(Color.BLUE, Color.YELLOW), 0.2)),
             q4=new Quadrica(Quadrica.SFERA, new TexturePlain(Color.PURPLE));
    q1.scale(new Vector(1.2, 1.0, 0.8));
    q1.translate(new Vector(1.0, 1.0, 0.0));
    q2.scale(new Vector(0.5, 0.5, 0.5));
    q2.translate(new Vector(0.0, -1.0, -0.5));
    q3.scale(new Vector(0.25, 0.25, 0.25));
    q4.scale(new Vector(0.2, 0.2, 0.2));
    q4.translate(new Vector(0.5, 0.0, -2.0));
    CSG_Union q=new CSG_Union();
    q.add(q1);
    q.add(q2);
    q.add(q3);
    q.add(q4);
    //Quadrica q=new Quadrica(Quadrica.PIPPO, new TexturePlain(Color.RED));
    Light l=new Light(new Vector(1.0, 3.0, -5.0), Color.WHITE, 5.0),
          l2=new Light(new Vector(-1.0, 2.0, -4.0), Color.YELLOW, 3.0);
    Vector eye=new Vector(0.0, 0.0, -5.0);
    scn=new Scene(q, l, eye);
    if(size==null)
      size=getSize();
    rt=new RayTracer(scn, 1);
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
    RT.setRenderSize(new Dimension(dimX, dimY));
    RT.init();
    RT.start();
    f.add("Center", RT);
    f.setSize(dimX+40, dimY+40);
    f.show();
    f.addWindowListener(new MyAdapter());
  }
  public String getAppletInfo() {
    return("RayTraccio 0.9\r\n"+
           "(c)1999 Lapo Luchini");
  }
  fare più luci!!
}
