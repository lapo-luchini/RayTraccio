import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

class RenderThread extends Thread {
	private Dimension size;
	private int buff[];
	private MemoryImageSource src;
	private Thread t;
	private long timer;
	private Scene scn;
	private int numCPU, actCPU;
RenderThread(Scene s, Dimension sz, int b[], MemoryImageSource sr, int act, int num) {
	scn = s;
	size = sz;
	buff = b;
	src = sr;
	numCPU = num;
	actCPU = act;
}
public void run() {
	int i, j, sizY=size.height/numCPU;
	EyeRays r=new EyeRays(scn.eye,
	                      scn.c.sub(scn.v.mul((double)actCPU/numCPU)),
	                      scn.h,
	                      Vector.ORIGIN.sub(scn.v),
	                      size.width, size.height);
	timer=System.currentTimeMillis();
	for (j=actCPU*sizY; j<(actCPU+1)*sizY; j++) { // CPU a blocchi
	//for (j=actCPU; j<size.height; j+=numCPU) { // CPU interlacciate
	  for (i=0; i<size.width; i++, r.next()) {
		buff[j*size.width+i]=scn.hit(r).getARGB();
		//System.out.println(r);
	  }
	  src.newPixels(0, j, size.width, 1);
	}
	//src.newPixels(0, actCPU*sizY, size.width, sizY);
	System.out.println("CPU:"+(actCPU+1)+"/"+numCPU+" Time:"+(System.currentTimeMillis()-timer));
	System.out.println(Quadric.stats());
	System.out.println(Plane.stats());
}
}
