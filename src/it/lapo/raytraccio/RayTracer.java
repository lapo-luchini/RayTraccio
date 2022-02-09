// RayTraccio ray-tracing library Copyright (c) 2001-2022 Lapo Luchini <lapo@lapo.it>

package it.lapo.raytraccio;
import it.lapo.raytraccio.shape.ShapeStats;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

/**
 * Componente che gestisce il ray-tracing e la visualizzazione di una
 * scena data.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class RayTracer extends Component {

  public String VERSION = "RayTraccio 1.90 © 2001-2022 Lapo Luchini";

  /** Oggetto origine dell'immagine */
  private MemoryImageSource src = null;

  /** Array di thread di rendering */
  private RenderThread t[] = null;

  /** Array di thread di rendering dell'antialias */
  private RenderThreadAntiAlias t_aa;

  /** Oggetto immagine */
  private Image img = null;

  private java.awt.Dimension renderSize;
  private int scale;
  private long timer;

  public RayTracer() {
      // empty
  }

  public void doneLine(int l) {
    src.newPixels(0, l, renderSize.width, 1); // aggiorna l'immagine riga per riga
  }

  /**
   * Stampa un numero cno precisione data.
   * @returns una stringa lunga massimo <code>p</code> caratteri
   * @param d numero da stampare
   * @param p numero di caratteri da tenere
   */
  private String doublePrecision(double d, byte p) {
    String s=(new Double(d)).toString();
    if(s.length()>p)
      s=s.substring(0, p);
    return(s);
  }

  public Dimension getMinimumSize() {
    return(renderSize);
  }

  public Dimension getPreferredSize() {
    return(renderSize);
  }

  public void init(Scene scene, int numCPU, Dimension size, int scale, boolean antialias) {
    renderSize = size;
    setSize(size);
    renderSize.width /= scale;
    renderSize.height /= scale;
    this.scale = scale;
    int buff[] = new int[renderSize.width * renderSize.height];
    src = new MemoryImageSource(renderSize.width, renderSize.height, buff, 0, renderSize.width);
    src.setAnimated(true);
    img = createImage(src);
    for (int j = 0; j < renderSize.height; j++)
      for (int i = 0; i < renderSize.width; i++)
	buff[j * renderSize.width + i] = 0xFF7F7F7F;
    if (t != null)
      this.stop();
    t = new RenderThread[numCPU];
    for (int i = 0; i < numCPU; i++)
      t[i] = new RenderThread(scene, renderSize, buff, i, numCPU, this);
    if (antialias)
      t_aa = new RenderThreadAntiAlias(scene, renderSize, buff, this);
    else
      t_aa = null;
    repaint(100); // forza la pulizia dello schermo entro 0.1 secondi
  }

  public void paint(Graphics g) {
    if (img != null)
      g.drawImage(img, 0, 0, renderSize.width * scale, renderSize.height * scale, this);
    else
      super.paint(g);
    g.drawString(VERSION, 10, getSize().height - 10);
  }

  public void start() {
    timer = System.currentTimeMillis();
    System.out.println("Raytracing [ETA=0ms]");
    for (int i = 0; i < t.length; i++)
      t[i].start();
  }

  public void stop() {
    System.out.println("Aborting [ETA="+(System.currentTimeMillis()-timer)+"ms]");
    int i;
    for (i = 0; i < t.length; i++)
      if (t[i] != null)
	t[i].requestToStop();
    if (t_aa != null)
      t_aa.requestToStop();
    for (i = 0; i < t.length; i++)
      t[i] = null;
    t_aa = null;
  }

  synchronized protected void threadFinished() {
    for (int i = 0; i < t.length; i++)
      if (t[i] != null) {
	if (t[i].getStatus() != RenderThread.FINISHED)
	  return;
	else
	  t[i] = null;
      }
    if (t_aa != null) {
      int aas = t_aa.getStatus();
      if (aas == RenderThread.WAITING) {
	System.out.println("Antialiasing [ETA="+(System.currentTimeMillis()-timer)+"ms]");
	t_aa.start();
	return;
      } else if (aas != RenderThread.FINISHED)
	return;
      else
	t_aa = null;
    }
    System.out.println("Finished [ETA="+(System.currentTimeMillis()-timer)+"ms]");
    System.out.println("SHAPE3D\tRAYS\tEYE R\tCACHED\tRAY %\tHITS\t%");
    int t;
    for(byte i=0; i<ShapeStats.getEntityCount(); i++) {
      t=ShapeStats.getCount(i, ShapeStats.TYPE_RAY)+ShapeStats.getCount(i, ShapeStats.TYPE_EYERAY);
      System.out.println(
	ShapeStats.getEntityName(i)+ '\t' +
	ShapeStats.getCount(i, ShapeStats.TYPE_RAY) + '\t' +
	ShapeStats.getCount(i, ShapeStats.TYPE_EYERAY) + '\t' +
	ShapeStats.getCount(i, ShapeStats.TYPE_CACHEDRAY) + '\t' +
	doublePrecision(ShapeStats.getCount(i, ShapeStats.TYPE_CACHEDRAY)*100.0/t, (byte)6) + '\t' +
	ShapeStats.getCount(i, ShapeStats.TYPE_HIT) + '\t' +
	doublePrecision(ShapeStats.getCount(i, ShapeStats.TYPE_HIT)*100.0/t, (byte)6)
      );
    }
  }

}
