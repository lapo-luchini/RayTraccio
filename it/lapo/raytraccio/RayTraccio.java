// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/RayTraccio.java,v 1.22 2002/02/23 16:41:16 lapo Exp $

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

package it.lapo.raytraccio;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.WindowListener;

/**
 * Applet dimostrativo del componente {@link RayTracer} (non contiene altro).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class RayTraccio extends java.applet.Applet implements WindowListener {

  protected RayTracer rt;
  protected Dimension size;
  protected int scala = 1;
  protected byte nthreads = 1;
  protected TextArea error;

  public void destroy() {
    remove(rt);
  }

  public String getAppletInfo() {
    return ("RayTraccio Applet (contains a RayTracer component)\r\n" +
	    "(c)2001-2004 Lapo Luchini");
  }

  public void init() {
    rt = new RayTracer();
    error = new TextArea();
    error.setEditable(false);
    if(size == null)
      size = getSize();
    rt.setSize(size);
    setLayout(new CardLayout());
    add(rt, "rt");
    add(error, "er");
    // la scena va inizializzata!!
  }

  public void initRenderer(Scene scn) {
    rt.init(scn, nthreads, size, scala, true);
  }

  public void setThreadNumber(byte n) {
    nthreads = n;
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

  // WindowListener interface

  public void windowActivated(java.awt.event.WindowEvent e) { /*empty*/ }
  public void windowClosed(java.awt.event.WindowEvent e) { /*empty*/ }
  public void windowClosing(java.awt.event.WindowEvent e) {
    System.exit(0);
  }
  public void windowDeactivated(java.awt.event.WindowEvent e) { /*empty*/ }
  public void windowDeiconified(java.awt.event.WindowEvent e) { /*empty*/ }
  public void windowIconified(java.awt.event.WindowEvent e) { /*empty*/ }
  public void windowOpened(java.awt.event.WindowEvent e) { /*empty*/ }

}
