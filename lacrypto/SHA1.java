// LapInt number theory library Copyright (c) 2001-2002 Lapo Luchini <lapo@lapo.it>
// $Header: /usr/local/cvsroot/lapint/crypto/SHA1.java,v 1.7 2002/02/23 14:49:43 lapo Exp $

// This file is part of LapInt.
//
// LapInt is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// LapInt is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with LapInt; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package lacrypto;

/**
 * SHA-1 Message Digest class. <br>
 * Follows the <a href="http://www.itl.nist.gov/fipspubs/fip180-1.htm">FIPS PUB 180-1</a> standard
 * which elaborates a message digest from given data (legnth<2^64 bytes). <br>
 * On the same machine (Linux 2.4.8 on P3-850) I obtained the following stats: <ul>
 *   <li><b>20.32 Mb/s</b> - Adam Back C implementation, compiled with GCC 2.95</li>
 *   <li><b>9.21 Mb/s</b> - This Java implementation, compiled natively with GCC 2.95 -O2</li>
 *   <li><b>7.30 Mb/s</b> - This Java implementation, compiled with JIKES and executed with KAFFE</li>
 *   <li><b>3.01 Mb/s</b> - This Java implementation, compiled natively with GCC 2.95</li>
 * </ul>
 * Creation date: (24/10/2000 17.50.12)
 * @author: Lapo Luchini <lapo@lapo.it>
 */
public class SHA1 {

  /** Partial hash */
  protected int[] H;
  /** Internal buffer */
  protected transient int[] W;
  /** Internal buffer used bytes */
  protected transient byte bufs;
  /** Total bits digested */
  protected long totb;
  /** Wherever next update should start from scratch */
  protected boolean reset;
  /** Used to convert number to Hex strings */
  public final static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
  /** First official test */
  public final static java.lang.String TEST_0_STRING = "abc";
  /** Second official test */
  public final static java.lang.String TEST_1_STRING = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
  /** Third official test */
  public final static java.lang.String TEST_2_STRING = "1.000.000 repetitions of 'a'";
  /** First official test result */
  public final static int[] TEST_0_HASH = {0xA9993E36, 0x4706816A, 0xBA3E2571, 0x7850C26C, 0x9CD0D89D};
  /** Second official test result */
  public final static int[] TEST_1_HASH = {0x84983E44, 0x1C3BD26E, 0xBAAE4AA1, 0xF95129E5, 0xE54670F1};
  /** Third official test result */
  public final static int[] TEST_2_HASH = {0x34AA973C, 0xD4C4DAA4, 0xF61EEB2B, 0xDBAD2731, 0x6534016F};

  /**
   * Creates a SHA1 object. <br>
   * A single SHA1 object can (sequentially) elaborate many hashes.
   */
  public SHA1() {
    H=new int[5];
    W=new int[80];
    reset=true;
  }

  /**
   * Returns the digest and resets the object to calculate another digest. <br>
   * The array returned will NOT be reused by this object.
   * @return byte[20] array containing the required hash
   */
  public byte[] digest8() {
    digest_finalize();
    byte[] out=new byte[20];
    for(int i=0; i<20; i++)
      out[i]=(byte)((H[i>>2]>>(8*(3-(i&3))))&0xFF);
    return(out);
  }

  /**
   * Returns the digest and resets the object to calculate another digest. <br>
   * The array returned will NOT be reused by this object.
   * @return int[5] array containing the required hash
   */
  public int[] digest32() {
    digest_finalize();
    int[] out=new int[5];
    System.arraycopy(H, 0, out, 0, 5);
    return(out);
  }
  /**
   * Finalize the hash calculation, as defined in the standard.
   */
  private final void digest_finalize() {
    int i;
    if(bufs<64) {
      if((bufs&3)==0)
	W[bufs>>2]=0;
      W[bufs>>2]|=0x80<<(8*(3-(bufs&3)));
      bufs++;
      if(bufs>56) {
	for(i=(bufs+3)>>2; i<16; i++)
	  W[i]=0;
	update_buffers();
	bufs=0;
      }
      for(i=(bufs+3)>>2; i<14; i++)
	W[i]=0;
      totb<<=3; // bytes to bits
      W[14]=(int)(totb>>>32);
      W[15]=(int)(totb&0xFFFFFFFF);
      update_buffers();
    }
    reset=true;
  }

  /**
   * Method to self-test the class from command line.
   * Creation date: (25/10/2000 11.43.19)
   * @param args command line parameters
   */
  public static void main(String[] args) {
    if(args.length==0)
      selfTest();
    else {
      SHA1 sha=new SHA1();
      sha.update(args[0].getBytes());
      System.out.println(sha.toHex(sha.digest8()));
    }
  }

