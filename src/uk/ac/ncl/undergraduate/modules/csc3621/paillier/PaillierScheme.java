package uk.ac.ncl.undergraduate.modules.csc3621.paillier;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This class implements the algorithms in the Paillier scheme.
 *
 * @author Changyu Dong
 */

public class PaillierScheme {
    /**
     * The key generation algorithm.
     *
     * @param n determines the bit length of prime numbers p and q, i.e |p| = |q| = n.
     * @return a valid public private key pair in Paillier scheme.
     */
    public static KeyPair Gen(int n) {
        BigInteger p = BigInteger.probablePrime(n, new SecureRandom());
        BigInteger q = BigInteger.probablePrime(n, new SecureRandom());

        BigInteger N = p.multiply(q);
        BigInteger NSqr = N.pow(2);
//        // Expand (p-1)(q-1) -> N - p - q + 1. Note: N -> p*q
//        BigInteger phiN = N.subtract(p).subtract(q).add(BigInteger.ONE);
        BigInteger pMinusOne =  p.subtract(BigInteger.ONE);
        BigInteger qMinusOne =  q.subtract(BigInteger.ONE);
        BigInteger phiN = pMinusOne.multiply(qMinusOne);

        return new KeyPair(new PublicKey(N, NSqr), new PrivateKey(N, NSqr, phiN));
    }

    /**
     * The encryption algorithm
     *
     * @param pk the public key
     * @param m  the plaintext to be encrypted
     * @return the ciphertext of m
     */

    public static BigInteger Enc(PublicKey pk, BigInteger m) {
        BigInteger N = pk.getN();
        BigInteger NSqr = pk.getNSqr();

        BigInteger r = BigInteger.probablePrime(
                // -1 so that random number will always be less than N
                N.bitLength() - 1,
                new SecureRandom()
        );

        // (1 + N)^m mod N^2
        BigInteger a = (N.add(BigInteger.ONE)).modPow(m, NSqr);
        // r^m mod N^2
        BigInteger b = r.modPow(N, NSqr);

        return a.multiply(b);
    }

    /**
     * The decryption algorithm
     *
     * @param sk the private key
     * @param c  the ciphertext to be decrypted
     * @return the plaintext decrypted from c
     */
    public static BigInteger Dec(PrivateKey sk, BigInteger c) {
        BigInteger N = sk.getN();
        BigInteger NSqr = sk.getNSqr();
        BigInteger phiN = sk.getPhiN();

        BigInteger a = c.modPow(phiN, NSqr);
        BigInteger b = (a.subtract(BigInteger.ONE)).divide(N);
        BigInteger ma = b.modPow(BigInteger.ONE, N);
        BigInteger mb = phiN.modInverse(N);
        return ma.multiply(mb);
    }

    /**
     * The homomorphic addition algorithm
     * g^2 0
     * @param pk the public key
     * @param c1 the first ciphertext
     * @param c2 the second ciphertext
     * @return the ciphertext contains the addition result
     */
    public static BigInteger Add(PublicKey pk, BigInteger c1, BigInteger c2) {
        return c1.multiply(c2).modPow(BigInteger.ONE,pk.getNSqr());
    }

    /**
     * The homomorphic multiply with plaintext algorithm
     * c₂
     * @param pk the public key
     * @param s  a plaintext integer
     * @param c  the ciphertext
     * @return the ciphertext contains the multiplication result
     */

    public static BigInteger Multiply(PublicKey pk, BigInteger s, BigInteger c) {
        return c.modPow(s,pk.getNSqr());

    }


}
