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

/**
 * Modifica un altro pattern perturbandolo. <br>
 * La turbolenza da applicare &egrave; data da un'altro pattern, spesso
 * si usa un {@link PatternPerlin} per produrre effetti molto realistici.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class PatternTurbulence extends Pattern {
	/** {@link Pattern} da perturbare */
	protected Pattern pat;
	/** {@link Pattern} usato per perturbare */
	protected Pattern n;
	/** Turbolenza della perturbazione nella direzione X */
	protected double tx;
	/** Turbolenza della perturbazione nella direzione Y */
	protected double ty;
	/** Turbolenza della perturbazione nella direzione Z */
	protected double tz;
/**
 * Applica una turbulenza a un altro pattern.
 * @param pattern pattern da usare come base
 * @param noise rumore da usare come turbulenza
 * @param turbulence quanto il rumore perturba il pattern (isotropo)
 */
public PatternTurbulence(Pattern pattern, Pattern noise, double turbulence) {
	pat = pattern;
	n = noise;
	tx = ty = tz = turbulence;
}
/**
 * Applica una turbulenza a un altro pattern.
 * @param pattern pattern da usare come base
 * @param noise rumore da usare come turbulenza
 * @param turbulence quanto il rumore perturba il pattern (anisotropo)
 */
public PatternTurbulence(Pattern pattern, Pattern noise, Vector turbulence) {
	pat = pattern;
	n = noise;
	tx = turbulence.x;
	ty = turbulence.y;
	tz = turbulence.z;
}
public double scalar(Vector p) {
	double vn[]=n.vectorial(p, (byte)3);
	return(pat.scalar(new Vector(p.x+tx*vn[0], p.y+ty*vn[1], p.z+tz*vn[2])));
}
public double[] vectorial(Vector p, byte dim) {
	double vn[]=n.vectorial(p, (byte)3);
	return(pat.vectorial(new Vector(p.x+tx*vn[0], p.y+ty*vn[1], p.z+tz*vn[2]), dim));
}
}
