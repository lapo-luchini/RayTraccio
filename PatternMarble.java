// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/PatternMarble.java,v 1.7 2001/04/27 08:50:16 lapo Exp $

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
 * Campo vettoriale di rumore di tipo marmo <code>0.5+0.5*cos(x+turbulence*noise.scalar(p))</code>. <br>
 * Questo tipo di marmo in realt&agrave; non &egrave; molto realistico, ci sono modi migliori,
 * come ad esempio applicare una turbolenza ad un pattern ad onda triangolare (applicare
 * {@link PatternTurbulence} a {@link PatternTriangle} applicato a {@link PatternSawtooth}).
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternMarble extends Pattern {

  private Pattern noise;
  private double turbulence;

  /**
   * Definisce un pattern di tipo marmo.
   * @param noise rumore da usare come base
   * @param incidence quanto il rumore perturba il pattern
   */
  public PatternMarble(Pattern noise, double turbulence) {
    this.noise=noise;
    this.turbulence=turbulence;
  }

  /**
   * Genera un campo vettoriale di rumore di tipo marmo.
   * @return double[dim] valore del rumore [0.0,+1.0)
   * @param p vettore posizione nel campo
   */
  public double scalar(Vector p) {
    return(0.5+0.5*Math.cos(p.x+turbulence*noise.scalar(p)));
  }

  /**
   * Genera un campo vettoriale di rumore di tipo marmo.
   * @return double[dim] valore nel punto richiesto [0.0,+1.0) per ogni dimensione
   * @param p vettore posizione nel campo
   * @param dim numero di dimensioni (massimo 5)
   */
  public double[] vectorial(Vector p, byte dim) {
    double[] v=noise.vectorial(p, dim);
    for(int i=0; i<dim; i++)
      v[i]=0.5+0.5*Math.cos(p.x+turbulence*v[i]);
    return(v);
  }

}