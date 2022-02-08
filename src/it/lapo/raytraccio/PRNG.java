package it.lapo.raytraccio;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * PRNG based on SplitMix64 by Sebastiano Vigna (vigna@acm.org)
 * https://prng.di.unimi.it/splitmix64.c
 */
public final class PRNG {

    private PRNG() {} // static class

    private static long mix(long z) {
        z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
        z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
        return z ^ (z >>> 31);
    }

    private static long mixSeed(long[] seed) {
        long x = 0;
        for (long l : seed) {
            x ^= l;
            x = mix(x);
        }
        return x;
    }

    public static int getInt(long[] seed) {
        long x = mixSeed(seed);
        return (int) x;
    }

    public static void getInt3(long[] seed, int[] val) {
        long x = mixSeed(seed);
        val[0] = (int) x;
        x += 0x9e3779b97f4a7c15L;
        val[1] = (int) mix(x);
        x += 0x9e3779b97f4a7c15L;
        val[2] = (int) mix(x);
    }

}
