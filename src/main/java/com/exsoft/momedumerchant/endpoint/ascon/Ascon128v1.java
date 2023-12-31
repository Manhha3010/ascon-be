package com.exsoft.momedumerchant.endpoint.ascon;

import java.nio.ByteBuffer;
import java.util.Arrays;
public class Ascon128v1 {
    // Defines
    public static final int CRYPTO_KEYBYTES = 16;
    public static final int CRYPTO_NSECBYTES = 0;
    public static final int CRYPTO_NPUBBYTES = 16;
    public static final int CRYPTO_ABYTES = 16;
    public static final int CRYPTO_NOOVERLAP = 1;

    public static final boolean PRINTSTATE = false;
    public static final boolean PRINTWORDS = false;

    static void print(String name, byte var[], long len) {
        int i;
        System.out.print(name);
        System.out.print("[");
        System.out.print(len);
        System.out.print("]=");
        for (i = 0; i < len; ++i) {
            System.out.printf("%02x", var[i]);
        }
    }

    private static long ROTR(long x, int n) {
        return (x >>> n) | (x << (64 - n));
    }

    private static long load64(byte S[], int offset) {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            x |= ((long) (S[i + offset] & 0xFF) << (8 * i));
        }
        return x;
    }

    private static void store64(byte S[], int offset, long x) {
        for (int i = 0; i < 8; i++) {
            S[i + offset] = (byte) (x & 0xFF);
            x >>= 8;
        }
    }

    private static void permutation(byte S[], int rounds) {
        long x0 = 0, x1 = 0, x2 = 0, x3 = 0, x4 = 0;
        long t0, t1, t2, t3, t4;

        x0 = load64(S, 0);
        x1 = load64(S, 8);
        x2 = load64(S, 16);
        x3 = load64(S, 24);
        x4 = load64(S, 32);

        for (int i = 0; i < rounds; ++i) {
            // addition of round constant
            x2 ^= (((long) (0xf) - i) << 4) | i;
            // substitution layer
            x0 ^= x4;
            x4 ^= x3;
            x2 ^= x1;
            t0 = x0;
            t1 = x1;
            t2 = x2;
            t3 = x3;
            t4 = x4;
            t0 = ~t0;
            t1 = ~t1;
            t2 = ~t2;
            t3 = ~t3;
            t4 = ~t4;
            t0 &= x1;
            t1 &= x2;
            t2 &= x3;
            t3 &= x4;
            t4 &= x0;
            x0 ^= t1;
            x1 ^= t2;
            x2 ^= t3;
            x3 ^= t4;
            x4 ^= t0;
            x1 ^= x0;
            x0 ^= x4;
            x3 ^= x2;
            x2 = ~x2;
            // linear diffusion layer
            x0 ^= ROTR(x0, 19) ^ ROTR(x0, 28);
            x1 ^= ROTR(x1, 61) ^ ROTR(x1, 39);
            x2 ^= ROTR(x2, 1) ^ ROTR(x2, 6);
            x3 ^= ROTR(x3, 10) ^ ROTR(x3, 17);
            x4 ^= ROTR(x4, 7) ^ ROTR(x4, 41);
        }
        store64(S, 0, x0);
        store64(S, 8, x1);
        store64(S, 16, x2);
        store64(S, 24, x3);
        store64(S, 32, x4);
    }

    public static int crypto_aead_encrypt(byte c[], int clen, byte m[], int mlen, byte ad[], int adlen,
                                          byte nsec[], byte npub[], byte k[]) {

        int klen = CRYPTO_KEYBYTES;
        int size = 320 / 8;
        int capacity = 2 * klen;
        int rate = size - capacity;
        int a = 12;
        int b = (klen == 16) ? 6 : 8;
        int s = adlen / rate + 1;
        int t = mlen / rate + 1;
        int l = mlen % rate;

        byte[] S = new byte[size];
        byte[] A = new byte[s * rate];
        byte[] M = new byte[t * rate];

        // pad associated data
        for (int i = 0; i < adlen; ++i)
            A[i] = ad[i];
        A[adlen] = (byte) 0x80;
        for (int i = adlen + 1; i < s * rate; ++i)
            A[i] = 0;
        // pad plaintext
        for (int i = 0; i < mlen; ++i)
            M[i] = m[i];
        M[mlen] = (byte) 0x80;
        for (int i = mlen + 1; i < t * rate; ++i)
            M[i] = 0;

        // initialization
        S[0] = (byte) (klen * 8);
        S[1] = (byte) a;
        S[2] = (byte) b;
        for (int i = 3; i < rate; ++i)
            S[i] = 0;
        for (int i = 0; i < klen; ++i)
            S[rate + i] = k[i];
        for (int i = 0; i < klen; ++i)
            S[rate + klen + i] = npub[i];
        permutation(S, a);
        for (int i = 0; i < klen; ++i)
            S[rate + klen + i] ^= k[i];

        // process associated data
        if (adlen != 0) {
            for (int i = 0; i < s; ++i) {
                for (int j = 0; j < rate; ++j)
                    S[j] ^= A[i * rate + j];
                permutation(S, b);
            }
        }
        S[size - 1] ^= 1;

        // process plaintext
        for (int i = 0; i < t - 1; ++i) {
            for (int j = 0; j < rate; ++j) {
                S[j] ^= M[i * rate + j];
                c[i * rate + j] = S[j];
            }
            permutation(S, b);
        }
        for (int j = 0; j < rate; ++j)
            S[j] ^= M[(t - 1) * rate + j];
        for (int j = 0; j < l; ++j)
            c[(t - 1) * rate + j] = S[j];

        // finalization
        for (int i = 0; i < klen; ++i)
            S[rate + i] ^= k[i];
        permutation(S, a);
        for (int i = 0; i < klen; ++i)
            S[rate + klen + i] ^= k[i];

        // return tag
        for (int i = 0; i < klen; ++i)
            c[mlen + i] = S[rate + klen + i];
        clen = mlen + klen;

        return clen;
    }

    public static int crypto_aead_decrypt(byte m[], int mlen, byte nsec[], byte c[], int clen, byte ad[],
                                          int adlen, byte npub[], byte k[]) {

        mlen = 0;
        if (clen < CRYPTO_KEYBYTES)
            return -1;

        int klen = CRYPTO_KEYBYTES;
        int size = 320 / 8;
        int capacity = 2 * klen;
        int rate = size - capacity;
        int a = 12;
        int b = (klen == 16) ? 6 : 8;
        int s = adlen / rate + 1;
        int t = (clen - klen) / rate + 1;
        int l = (clen - klen) % rate;

        byte[] S = new byte[size];
        byte[] A = new byte[s * rate];
        byte[] M = new byte[t * rate];

        // pad associated data
        for (int i = 0; i < adlen; ++i)
            A[i] = ad[i];
        A[adlen] = (byte) 0x80;
        for (int i = adlen + 1; i < s * rate; ++i)
            A[i] = 0;

        // initialization
        S[0] = (byte) (klen * 8);
        S[1] = (byte) a;
        S[2] = (byte) b;
        for (int i = 3; i < rate; ++i)
            S[i] = 0;
        for (int i = 0; i < klen; ++i)
            S[rate + i] = k[i];
        for (int i = 0; i < klen; ++i)
            S[rate + klen + i] = npub[i];
        permutation(S, a);
        for (int i = 0; i < klen; ++i)
            S[rate + klen + i] ^= k[i];

        // process associated data
        if (adlen != 0) {
            for (int i = 0; i < s; ++i) {
                for (int j = 0; j < rate; ++j)
                    S[j] ^= A[i * rate + j];
                permutation(S, b);
            }
        }
        S[size - 1] ^= 1;

        // process plaintext
        for (int i = 0; i < t - 1; ++i) {
            for (int j = 0; j < rate; ++j) {
                M[i * rate + j] = (byte) (S[j] ^ c[i * rate + j]);
                S[j] = c[i * rate + j];
            }
            permutation(S, b);
        }
        for (int j = 0; j < l; ++j)
            M[(t - 1) * rate + j] = (byte) (S[j] ^ c[(t - 1) * rate + j]);
        for (int j = 0; j < l; ++j)
            S[j] = c[(t - 1) * rate + j];
        S[l] ^= 0x80;

        // finalization
        for (int i = 0; i < klen; ++i)
            S[rate + i] ^= k[i];
        permutation(S, a);
        for (int i = 0; i < klen; ++i)
            S[rate + klen + i] ^= k[i];

        // return -1 if verification fails
        for (int i = 0; i < klen; ++i)
            if (c[clen - klen + i] != S[rate + klen + i])
                return -1;

        // return plaintext
        mlen = clen - klen;
        for (int i = 0; i < mlen; ++i)
            m[i] = M[i];

        return mlen;
    }
}
