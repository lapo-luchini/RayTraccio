/**
 * Campo di rumore tridimensionale a spettro |1/f|. <br>
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class PatternPerlinAbs extends Pattern {
	/** Funzione di hash da usata come PRNG dipendente dalla posizione */
	private lacrypto.SHA1 sha=new lacrypto.SHA1();
	/** Seme dello spazio casuale */
	private long seed;
	/** Numero di ottave da calcolare */
	protected int octaves;
	/** Persistenza (ampiezza di un'ottava rispetto alla precedente) */
	protected double persistence;
	/** Valore usato internamente per scalare il valore di uscita in [0.0,1.0) */
	protected double amplitude;
/**
 * Definisce un pattern di tipo rumore di Perlin.
 * @param octaves numero di ottave da calcolare
 * @param persistence ampiezza dell'ottava successiva rispetto alla precedente
 * @param seed seme del generatore pseudocasuale
 */
public PatternPerlinAbs(byte octaves, double persistence, long seed) {
	this.octaves=octaves;
	this.persistence=persistence;
	this.seed=seed;
	if(persistence==1.0)
		amplitude=1.0/octaves;
	else {
		double po=persistence;
		while(--octaves>0)
			po*=persistence;
		amplitude=(1-persistence)/(1-po);
	}
}
/**
 * Genera un campo vettoriale di rumore.
 * @return double[dim] valore del rumore [0.0,+1.0)
 * @param p vettore posizione nel campo di rumore
 */
public double scalar(Vector p) {
	// funzione di hash SHA1, segue lo standard FIPS PUB 180-1
	lacrypto.SHA1 sha=new lacrypto.SHA1();
	// vettore a cui applicare SHA1: inizializzato con le 3 long della posizione e la long di seed
	long[] vect=new long[4];
	// vettore di output di SHA1: 20 byte
	int[] out;
	// 1.0/2147483648 trasforma un int in un reale in [-1.0,+1.0)
	double amp=0.0000000004656612873077392578125000000000;
	double x=p.x, y=p.y, z=p.z;
	double noises[]=new double[8];
	double noise=0.0;
	long lx, ly, lz;
	double fx, fy, fz;
	double intr, ints;
	vect[3]=seed;
	for(int o=0; o<octaves; o++) {
		// prende parte intera e frazionaria delle coordinate
		lx=(long)Math.floor(x); fx=x-lx;
		ly=(long)Math.floor(y); fy=y-ly;
		lz=(long)Math.floor(z); fz=z-lz;
		// prende gli 8 punti ai vertici del cubo unitario
		vect[0]=lx;   vect[1]=ly;   vect[2]=lz;   sha.update(vect); out=sha.digestInt(); noises[0]=out[0]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); noises[1]=out[0]*amp;
		vect[0]=lx;   vect[1]=ly+1;               sha.update(vect); out=sha.digestInt(); noises[2]=out[0]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); noises[3]=out[0]*amp;
		vect[0]=lx;   vect[1]=ly;   vect[2]=lz+1; sha.update(vect); out=sha.digestInt(); noises[4]=out[0]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); noises[5]=out[0]*amp;
		vect[0]=lx;   vect[1]=ly+1;               sha.update(vect); out=sha.digestInt(); noises[6]=out[0]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); noises[7]=out[0]*amp;
		// interpola sull'asse X
		intr=0.5*(1.0-Math.cos(fx*Math.PI)); ints=1.0-intr;
		noises[0]=noises[0]*ints+noises[1]*intr;
		noises[2]=noises[2]*ints+noises[3]*intr;
		noises[4]=noises[4]*ints+noises[5]*intr;
		noises[6]=noises[6]*ints+noises[7]*intr;
		// interpola sull'asse Y
		intr=0.5*(1.0-Math.cos(fy*Math.PI)); ints=1.0-intr;
		noises[0]=noises[0]*ints+noises[2]*intr;
		noises[4]=noises[4]*ints+noises[6]*intr;
		// interpola sull'asse Z
		intr=0.5*(1.0-Math.cos(fz*Math.PI)); ints=1.0-intr;
		noises[0]=noises[0]*ints+noises[4]*intr;
		// aggiunge l'ottava al rumore
		if(noises[0]>0.0)
			noise+=noises[0];
		else
			noise-=noises[0];
		// prepara la prossima ottava
		x*=2; y*=2; z*=2;
		amp*=persistence;
	}
	return(noise*amplitude);
}
/**
 * Rappresentazione testuale dell'oggetto. <br>
 * Esempio: <code>NoisePerlin[10,0.5,562746835683345476]</code> <br>
 */
