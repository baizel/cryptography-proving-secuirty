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
    private static final int NUMBER_BIT_LENGTH = 1024;

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
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        return new KeyPair(new PublicKey(N, N.multiply(N)), new PrivateKey(N, N.multiply(N), phiN));
    }

    /**
     * The encryption algorithm
     *
     * @param pk the public key
     * @param m  the plaintext to be encrypted
     * @return the ciphertext of m
     */

    public static BigInteger Enc(PublicKey pk, BigInteger m) {
        BigInteger nm = pk.getN().add(BigInteger.ONE);
        Random r = new SecureRandom();
        BigInteger uniformRandom = new BigInteger(NUMBER_BIT_LENGTH, new SecureRandom());

    }

    /**
     * The decryption algorithm
     *
     * @param sk the private key
     * @param c  the ciphertext to be decrypted
     * @return the plaintext decrypted from c
     */
    public static BigInteger Dec(PrivateKey sk, BigInteger c) {

    }

    /**
     * The homomorphic addition algorithm
     *
     * @param pk the public key
     * @param c1 the first ciphertext
     * @param c2 the second ciphertext
     * @return the ciphertext contains the addition result
     */
    public static BigInteger Add(PublicKey pk, BigInteger c1, BigInteger c2) {

    }

    /**
     * The homomorphic multiply with plaintext algorithm
     *
     * @param pk the public key
     * @param s  a plaintext integer
     * @param c  the ciphertext
     * @return the ciphertext contains the multiplication result
     */

    public static BigInteger Multiply(PublicKey pk, BigInteger s, BigInteger c) {

    }


}
