// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>

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
// along with Foobar; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

// This file is $Revision$, released by $Author$ on $Date$

/**
 * Materiale formato da vari strati di altri materiali. <br>
 * I sotto-materiali vengono passati in ordine di "profondità": il primo passato
 * è il più esterno. <br>
 * Si suppone che tutti i materiali tranne il primo abbiano una trasparenza
 * da qualche parte (alpha &lt; 1.0). <br>
 * Il coefficente di riflessione viene dal primo materiale.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
class TextureLayered extends Texture {
	/** Array di sotto-texture */
	protected Texture[] t = new Texture[2];
	/** Numero di texture contenute nell'array */
	protected int n = 0;
/**
 * Aggiunge una sotto-texture. <br>
 * Le figure sono contenute in un array la cui lunghezza è aumentata in modo dinamico
 * per ottimizzare spazio occupato e velocità di aggiunta (in caso manchi spazio
 * l'array viene aumentato del 50%+1).
 * @param a {@link Texture} da aggiungere
 */
public void add(Texture a) {
	if (n == t.length) {
		Texture old[] = t;
		t = new Texture[ (t.length * 3) / 2 + 1 ]; // dimensione ispirata da ArrayList.java
		System.arraycopy(old, 0, t, 0, n);
	}
	t[n++] = a;
}
public Color color(Vector p) {
	if(n<1)
		throw new RuntimeException("no layers defined");
	Color u=t[0].color(p), tmp;
	double frac=1.0-u.a;
	u=u.mul(u.a);
	for(int i=1; (i<n)&&(frac>1E-4); i++) {
		tmp=t[i].color(p);
		u=u.addU(tmp.mul(frac*tmp.a));
		frac*=1.0-tmp.a;
	}
	u.a=1.0-frac;
	return(u);
}
public double reflect(Vector p) {
	if(n<1)
		throw new RuntimeException("no layers defined");
	return(t[0].reflect(p));
}
}
