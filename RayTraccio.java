import java.awt.*;
import java.awt.event.*;

public class RayTraccio extends java.applet.Applet {
	private RayTracer rt;
	private Dimension size;
	private int scala = 1;
	private Scene scn;
public void destroy() {
	remove(rt);
}
public String getAppletInfo() {
	return ("RayTraccio Applet (contains a RayTracer component)\r\n" +
		      "(c)1999 Lapo Luchini");
}
public void init() {
	Scene scn=null;
	java.io.Reader r;
	try { // tries online
		java.net.URL u=new java.net.URL(getCodeBase(), "default.sdl");
		System.out.println("Loading URL "+u);
		r=new java.io.InputStreamReader(u.openStream());
	} catch(Exception e) { // tries offline
		r=null;
		try {
			System.out.println("Loading local file default.sdl");
			r=new java.io.FileReader("default.sdl");
		}	catch(Exception e2) {
			r=null;
		}
	}
	if(r==null) {
		System.err.println("Could not load default.sdl");
		System.exit(1);
	}
	//System.out.println("Using following Reader: "+r);
	try {
		scn=(new SDL(r)).sdlScene();
		//r.close();
	} catch(Exception e) {
		System.err.println(e);
		System.exit(2);
	}
	//System.out.println("Using following Scene: "+scn);
	rt=new RayTracer();
	//if(savefile.length()>0)
	//  rt.setSaveFile(savefile);
	if(size==null)
	  size=getSize();
	rt.setSize(size);
	rt.init(scn, 1 /*threads*/, size, scala, true);
	setLayout(new BorderLayout());
	add("Center", rt);
}  
public static void main(String as[]) {
	int dimX = 100, dimY = 100, scala = 5;
	if (as.length >= 2) {
		dimX = Integer.parseInt(as[0]);
		dimY = Integer.parseInt(as[1]);
		if (as.length >= 3) {
			scala = Integer.parseInt(as[2]);
		}
	} else if (as.length != 0) {
		System.out.println("USE: java RayTraccio [dimX dimY [scale]]");
		System.exit(1);
	}
	Frame f = new Frame("RayTraccio");
	RayTraccio RT = new RayTraccio();
	RT.setRenderSize(new Dimension(dimX, dimY), scala);
	RT.init();
	RT.start();
	f.add("Center", RT);
	f.setSize(dimX + 20, dimY + 40);
	f.show();
	f.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	});
}
public void setRenderSize(Dimension s, int sc) {
	size = s;
	scala = sc;
}
public void start() {
	rt.start();
}
public void stop() {
	rt.stop();
}
public void update(Graphics g) {
	// evito di cancellare prima della paint
	paint(g);
}
}
