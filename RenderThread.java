import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * Thread di rendering dell'immagine. <br>
 * Prevede di non essere l'unico thread, in modo da rendere facilmente multi-threaded il rendering stesso.
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
	protected int status = 0;
	/** Valore preso da {@link status status} quando ilprocesso ha finito ilsuo lavoro */
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
	                      Vector.ORIGIN.sub(scn.v),
	                      size.width, size.height);
	timer=System.currentTimeMillis();
	status=0;
	endY=(size.height*(actCPU+1))/numCPU;
	for (j=(size.height*actCPU)/numCPU; (j<endY)&&(running); j++) { // CPU a blocchi
	//for (j=actCPU; j<size.height; j+=numCPU) { // CPU interlacciate
		for (i=0; (i<size.width)&&(running); i++, r.next())
			buff[j*size.width+i]=scn.hit(r).getARGB();
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
