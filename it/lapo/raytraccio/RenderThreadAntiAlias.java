package it.lapo.raytraccio;

// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/RenderThreadAntiAlias.java,v 1.9 2002/02/23 16:41:16 lapo Exp $

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

/**
 * Thread specializzato per applicare l'anti-aliasing a un'immagine gi&agrave; creata. <br>
 * Viene utilizzato un metodo adattivo per calcolare solo i raggi necessari: per quei
 * raggi che differiscono da un loro vicino di almeno 30 livelli di colore (sommando le
 * differenze assolute delle tre componenti) vengono tracciati altri 8 raggi, posti in
 * modo da formare nel suo intorno un reticolo 3x3 volte pi&ugrave; denso, e il colore finale
 * viene posto alla media di tutti e nove. <br>
 * Il metodo potrebbe essere reso parametrico o addirittura ricorsivo per fargli
 * tracciare con sempre pi&ugrave; precisione l'edge tra i due colori (in modo da avere
 * sfumature sempre più continue sui bordi). <br>
 * Nota: a differenza della classe genitrice questa classe non supporta la ripartizione
 * del proprio lavoro in pi&ugrave; thread, anche perch&eacute; (per immagini particolarmente grandi,
 * dove potrebbe servire) questa classe esegue un compito notevolmente più breve,
 * dovendo tracciare solo le aree dove c'&egrave; un edge.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class RenderThreadAntiAlias extends RenderThread {

  /** {@link java.util.BitSet BitSet} che indica a quali raggi della riga precedente va applicato l'antialias */
  private java.util.BitSet bs_p;
  /** {@link java.util.BitSet BitSet} che indica a quali raggi della riga corrente va applicato l'antialias */
  private java.util.BitSet bs_a;

  /**
   * RenderThreadAntiAlias constructor comment.
   * @param s Scene
   * @param sz java.awt.Dimension
   * @param b int[]
   * @param sr java.awt.image.MemoryImageSource
   * @param act int
   * @param num int
   * @param rt RayTracer
   */
  RenderThreadAntiAlias(Scene s, java.awt.Dimension sz, int[] b, RayTracer rt) {
    super(s, sz, b, 0, 1, rt);
    bs_p = new java.util.BitSet(sz.width);
    bs_a = new java.util.BitSet(sz.width);
  }

  /**
   * Calcola la distanza tra i due colori RGB dati. <br>
   * Questa implementazione somma il valore assoluto delle differenze per-colore.
   * Creation date: (15/02/2001 13.56.48)
   * @return distanza tra i colori (da 0.0 a 3.0)
   * @param a primo colore
   * @param b secondo colore
   */
  private static int colorDiff(int a, int b) {
    int u;
    u =Math.abs(((a>>16)&0xFF)-((b>>16)&0xFF));
    u+=Math.abs(((a>> 8)&0xFF)-((b>> 8)&0xFF));
    u+=Math.abs(( a     &0xFF)-( b     &0xFF));
    return u;
  }

  private int doLine(int j, java.util.BitSet bs, EyeRays r) {
    int tmp, cnt=0;
    Color c;
    double t=1.0/3.0;
    for (int i=1; (i<size.width)&&(running); i++)
      if(bs.get(i)) {
  cnt++;
  tmp=j*size.width+i;
  c=new Color(
    ((buff[tmp]>>16)&0xFF)/255.0,
    ((buff[tmp]>> 8)&0xFF)/255.0,
    ( buff[tmp]     &0xFF)/255.0
  );
  // SUPERSAMPLING A 9
  c.addU(scn.hit(r.specificRay(i-t, j-t)));
  c.addU(scn.hit(r.specificRay(i-t, j  )));
  c.addU(scn.hit(r.specificRay(i-t, j+t)));
  c.addU(scn.hit(r.specificRay(i  , j-t)));
  c.addU(scn.hit(r.specificRay(i  , j+t)));
  c.addU(scn.hit(r.specificRay(i+t, j-t)));
  c.addU(scn.hit(r.specificRay(i+t, j  )));
  c.addU(scn.hit(r.specificRay(i+t, j+t)));
  c.mulU(1.0/9.0);
  buff[tmp]=c.getRGB();
  bs.clear(i); // così restano tutti a zero
      }
    rayt.doneLine(j);
    return(cnt);
  }

  public void run() {
    int i, j;
    EyeRays r=new EyeRays(
      scn.eye,
      scn.c.sub(scn.v.mul((double)actCPU/numCPU)),
      scn.h,
      Vector3D.ORIGIN.sub(scn.v),
      size.width, size.height
    );
    int cnt=0;
    java.util.BitSet bs_t;
    status=0;
    for (j=1; (j<size.height)&&(running); j++) {
      // calcola quali vanno antialiasati
      for (i=1; (i<size.width)&&(running); i++) {
  if(colorDiff(buff[j*size.width+i], buff[j*size.width+i-1])>30) {
    bs_a.set(i);
    bs_a.set(i-1);
  }
  if(colorDiff(buff[j*size.width+i], buff[(j-1)*size.width+i])>30) {
    bs_a.set(i);
    bs_p.set(i);
  }
  //buff[j*size.width+i]=scn.hit(r).getARGB();
      }
      // antialiasa la riga precedente
      cnt+=doLine(j-1, bs_p, r);
      bs_t=bs_p;
      bs_p=bs_a;
      bs_a=bs_t;
      status++;
    }
    cnt+=doLine(j-1, bs_p, r);
    status=FINISHED;
    System.out.println("Oversampled "+cnt+"/"+(size.width*size.height)+" pixels ("+(cnt*800/(size.width*size.height))+"% overhead)");
    rayt.threadFinished();
  }

}
