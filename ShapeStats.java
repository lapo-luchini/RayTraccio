/**
 * Classe statica utilizzata per calcolare le statistiche di uso.
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public final class ShapeStats {
	/** Tipo di statistica: un raggio normale � stato intersecato */
	public static byte TYPE_RAY=0;
	/** Tipo di statistica: un raggio a origine fissa � stato intersecato */
	public static byte TYPE_EYERAY=1;
	/** Tipo di statistica: un raggio � stato calcolato in modo ottimizzato */
	public static byte TYPE_CACHEDRAY=2;
	/** Tipo di statistica: un raggio ha effettivamente colpito la figura */
	public static byte TYPE_HIT=3;
	/** Indica l'ultima costante di tipo statistica */
	public static byte TYPE_LAST=3;
	/** Entit� registrate per la statistica */
	private static String entities[]=new String[0];
	/** Statistiche per tutte le entit� registrate */
	private static int stats[][]=new int[TYPE_LAST+1][0];
	/** Numero di entit� effettivamente registrate */
	private static byte n=0;
/**
 * Aumenta di uno la statistica voluta.
 * @param entity indicatore dell'entit� da aggiornare
 * @param type tipo di statistica da aggiornare
 */
public static void count(byte entity, byte type) {
	stats[type][entity] ++;
}
/**
 * Legge la statistica voluta.
 * @param entity indicatore dell'entit�
 * @param type tipo di statistica
 * @return il valore dell astatistica
 */
public static int getCount(byte entity, byte type) {
	return (stats[type][entity]);
}
/**
 * Legge il numero di entit� registrate.
 * @return numero di entit� registrate
 */
public static int getEntityCount() {
	return (n);
}
/**
 * Legge il nome dell'entit� voluta.
 * @param entity indicatore dell'entit� voluta
 * @return nome dell'entit�
 */
public static String getEntityName(byte entity) {
	return(entities[entity]);
}
/**
 * Registra una nuova entit�, con nome dato.
 * @param name nome voluto dell'entit�
 * @return indicatore dell'entit�, usato da ora in poi per identificarla
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
