package it.lapo.raytraccio;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class PRNG {

    private final MessageDigest sha1;

    public PRNG() {
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new InternalError("SHA-1 is always available", e);
        }
    }

    private ByteBuffer hash(long[] seed) {
        ByteBuffer bb = ByteBuffer.allocate(seed.length * 8);
        for (long l : seed)
            bb.putLong(l);
        bb.rewind();
        sha1.update(bb);
        return ByteBuffer.wrap(sha1.digest());
    }

    public int getInt(long[] seed) {
//        ByteBuffer bb = hash(seed);
//        System.out.println("H: " + Arrays.toString(bb.array()));
        int i = hash(seed).getInt();
//        System.out.println(Arrays.toString(seed) + " -> " + i);
        return i;
    }

    public int[] getInt3(long[] seed) {
        ByteBuffer bb = hash(seed);
        return new int[]{ bb.getInt(), bb.getInt(), bb.getInt() };
    }

}