public String toString() {
	return ("NoisePerlin[" + octaves + "," + persistence + "," + seed + "]");
}
/**
 * Genera un campo vettoriale di rumore.
 * @return double[dim] valore del rumore [0.0,+1.0)
 * @param p vettore posizione nel campo di rumore
 * @param dim numero di dimensioni (massimo 5)
 */
public double[] vectorial(Vector p, byte dim) {
	// funzione di hash SHA1, segue lo standard FIPS PUB 180-1
	lacrypto.SHA1 sha=new lacrypto.SHA1();
	// vettore a cui applicare SHA1: inizializzato con le 3 long della posizione e la long di seed
	long[] vect=new long[4];
	// vettore di output di SHA1: 20 byte
	int[] out;
	// 1.0/2147483648 trasforma un int in un reale in [-1.0,+1.0)
	double amp=0.0000000004656612873077392578125000000000;
	double x=p.x, y=p.y, z=p.z;
	double noises[][]=new double[dim][8];
	double noise[]=new double[dim];
	long lx, ly, lz;
	double fx, fy, fz;
	double intr, ints;
	int dims;
	vect[3]=seed;
	for(int o=0; o<octaves; o++) {
		// prende parte intera e frazionaria delle coordinate
		lx=(long)Math.floor(x); fx=x-lx;
		ly=(long)Math.floor(y); fy=y-ly;
		lz=(long)Math.floor(z); fz=z-lz;
		// prende gli 8 punti ai vertici del cubo unitario
		vect[0]=lx;   vect[1]=ly;   vect[2]=lz;   sha.update(vect); out=sha.digestInt();
		for(dims=0; dims<dim; dims++)
			noises[dims][0]=out[dims]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); 
		for(dims=0; dims<dim; dims++)
			noises[dims][1]=out[dims]*amp;
		vect[0]=lx;   vect[1]=ly+1;               sha.update(vect); out=sha.digestInt();
		for(dims=0; dims<dim; dims++)
			noises[dims][2]=out[dims]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); 
		for(dims=0; dims<dim; dims++)
			noises[dims][3]=out[dims]*amp;
		vect[0]=lx;   vect[1]=ly;   vect[2]=lz+1; sha.update(vect); out=sha.digestInt();
		for(dims=0; dims<dim; dims++)
			noises[dims][4]=out[dims]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt();
		for(dims=0; dims<dim; dims++)
			noises[dims][5]=out[dims]*amp;
		vect[0]=lx;   vect[1]=ly+1;               sha.update(vect); out=sha.digestInt();
		for(dims=0; dims<dim; dims++)
			noises[dims][6]=out[dims]*amp;
		vect[0]=lx+1;                             sha.update(vect); out=sha.digestInt(); 
		for(dims=0; dims<dim; dims++)
			noises[dims][7]=out[dims]*amp;
		for(dims=0; dims<dim; dims++) {
			// interpola sull'asse X
			intr=0.5*(1.0-Math.cos(fx*Math.PI)); ints=1.0-intr;
			noises[dims][0]=noises[dims][0]*ints+noises[dims][1]*intr;
			noises[dims][2]=noises[dims][2]*ints+noises[dims][3]*intr;
			noises[dims][4]=noises[dims][4]*ints+noises[dims][5]*intr;
			noises[dims][6]=noises[dims][6]*ints+noises[dims][7]*intr;
			// interpola sull'asse Y
			intr=0.5*(1.0-Math.cos(fy*Math.PI)); ints=1.0-intr;
			noises[dims][0]=noises[dims][0]*ints+noises[dims][2]*intr;
			noises[dims][4]=noises[dims][4]*ints+noises[dims][6]*intr;
			// interpola sull'asse Z
			intr=0.5*(1.0-Math.cos(fz*Math.PI)); ints=1.0-intr;
			noises[dims][0]=noises[dims][0]*ints+noises[dims][4]*intr;
			// aggiunge l'ottava al rumore
			if(noises[dims][0]>0.0)
				noise[dims]+=noises[dims][0];
			else
				noise[dims]-=noises[dims][0];
		}
		// prepara la prossima ottava
		x*=2; y*=2; z*=2;
		amp*=persistence;
	}
	for(dims=0; dims<dim; dims++)
		noise[dims]*=amplitude;
	return(noise);
}
}
