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
	try {
		scn=(new SDL(new java.io.FileReader("default.sdl"))).sdlScene();
	} catch(Exception e) {
		System.err.println(e);
		System.exit(1);
	}
	rt=new RayTracer(scn, 1, scala);
	rt.setSize(size);
	//if(savefile.length()>0)
	//  rt.setSaveFile(savefile);
	rt.init();
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
	f.setSize(dimX * scala + 20, dimY * scala + 40);
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