  /**
   * Self test using standard tests and write to standard output the result.
   * Creation date: (25/10/2000 11.23.53)
   */
  public static void selfTest() {
    SHA1 sha=new SHA1();
    byte[] hash;
    int i;
    sha.update(TEST_0_STRING.getBytes());
    hash=sha.digest8();
    for(i=0; i<20; i+=4) {
      if((((hash[i]&0xFF)<<24)|((hash[i+1]&0xFF)<<16)+((hash[i+2]&0xFF)<<8)+(hash[i+3]&0xFF))!=TEST_0_HASH[i>>2])
	throw(new RuntimeException("Hash not valid."));
    }
    System.out.println(TEST_0_STRING+" => "+sha.toHex(hash));
    sha.update(TEST_1_STRING.getBytes());
    hash=sha.digest8();
    for(i=0; i<20; i+=4) {
      if((((hash[i]&0xFF)<<24)|((hash[i+1]&0xFF)<<16)+((hash[i+2]&0xFF)<<8)+(hash[i+3]&0xFF))!=TEST_1_HASH[i>>2])
	throw(new RuntimeException("Hash not valid."));
    }
    System.out.println(TEST_1_STRING+" => "+sha.toHex(hash));
    byte[] s="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes();
    for(i=0; i<10000; i++)
      sha.update(s);
    hash=sha.digest8();
    for(i=0; i<20; i+=4) {
      if((((hash[i]&0xFF)<<24)|((hash[i+1]&0xFF)<<16)+((hash[i+2]&0xFF)<<8)+(hash[i+3]&0xFF))!=TEST_2_HASH[i>>2])
	throw(new RuntimeException("Hash not valid."));
    }
    System.out.println(TEST_2_STRING+" => "+sha.toHex(hash));
    long tm=System.currentTimeMillis(), tmn;
    i=0;
    while((tmn=(System.currentTimeMillis()-tm))<2000)
      for(int j=0; j<100; j++, i++)
	sha.update(s);
    hash=sha.digest8();
    tmn=System.currentTimeMillis()-tm;
    System.out.println("All is OK ("+(s.length*i*1000L)/tmn+" bytes/s).");
  }

  /**
   * Formats a number in an hex string.
   * Creation date: (24/10/2000 23.42.34)
   * @param v number to format
   * @return hex string of the number
   */
  public final static String toHex(byte[] v) {
    String out="";
    for(int i=0; i<v.length; i++)
      out=out+hex[(v[i]>>4)&0xF]+hex[v[i]&0xF];
    return(out);
  }

  /**
   * Feeds more bytes to the digest.
   * Creation date: (24/10/2000 17.54.31)
   * @param m bytes to elaborate (any length is valid)
   */
  public void update(byte[] m) {
    int i=0, j;
    if(reset) {
      H[0]=0x67452301;
      H[1]=0xEFCDAB89;
      H[2]=0x98BADCFE;
      H[3]=0x10325476;
      H[4]=0xC3D2E1F0;
      totb=0;
      bufs=0;
      reset=false;
    }
    while(i<m.length) {
      for(; (bufs<64)&&(i<m.length); i++, bufs++, totb++) {
	if((bufs&3)==0)
	  W[bufs>>2]=0;
	W[bufs>>2]|=m[i]<<(8*(3-(bufs&3)));
      }
      if(bufs==64) {
	update_buffers();
	bufs=0;
      }
    }
  }

  /**
   * Updates the hash with more ints (big-endian).
   * Creation date: (24/10/2000 17.54.31)
   * @param m an array of ints to add
   */
  public void update(int[] m) {
    byte[] vect=new byte[4];
    for(int i=0; i<m.length; i++) {
      vect[0]=(byte)((m[i]>>24)&0xFF);
      vect[1]=(byte)((m[i]>>16)&0xFF);
      vect[2]=(byte)((m[i]>> 8)&0xFF);
      vect[3]=(byte)((m[i]    )&0xFF);
      update(vect);
    }
  }

