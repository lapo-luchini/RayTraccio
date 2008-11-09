// RayTraccio ray-tracing library Copyright (c) 2001-2008 Lapo Luchini <lapo@lapo.it>

// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

package it.lapo.raytraccio;
import java.awt.Dimension;

/**
 * Thread di rendering dell'immagine. <br>
 * Prevede di non essere l'unico thread, in modo da rendere facilmente multi-threaded il rendering stesso.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class RenderThread extends Thread {

  /** Dimensione dell'immagine da produrre */
  protected Dimension size;
  /** Buffer dell'immagine completa (riferimento) */
  protected int buff[];
  /** Usato internamente per statistica. */
  protected long timer;
  /** Scena da renderizzare (riferimento) */
  protected Scene scn;
  /** Numero di thread totali che renderizzeranno questa scena */
  protected int numCPU;
  /** Numero di questo thread (0-based) */
  protected int actCPU;
  /** RayTracer da avvertire quando si concludono righe e quadri */
  protected RayTracer rayt;
  /** Valore che indica se si deve proseguire o fermarsi */
  protected boolean running = true;
  /** Valore che indica lo stato del processo */
  protected int status = WAITING;
  /** Valore preso da {@link #status status} quando il processo non &egrave; ancora partito */
  public final static int WAITING = Integer.MIN_VALUE;
  /** Valore preso da {@link #status status} quando il processo ha finito il suo lavoro */
  public final static int FINISHED = Integer.MAX_VALUE;

  RenderThread(Scene s, Dimension sz, int b[], int act, int num, RayTracer rt) {
    scn = s;
    size = sz;
    buff = b;
    numCPU = num;
    actCPU = act;
    rayt = rt;
  }

  public int getStatus() {
    return(status);
  }

  public void requestToStop() {
    running = false;
  }

  public void run() {
    int i, j, endY;
    EyeRays r=new EyeRays(scn.eye,
			  scn.c.sub(scn.v.mul((double)actCPU/numCPU)),
			  scn.h,
			  Vector3D.ORIGIN.sub(scn.v),
			  size.width, size.height);
    timer=System.currentTimeMillis();
    status=0;
    endY=(size.height*(actCPU+1))/numCPU;
    for (j=(size.height*actCPU)/numCPU; (j<endY)&&(running); j++) { // CPU a blocchi
    //for (j=actCPU; j<size.height; j+=numCPU) { // CPU interlacciate
      for (i=0; (i<size.width)&&(running); i++, r.next())
	buff[j*size.width+i]=scn.hit(r).getRGB();
      rayt.doneLine(j); // aggiorna l'immagine riga per riga
      status++;
    }
    status=FINISHED;
    rayt.threadFinished();
    /*if(running) {
      System.out.println("CPU:"+(actCPU+1)+"/"+numCPU+" Time:"+(System.currentTimeMillis()-timer));
      System.out.println(Quadric.stats());
      System.out.println(Plane.stats());
    }*/
  }

}
