import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

class RayTracer extends Component {
  public String VERSION="RayTraccio 0.999b (c)1999 Lapo Luchini";
  private Dimension size;
  private Image img;
  private int buff[];
  private MemoryImageSource src;
  private Scene scn;
  private int numCPU, scale;
  private RenderThread t[];
RayTracer(Scene s, int n, int sc) {
	scn = s;
	numCPU = n;
	scale = sc;
}
public void init() {
	int i, j;
	size = getSize();
	buff = new int[size.width * size.height];
	src = new MemoryImageSource(size.width, size.height, buff, 0, size.width);
	src.setAnimated(true);
	img = createImage(src);
	for (j = 0; j < size.height; j++)
		for (i = 0; i < size.width; i++)
			buff[j * size.width + i] = 0xFF7F7F7F;
	t = new RenderThread[numCPU];
	for (i = 0; i < numCPU; i++)
		t[i] = new RenderThread(scn, size, buff, src, i, numCPU);
}
public void paint(Graphics g) {
	if (scale == 1)
		g.drawImage(img, 0, 0, this);
	else
		g.drawImage(img, 0, 0, size.width * scale, size.height * scale, this);
	g.drawString(VERSION, 10, size.height * scale - 10);
}
public void start() {
	int i;
	for (i = 0; i < numCPU; i++)
		t[i].start();
}
public void stop() {
	int i;
	for (i = 0; i < numCPU; i++)
		t[i] = null;
}
}
