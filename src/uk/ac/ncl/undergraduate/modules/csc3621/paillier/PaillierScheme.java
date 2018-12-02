package uk.ac.ncl.undergraduate.modules.csc3621.paillier;

import java.math.BigInteger;
import java.security.SecureRandom;

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

        BigInteger pMinusOne = p.subtract(BigInteger.ONE);
        BigInteger qMinusOne = q.subtract(BigInteger.ONE);
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
        //Assume all inputs are valid
        BigInteger N = pk.getN();
        BigInteger NSqr = pk.getNSqr();
        BigInteger r = getRandomR(N.bitLength() - 1, N);

        // (1 + N)^m
        BigInteger a = (N.add(BigInteger.ONE)).modPow(m, NSqr);
        // r^N mod N^2
        BigInteger b = r.modPow(N, NSqr);

        return a.multiply(b).mod(NSqr);
    }

    /**
     * Function to get r which is in group Z*N
     * @param length bit length of r
     * @param N
     * @return Bigintger R as in the group Z*N
     */
    public static BigInteger getRandomR(int length, BigInteger N){
        BigInteger r =new BigInteger(length, new SecureRandom());
        while (!N.gcd(r).equals(BigInteger.ONE)){
            r = r.add(BigInteger.ONE);
        }

        return r;
    }
    /**
     * The decryption algorithm
     *
     * @param sk the private key
     * @param c  the ciphertext to be decrypted
     * @return the plaintext decrypted from c
     */
    public static BigInteger Dec(PrivateKey sk, BigInteger c) {
        //  Return statement is below code just in one line to be faster
        //  a = c.modPow(phiN, NSqr);
        //  b = (a.subtract(BigInteger.ONE)).divide(N);
        //  ma = b.mod(N);
        //  mb = phiN.modInverse(N);
        //  return ma.multiply(mb).mod(N);
        BigInteger N = sk.getN();
        BigInteger phiN = sk.getPhiN();
        return ((c.modPow(phiN, sk.getNSqr()).subtract(BigInteger.ONE)).divide(N)).multiply(phiN.modInverse(N)).mod(N);
    }

    /**
     * The homomorphic addition algorithm
     * g^2 0
     *
     * @param pk the public key
     * @param c1 the first ciphertext
     * @param c2 the second ciphertext
     * @return the ciphertext contains the addition result
     */
    public static BigInteger Add(PublicKey pk, BigInteger c1, BigInteger c2) {
        return c1.multiply(c2).mod(pk.getNSqr());
    }

    /**
     * The homomorphic multiply with plaintext algorithm
     * c₂
     *
     * @param pk the public key
     * @param s  a plaintext integer
     * @param c  the ciphertext
     * @return the ciphertext contains the multiplication result
     */

    public static BigInteger Multiply(PublicKey pk, BigInteger s, BigInteger c) {
        return c.modPow(s, pk.getNSqr());

    }


}