  /**
   * Updates the hash with more longs (big-endian).
   * Creation date: (24/10/2000 17.54.31)
   * @param m an array of ints to add
   */
  public void update(long[] m) {
    byte[] vect=new byte[8];
    for(int i=0; i<m.length; i++) {
      vect[0]=(byte)((m[i]>>56)&0xFF);
      vect[1]=(byte)((m[i]>>48)&0xFF);
      vect[2]=(byte)((m[i]>>40)&0xFF);
      vect[3]=(byte)((m[i]>>32)&0xFF);
      vect[4]=(byte)((m[i]>>24)&0xFF);
      vect[5]=(byte)((m[i]>>16)&0xFF);
      vect[6]=(byte)((m[i]>> 8)&0xFF);
      vect[7]=(byte)((m[i]    )&0xFF);
      update(vect);
    }
  }

  /**
   * Internally used when the buffer is ready for the digest. <br>
   * Completely unrolled for maximum performance (almost 1/3 of optimized C speed!).
   * Creation date: (24/10/2000 19.17.40)
   */
  private final void update_buffers() {
    for(int j=16; j<80; j++) {
      W[j]=W[j-3]^W[j-8]^W[j-14]^W[j-16];
      W[j]=(W[j]<<1)|(W[j]>>>31);
    }

    int A=H[0], B=H[1], C=H[2], D=H[3], E=H[4];

    E+=W[ 0]+((A<<5)|(A>>>27))+(D^(B&(C^D)))+0x5A827999; B=(B<<30)|(B>>>2);
    D+=W[ 1]+((E<<5)|(E>>>27))+(C^(A&(B^C)))+0x5A827999; A=(A<<30)|(A>>>2);
    C+=W[ 2]+((D<<5)|(D>>>27))+(B^(E&(A^B)))+0x5A827999; E=(E<<30)|(E>>>2);
    B+=W[ 3]+((C<<5)|(C>>>27))+(A^(D&(E^A)))+0x5A827999; D=(D<<30)|(D>>>2);
    A+=W[ 4]+((B<<5)|(B>>>27))+(E^(C&(D^E)))+0x5A827999; C=(C<<30)|(C>>>2);
    E+=W[ 5]+((A<<5)|(A>>>27))+(D^(B&(C^D)))+0x5A827999; B=(B<<30)|(B>>>2);
    D+=W[ 6]+((E<<5)|(E>>>27))+(C^(A&(B^C)))+0x5A827999; A=(A<<30)|(A>>>2);
    C+=W[ 7]+((D<<5)|(D>>>27))+(B^(E&(A^B)))+0x5A827999; E=(E<<30)|(E>>>2);
    B+=W[ 8]+((C<<5)|(C>>>27))+(A^(D&(E^A)))+0x5A827999; D=(D<<30)|(D>>>2);
    A+=W[ 9]+((B<<5)|(B>>>27))+(E^(C&(D^E)))+0x5A827999; C=(C<<30)|(C>>>2);
    E+=W[10]+((A<<5)|(A>>>27))+(D^(B&(C^D)))+0x5A827999; B=(B<<30)|(B>>>2);
    D+=W[11]+((E<<5)|(E>>>27))+(C^(A&(B^C)))+0x5A827999; A=(A<<30)|(A>>>2);
    C+=W[12]+((D<<5)|(D>>>27))+(B^(E&(A^B)))+0x5A827999; E=(E<<30)|(E>>>2);
    B+=W[13]+((C<<5)|(C>>>27))+(A^(D&(E^A)))+0x5A827999; D=(D<<30)|(D>>>2);
    A+=W[14]+((B<<5)|(B>>>27))+(E^(C&(D^E)))+0x5A827999; C=(C<<30)|(C>>>2);
    E+=W[15]+((A<<5)|(A>>>27))+(D^(B&(C^D)))+0x5A827999; B=(B<<30)|(B>>>2);
    D+=W[16]+((E<<5)|(E>>>27))+(C^(A&(B^C)))+0x5A827999; A=(A<<30)|(A>>>2);
    C+=W[17]+((D<<5)|(D>>>27))+(B^(E&(A^B)))+0x5A827999; E=(E<<30)|(E>>>2);
    B+=W[18]+((C<<5)|(C>>>27))+(A^(D&(E^A)))+0x5A827999; D=(D<<30)|(D>>>2);
    A+=W[19]+((B<<5)|(B>>>27))+(E^(C&(D^E)))+0x5A827999; C=(C<<30)|(C>>>2);
    E+=W[20]+((A<<5)|(A>>>27))+(B^C^D)+0x6ED9EBA1; B=(B<<30)|(B>>>2);
    D+=W[21]+((E<<5)|(E>>>27))+(A^B^C)+0x6ED9EBA1; A=(A<<30)|(A>>>2);
    C+=W[22]+((D<<5)|(D>>>27))+(E^A^B)+0x6ED9EBA1; E=(E<<30)|(E>>>2);
    B+=W[23]+((C<<5)|(C>>>27))+(D^E^A)+0x6ED9EBA1; D=(D<<30)|(D>>>2);
    A+=W[24]+((B<<5)|(B>>>27))+(C^D^E)+0x6ED9EBA1; C=(C<<30)|(C>>>2);
    E+=W[25]+((A<<5)|(A>>>27))+(B^C^D)+0x6ED9EBA1; B=(B<<30)|(B>>>2);
    D+=W[26]+((E<<5)|(E>>>27))+(A^B^C)+0x6ED9EBA1; A=(A<<30)|(A>>>2);
    C+=W[27]+((D<<5)|(D>>>27))+(E^A^B)+0x6ED9EBA1; E=(E<<30)|(E>>>2);
    B+=W[28]+((C<<5)|(C>>>27))+(D^E^A)+0x6ED9EBA1; D=(D<<30)|(D>>>2);
    A+=W[29]+((B<<5)|(B>>>27))+(C^D^E)+0x6ED9EBA1; C=(C<<30)|(C>>>2);
    E+=W[30]+((A<<5)|(A>>>27))+(B^C^D)+0x6ED9EBA1; B=(B<<30)|(B>>>2);
    D+=W[31]+((E<<5)|(E>>>27))+(A^B^C)+0x6ED9EBA1; A=(A<<30)|(A>>>2);
    C+=W[32]+((D<<5)|(D>>>27))+(E^A^B)+0x6ED9EBA1; E=(E<<30)|(E>>>2);
    B+=W[33]+((C<<5)|(C>>>27))+(D^E^A)+0x6ED9EBA1; D=(D<<30)|(D>>>2);
    A+=W[34]+((B<<5)|(B>>>27))+(C^D^E)+0x6ED9EBA1; C=(C<<30)|(C>>>2);
    E+=W[35]+((A<<5)|(A>>>27))+(B^C^D)+0x6ED9EBA1; B=(B<<30)|(B>>>2);
    D+=W[36]+((E<<5)|(E>>>27))+(A^B^C)+0x6ED9EBA1; A=(A<<30)|(A>>>2);
    C+=W[37]+((D<<5)|(D>>>27))+(E^A^B)+0x6ED9EBA1; E=(E<<30)|(E>>>2);
    B+=W[38]+((C<<5)|(C>>>27))+(D^E^A)+0x6ED9EBA1; D=(D<<30)|(D>>>2);
    A+=W[39]+((B<<5)|(B>>>27))+(C^D^E)+0x6ED9EBA1; C=(C<<30)|(C>>>2);
    E+=W[40]+((A<<5)|(A>>>27))+((B&(C|D))|(C&D))+0x8F1BBCDC; B=(B<<30)|(B>>>2);
    D+=W[41]+((E<<5)|(E>>>27))+((A&(B|C))|(B&C))+0x8F1BBCDC; A=(A<<30)|(A>>>2);
    C+=W[42]+((D<<5)|(D>>>27))+((E&(A|B))|(A&B))+0x8F1BBCDC; E=(E<<30)|(E>>>2);
    B+=W[43]+((C<<5)|(C>>>27))+((D&(E|A))|(E&A))+0x8F1BBCDC; D=(D<<30)|(D>>>2);
    A+=W[44]+((B<<5)|(B>>>27))+((C&(D|E))|(D&E))+0x8F1BBCDC; C=(C<<30)|(C>>>2);
    E+=W[45]+((A<<5)|(A>>>27))+((B&(C|D))|(C&D))+0x8F1BBCDC; B=(B<<30)|(B>>>2);
    D+=W[46]+((E<<5)|(E>>>27))+((A&(B|C))|(B&C))+0x8F1BBCDC; A=(A<<30)|(A>>>2);
    C+=W[47]+((D<<5)|(D>>>27))+((E&(A|B))|(A&B))+0x8F1BBCDC; E=(E<<30)|(E>>>2);
    B+=W[48]+((C<<5)|(C>>>27))+((D&(E|A))|(E&A))+0x8F1BBCDC; D=(D<<30)|(D>>>2);
    A+=W[49]+((B<<5)|(B>>>27))+((C&(D|E))|(D&E))+0x8F1BBCDC; C=(C<<30)|(C>>>2);
    E+=W[50]+((A<<5)|(A>>>27))+((B&(C|D))|(C&D))+0x8F1BBCDC; B=(B<<30)|(B>>>2);
    D+=W[51]+((E<<5)|(E>>>27))+((A&(B|C))|(B&C))+0x8F1BBCDC; A=(A<<30)|(A>>>2);
    C+=W[52]+((D<<5)|(D>>>27))+((E&(A|B))|(A&B))+0x8F1BBCDC; E=(E<<30)|(E>>>2);
    B+=W[53]+((C<<5)|(C>>>27))+((D&(E|A))|(E&A))+0x8F1BBCDC; D=(D<<30)|(D>>>2);
    A+=W[54]+((B<<5)|(B>>>27))+((C&(D|E))|(D&E))+0x8F1BBCDC; C=(C<<30)|(C>>>2);
    E+=W[55]+((A<<5)|(A>>>27))+((B&(C|D))|(C&D))+0x8F1BBCDC; B=(B<<30)|(B>>>2);
    D+=W[56]+((E<<5)|(E>>>27))+((A&(B|C))|(B&C))+0x8F1BBCDC; A=(A<<30)|(A>>>2);
    C+=W[57]+((D<<5)|(D>>>27))+((E&(A|B))|(A&B))+0x8F1BBCDC; E=(E<<30)|(E>>>2);
    B+=W[58]+((C<<5)|(C>>>27))+((D&(E|A))|(E&A))+0x8F1BBCDC; D=(D<<30)|(D>>>2);
    A+=W[59]+((B<<5)|(B>>>27))+((C&(D|E))|(D&E))+0x8F1BBCDC; C=(C<<30)|(C>>>2);
    E+=W[60]+((A<<5)|(A>>>27))+(B^C^D)+0xCA62C1D6; B=(B<<30)|(B>>>2);
    D+=W[61]+((E<<5)|(E>>>27))+(A^B^C)+0xCA62C1D6; A=(A<<30)|(A>>>2);
    C+=W[62]+((D<<5)|(D>>>27))+(E^A^B)+0xCA62C1D6; E=(E<<30)|(E>>>2);
    B+=W[63]+((C<<5)|(C>>>27))+(D^E^A)+0xCA62C1D6; D=(D<<30)|(D>>>2);
    A+=W[64]+((B<<5)|(B>>>27))+(C^D^E)+0xCA62C1D6; C=(C<<30)|(C>>>2);
    E+=W[65]+((A<<5)|(A>>>27))+(B^C^D)+0xCA62C1D6; B=(B<<30)|(B>>>2);
    D+=W[66]+((E<<5)|(E>>>27))+(A^B^C)+0xCA62C1D6; A=(A<<30)|(A>>>2);
    C+=W[67]+((D<<5)|(D>>>27))+(E^A^B)+0xCA62C1D6; E=(E<<30)|(E>>>2);
    B+=W[68]+((C<<5)|(C>>>27))+(D^E^A)+0xCA62C1D6; D=(D<<30)|(D>>>2);
    A+=W[69]+((B<<5)|(B>>>27))+(C^D^E)+0xCA62C1D6; C=(C<<30)|(C>>>2);
    E+=W[70]+((A<<5)|(A>>>27))+(B^C^D)+0xCA62C1D6; B=(B<<30)|(B>>>2);
    D+=W[71]+((E<<5)|(E>>>27))+(A^B^C)+0xCA62C1D6; A=(A<<30)|(A>>>2);
    C+=W[72]+((D<<5)|(D>>>27))+(E^A^B)+0xCA62C1D6; E=(E<<30)|(E>>>2);
    B+=W[73]+((C<<5)|(C>>>27))+(D^E^A)+0xCA62C1D6; D=(D<<30)|(D>>>2);
    A+=W[74]+((B<<5)|(B>>>27))+(C^D^E)+0xCA62C1D6; C=(C<<30)|(C>>>2);
    E+=W[75]+((A<<5)|(A>>>27))+(B^C^D)+0xCA62C1D6; B=(B<<30)|(B>>>2);
    D+=W[76]+((E<<5)|(E>>>27))+(A^B^C)+0xCA62C1D6; A=(A<<30)|(A>>>2);
    C+=W[77]+((D<<5)|(D>>>27))+(E^A^B)+0xCA62C1D6; E=(E<<30)|(E>>>2);
    B+=W[78]+((C<<5)|(C>>>27))+(D^E^A)+0xCA62C1D6; D=(D<<30)|(D>>>2);
    A+=W[79]+((B<<5)|(B>>>27))+(C^D^E)+0xCA62C1D6; C=(C<<30)|(C>>>2);

    H[0]+=A; H[1]+=B; H[2]+=C; H[3]+=D; H[4]+=E;
  }

}