// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/RayTraccio.java,v 1.21 2001/04/27 08:38:04 lapo Exp $

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
  private TextArea error;

  public void destroy() {
    remove(rt);
  }

  public String getAppletInfo() {
    return ("RayTraccio Applet (contains a RayTracer component)\r\n" +
	    "(c)2001 Lapo Luchini");
  }

  public void init() {
    rt=new RayTracer();
    error=new TextArea();
    error.setEditable(false);
    if(size==null)
      size=getSize();
    rt.setSize(size);
    setLayout(new CardLayout());
    add(rt, "rt");
    add(error, "er");
    reInit("default.sdl");
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
    reInit("default.sdl");
  }

  public void reInit(String str) {
    Scene scn=null;
    java.io.Reader r=null;
    if(str.substring(str.length()-4, str.length()).equalsIgnoreCase(".sdl")) { // is a filename
      try {
	// tries online
	java.net.URL u=new java.net.URL(getCodeBase(), str);
	System.out.println("Loading URL "+u);
	r=new java.io.InputStreamReader(u.openStream());
      } catch(Exception e) {
	// tries offline
	try {
	  System.out.println("Loading local file default.sdl");
	  r=new java.io.FileReader(str);
	}  catch(Exception e2) {
	  r=null;
	}
      }
      if(r==null)
	throw new RuntimeException("Could not load file: "+str);
    } else {
      r=new java.io.StringReader(str);
      if(r==null)
	throw new RuntimeException("Could not load string data.");
    }
    //System.out.println("Using following Reader: "+r);
    rt.setVisible(true);
    error.setVisible(false);
    try {
      long t=System.currentTimeMillis();
      scn=(new SDL(r)).sdlScene();
      System.out.println("Parsed in "+(System.currentTimeMillis()-t)+"ms");
      rt.init(scn, 1 /*threads*/, size, scala, true);
      ((CardLayout)getLayout()).show(this, "rt");
    } catch(Exception e) {
      scn=null;
      error.setText(e.toString());
      ((CardLayout)getLayout()).show(this, "er");
      System.err.println(e);
    }
  }

  public void setRenderSize(Dimension s, int sc) {
    size = s;
    scala = sc;
  }

  public void start() {
    if (scn == null)
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