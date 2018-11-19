package uk.ac.ncl.undergraduate.modules.csc3621.paillier;

import java.math.BigInteger;

public class Test {

    public static void main(String args[]) {
        KeyPair kp = PaillierScheme.Gen(16);
        //System.out.println(kp.getPrivateKey().getN().toString());
        BigInteger cipher = (
                PaillierScheme.Enc(kp.getPublicKey(), new BigInteger("256"))
        );
        System.out.println(PaillierScheme.Dec(kp.getPrivateKey(), cipher));
    }
}
