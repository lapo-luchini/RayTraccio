// RayTraccio ray-tracing library Copyright (c) 2001-2004 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/RayTraccio/it/lapo/raytraccio/Color.java,v 1.1 2004/12/16 23:09:56 lapo Exp $

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

/**
 * Colore RGB con precisione <code>double</code>.<br>
 * I quattro valori (Red, Green, Blue, Alpha) variano da <code>0.0</code> a
 * <code>1.0</code>.<br>
 * I valori negativi vengono annullati.
 * 
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public final class Color {

    /** Componente Alpha */
    public double a;

    /** Componente Red */
    public double r;

    /** Componente Green */
    public double g;

    /** Componente Blue */
    public double b;

    /** Colore (0, 0, 0) nero */
    public static Color BLACK = new Color(0.0, 0.0, 0.0);

    /** Colore (1, 0, 0) rosso */
    public static Color RED = new Color(1.0, 0.0, 0.0);

    /** Colore (0, 1, 0) verde */
    public static Color GREEN = new Color(0.0, 1.0, 0.0);

    /** Colore (0, 0, 1) blu */
    public static Color BLUE = new Color(0.0, 0.0, 1.0);

    /** Colore (1, 1, 0) giallo */
    public static Color YELLOW = new Color(1.0, 1.0, 0.0);

    /** Colore (0, 1, 1) ciano */
    public static Color CYAN = new Color(0.0, 1.0, 1.0);

    /** Colore (1, 0, 1) viola */
    public static Color PURPLE = new Color(1.0, 0.0, 1.0);

    /** Colore (1, 1, 1) bianco */
    public static Color WHITE = new Color(1.0, 1.0, 1.0);

    /**
     * Crea un colore date le componenti. <br>
     * Alpha viene inizializzato a <code>1.0</code>.
     * 
     * @param r componente Red
     * @param g componente Green
     * @param b componente Blue
     */
    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0;
        this.check();
    }

    /**
     * Crea un colore date le componenti.
     * 
     * @param r componente Red
     * @param g componente Green
     * @param b componente Blue
     * @param a componente Alpha
     */
    public Color(double r, double g, double b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.check();
    }

    /**
     * Crea un colore copiandolo da un altro.
     * 
     * @param c colore da usare
     */
    public Color(Color c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
        this.a = c.a;
    }

    /**
     * Somma di colori.
     * 
     * @param c colore da usare
     * @return un nuovo oggetto
     */
    public Color add(Color c) {
        return new Color(r + c.r, g + c.g, b + c.b, a + c.a);
    }

    /**
     * Somma di colori (modifica l'oggetto origine).
     * 
     * @param c colore da usare
     * @return questo stesso oggetto
     */
    public Color addU(Color c) {
        r += c.r;
        g += c.g;
        b += c.b;
        a += c.a;
        return this;
    }

    /**
     * Valida il colore. <br>
     * Le componenti negative vengono azzerate.
     */
    public void check() {
        if (this.r < 0.0)
            this.r = 0.0;
        if (this.g < 0.0)
            this.g = 0.0;
        if (this.b < 0.0)
            this.b = 0.0;
        if (this.a < 0.0)
            this.a = 0.0;
        // le medie non funzionerebbero più...
        // if (this.a > 1.0)
        // this.a = 1.0;
    }

    /**
     * Trasforma il colore in formato 32 bit ARGB (Alpha, Red, Green, Blue).
     * 
     * @return colore in formato ARGB
     */
    public int getARGB() {
        int u = 0, r = (int) (this.r * 255), g = (int) (this.g * 255), b = (int) (this.b * 255), a = (int) (this.a * 255);
        if (a > 0)
            if (a < 255)
                u |= a;
            else
                u |= 255;
        u = u << 8;
        if (r > 0)
            if (r < 255)
                u |= r;
            else
                u |= 255;
        u = u << 8;
        if (g > 0)
            if (g < 255)
                u |= g;
            else
                u |= 255;
        u = u << 8;
        if (b > 0)
            if (b < 255)
                u |= b;
            else
                u |= 255;
        return u;
    }

    /**
     * Trasforma il colore in formato 24 bit RGB (Red, Green, Blue). <br>
     * Gli 8 bit più significativi sono fissati a uno.
     * 
     * @return colore in formato RGB
     */
    public int getRGB() {
        int u = 0x0000FF00, r = (int) (this.r * 255), g = (int) (this.g * 255), b = (int) (this.b * 255);
        if (r > 0)
            if (r < 255)
                u |= r;
            else
                u |= 255;
        u = u << 8;
        if (g > 0)
            if (g < 255)
                u |= g;
            else
                u |= 255;
        u = u << 8;
        if (b > 0)
            if (b < 255)
                u |= b;
            else
                u |= 255;
        return u;
    }

    /**
     * Moltiplicazione componente-per-costante del colore. <br>
     * Valori negativi del parametro generano un avvertimento su
     * <code>System.err</code>.
     * 
     * @param c costante da usare
     * @return un nuovo oggetto
     */
    public Color mul(double c) {
        if (c < 0.0)
            System.err.println("Color.mul: invalid parameter [" + c
                    + "]: it must be >= 0.0");
        return new Color(r * c, g * c, b * c, a * c);
    }

    /**
     * Moltiplicazione componente-per-componente di colori.
     * 
     * @param c colore da usare
     * @return un nuovo oggetto
     */
    public Color mul(Color c) {
        return new Color(r * c.r, g * c.g, b * c.b, a * c.a);
    }

    /**
     * Moltiplicazione componente-per-costante del colore (modifica l'oggetto
     * origine). Valori negativi del parametro generano un avvertimento su
     * <code>System.err</code>.
     * 
     * @param c costante da usare
     * @return questo stesso oggetto
     */
    public Color mulU(double c) {
        if (c < 0.0)
            System.err.println("Color.mulU: invalid parameter [" + c
                    + "]: it must be >= 0.0");
        r *= c;
        g *= c;
        b *= c;
        a *= c;
        return this;
    }

    /**
     * Moltiplicazione componente-per-componente di colori (modifica l'oggetto
     * origine).
     * 
     * @param c colore da usare
     * @return questo stesso oggetto
     */
    public Color mulU(Color c) {
        r *= c.r;
        g *= c.g;
        b *= c.b;
        a *= c.a;
        return this;
    }

    /**
     * Sottrazione di colori.
     * 
     * @param c colore da usare
     * @return un nuovo oggetto
     */
    public Color sub(Color c) {
        return new Color(r - c.r, g - c.g, b - c.b, a - c.a);
    }

    /**
     * Sottrazione di colori (modifica l'oggetto origine).
     * 
     * @param c colore da usare
     * @return questo stesso oggetto
     */
    public Color subU(Color c) {
        r -= c.r;
        g -= c.g;
        b -= c.b;
        a -= c.a;
        if (r < 0.0)
            r = 0.0;
        if (g < 0.0)
            g = 0.0;
        if (b < 0.0)
            b = 0.0;
        if (a < 0.0)
            a = 0.0;
        return this;
    }

    /**
     * Rappresentazione testuale dell'oggetto. <br>
     * Esempio: <code>Color[0.1,0.23,1.0,1.0]</code>
     */
    public String toString() {
        return "Color[" + r + "," + g + "," + b + "," + a + "]";
    }

}
