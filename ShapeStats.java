// RayTraccio ray-tracing library Copyright (c) 2001 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/raytraccio/ShapeStats.java,v 1.7 2001/04/27 08:50:16 lapo Exp $

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
 * Classe statica utilizzata per calcolare le statistiche di uso.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public final class ShapeStats {

  /** Tipo di statistica: un raggio normale &egrave; stato intersecato */
  public static byte TYPE_RAY=0;
  /** Tipo di statistica: un raggio a origine fissa &egrave; stato intersecato */
  public static byte TYPE_EYERAY=1;
  /** Tipo di statistica: un raggio è stato calcolato in modo ottimizzato */
  public static byte TYPE_CACHEDRAY=2;
  /** Tipo di statistica: un raggio ha effettivamente colpito la figura */
  public static byte TYPE_HIT=3;
  /** Indica l'ultima costante di tipo statistica */
  public static byte TYPE_LAST=3;
  /** Entit&agrave; registrate per la statistica */
  private static String entities[]=new String[0];
  /** Statistiche per tutte le entit&agrave; registrate */
  private static int stats[][]=new int[TYPE_LAST+1][0];
  /** Numero di entit&agrave; effettivamente registrate */
  private static byte n=0;

  /**
   * Aumenta di uno la statistica voluta.
   * @param entity indicatore dell'entità da aggiornare
   * @param type tipo di statistica da aggiornare
   */
  public static void count(byte entity, byte type) {
    stats[type][entity] ++;
  }

  /**
   * Legge la statistica voluta.
   * @param entity indicatore dell'entità
   * @param type tipo di statistica
   * @return il valore dell astatistica
   */
  public static int getCount(byte entity, byte type) {
    return (stats[type][entity]);
  }

  /**
   * Legge il numero di entità registrate.
   * @return numero di entità registrate
   */
  public static int getEntityCount() {
    return (n);
  }

  /**
   * Legge il nome dell'entità voluta.
   * @param entity indicatore dell'entità voluta
   * @return nome dell'entità
   */
  public static String getEntityName(byte entity) {
    return(entities[entity]);
  }

  /**
   * Registra una nuova entità, con nome dato.
   * @param name nome voluto dell'entità
   * @return indicatore dell'entità, usato da ora in poi per identificarla
   */
  synchronized public static byte register(String name) {
    if (n == entities.length) {
      int newlen = (entities.length * 3) / 2 + 1; // dimensione ispirata da ArrayList.java
      String oldE[] = entities;
      entities = new String[newlen];
      System.arraycopy(oldE, 0, entities, 0, n);
      for (int i = 0; i <= TYPE_LAST; i++) {
	int old[] = stats[i];
	stats[i] = new int[newlen];
	System.arraycopy(old, 0, stats[i], 0, n);
      }
    }
    entities[n]=name;
    return (n++);
  }

}