// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header$

// This file is part of RayTraccio.
//
// RayTraccio is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// RayTraccio is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RayTraccio; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

import java.awt.*;
import java.awt.event.*;
/**
 * Applet dimostrativo del componente {@link RayTracer} (non contiene altro).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class RayTraccio extends java.applet.Applet implements WindowListener {
	private RayTracer rt;
	private Dimension size;
	private int scala = 1;
	private Scene scn;
public void destroy() {
	remove(rt);
}
public String getAppletInfo() {
	return ("RayTraccio Applet (contains a RayTracer component)\r\n" +
		      "(c)2001 Lapo Luchini");
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
	if(r==null)
		throw new RuntimeException("Could not load default.sdl");
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
	int dimX = 300, dimY = 300, scala = 3;
	if (as.length >= 2) {
		dimX = Integer.parseInt(as[0]);
		dimY = Integer.parseInt(as[1]);
		scala = 1;
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
	f.addWindowListener(RT);
}
public void reInit() {
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
	if(r==null)
		throw new RuntimeException("Could not load default.sdl");
	//System.out.println("Using following Reader: "+r);
	try {
		scn=(new SDL(r)).sdlScene();
		//r.close();
	} catch(Exception e) {
		System.err.println(e);
		System.exit(2);
	}
	//System.out.println("Using following Scene: "+scn);
	//rt=new RayTracer();
	//if(savefile.length()>0)
	//  rt.setSaveFile(savefile);
	if(size==null)
	  size=getSize();
	rt.setSize(size);
	rt.init(scn, 1 /*threads*/, size, scala, true);
	setLayout(new BorderLayout());
	add("Center", rt);
}  
public void reInit(String scene) {
	Scene scn=null;
	java.io.Reader r=new java.io.StringReader(scene);
	if(r==null) {
		throw new RuntimeException("Could not load string data.");
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
	//rt=new RayTracer();
	//if(savefile.length()>0)
	//  rt.setSaveFile(savefile);
	if(size==null)
	  size=getSize();
	rt.setSize(size);
	rt.init(scn, 1 /*threads*/, size, scala, true);
	setLayout(new BorderLayout());
	add("Center", rt);
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
public void windowActivated(java.awt.event.WindowEvent e) {}
public void windowClosed(java.awt.event.WindowEvent e) {}
public void windowClosing(java.awt.event.WindowEvent e) {
	System.exit(0);
}
public void windowDeactivated(java.awt.event.WindowEvent e) {}
public void windowDeiconified(java.awt.event.WindowEvent e) {}
public void windowIconified(java.awt.event.WindowEvent e) {}
public void windowOpened(java.awt.event.WindowEvent e) {}
}
