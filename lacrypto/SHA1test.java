package lacrypto;

/**
 * Standard and custom tests to SHA1 implementation.
 * Creation date: (05/04/2001 11:05:36)
 * @author: Lapo Luchini
 */
public class SHA1test extends SHA1 {
	public final static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	public final static java.lang.String TEST_0_STRING = "abc";
	public final static java.lang.String TEST_1_STRING = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
	public final static java.lang.String TEST_2_STRING = "1.000.000 repetitions of 'a'";
	public final static int[] TEST_0_HASH = {0xA9993E36, 0x4706816A, 0xBA3E2571, 0x7850C26C, 0x9CD0D89D};
	public final static int[] TEST_1_HASH = {0x84983E44, 0x1C3BD26E, 0xBAAE4AA1, 0xF95129E5, 0xE54670F1};
	public final static int[] TEST_2_HASH = {0x34AA973C, 0xD4C4DAA4, 0xF61EEB2B, 0xDBAD2731, 0x6534016F};
/**
 * Either prints a self-test or calculates hash of a given string.
 * Creation date: (25/10/2000 11.43.19)
 * @param args java.lang.String[]
 */
public static void main(String[] args) {
	if(args.length==0)
		selfTest();
	else {
		SHA1test sha=new SHA1test();
		sha.update(args[0].getBytes());
		System.out.println(sha.toHex(sha.digest()));
	}
}
/**
 * Self-test using the three examples defined in the standard.
 * Creation date: (25/10/2000 11.23.53)
 * @return java.lang.String
 */
public static void selfTest() {
	SHA1test sha=new SHA1test();
	byte[] hash;
	int i;
	sha.update(TEST_0_STRING.getBytes());
	hash=sha.digest();
	for(i=0; i<20; i+=4) {
		if((((hash[i]&0xFF)<<24)|((hash[i+1]&0xFF)<<16)+((hash[i+2]&0xFF)<<8)+(hash[i+3]&0xFF))!=TEST_0_HASH[i>>2])
			throw(new RuntimeException("Hash not valid."));
	}
	System.out.println(TEST_0_STRING+" => "+sha.toHex(hash));
	sha.update(TEST_1_STRING.getBytes());
	hash=sha.digest();
	for(i=0; i<20; i+=4) {
		if((((hash[i]&0xFF)<<24)|((hash[i+1]&0xFF)<<16)+((hash[i+2]&0xFF)<<8)+(hash[i+3]&0xFF))!=TEST_1_HASH[i>>2])
			throw(new RuntimeException("Hash not valid."));
	}
	System.out.println(TEST_1_STRING+" => "+sha.toHex(hash));
	byte[] s="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes();
	for(i=0; i<10000; i++)
		sha.update(s);
	hash=sha.digest();
	for(i=0; i<20; i+=4) {
		if((((hash[i]&0xFF)<<24)|((hash[i+1]&0xFF)<<16)+((hash[i+2]&0xFF)<<8)+(hash[i+3]&0xFF))!=TEST_2_HASH[i>>2])
			throw(new RuntimeException("Hash not valid."));
	}
	System.out.println(TEST_2_STRING+" => "+sha.toHex(hash));
	long tm=System.currentTimeMillis(), tmn;
	i=0;
	while((tmn=(System.currentTimeMillis()-tm))<5000)
		for(int j=0; j<100; j++, i++)
			sha.update(s);
	hash=sha.digest();
	tm=System.currentTimeMillis()-tm;
	System.out.println("All is OK ("+(100000L*i)/tmn+" bytes/s).");
}
/**
 * Convert a big number into hex form.
 * Creation date: (24/10/2000 23.42.34)
 * @return java.lang.String
 * @param v big number in byte[] (big endian)
 */
public final static String toHex(byte[] v) {
	String out="";
	for(int i=0; i<v.length; i++)
		out+=hex[(v[i]>>4)&0xF]+hex[v[i]&0xF];
	return(out);
}
}
