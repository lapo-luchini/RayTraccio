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
import java.awt.image.*;
import java.awt.event.*;
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
	/** Valore preso da {@link status status} quando il processo non è ancora partito */
	public final static int WAITING = Integer.MIN_VALUE;
	/** Valore preso da {@link status status} quando il processo ha finito il suo lavoro */
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
