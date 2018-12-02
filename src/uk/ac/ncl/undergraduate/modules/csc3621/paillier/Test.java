package uk.ac.ncl.undergraduate.modules.csc3621.paillier;

import java.io.IOException;
import java.math.BigInteger;

public class Test {

    public static void main(String args[]) {
        KeyPair kp = PaillierScheme.Gen(1024);
        //System.out.println(kp.getPrivateKey().getN().toString());
        BigInteger value = new BigInteger("WORKs PLS".getBytes());
        System.out.println(value);
        BigInteger cipher = (
                PaillierScheme.Enc(kp.getPublicKey(), value)
        );
        PrivateKey pk = kp.getPrivateKey();
        System.out.println("asdsdm " + new String(PaillierScheme.Dec(pk,cipher).toByteArray()));
        int NumTimes = 100;

        long averageDec = 0;
        long averageDecOne = 0;
        long averageDecCall = 0;

        for (int i =0; i< NumTimes; i++){
            long start = System.nanoTime();
            PaillierScheme.Dec(pk,cipher);
//            BigInteger ciphers = (
//                    PaillierScheme.Enc(kp.getPublicKey(), new BigInteger("45"))
//            );
            long end = System.nanoTime();
            long diff = end-start;
            averageDec += diff;
        }

//        for (int i =0; i< NumTimes; i++){
//            long start = System.nanoTime();
//            PaillierScheme.EncFast(kp.getPublicKey(),new BigInteger("2"));
//            long end = System.nanoTime();
//            long diff = end-start;
//            averageDecOne += diff;
//        }
        for (int i =0; i< NumTimes; i++){
            long start = System.nanoTime();
            PaillierScheme.Enc(kp.getPublicKey(),new BigInteger("2"));
            long end = System.nanoTime();
            long diff = end-start;
            averageDecCall += diff;
        }

//
//        PaillierScheme.Dec(pk,cipher);
//        PaillierScheme.DecOneLine(pk,cipher);
//        PaillierScheme.DecOneLineCalls(pk,cipher);

        System.out.println("Avergae Dec: "+ averageDec/NumTimes);
        System.out.println("Avergae DecOneLIne: "+ averageDecOne/NumTimes);
        System.out.println("Avergae No CAll: "+ averageDecCall/NumTimes);

        System.out.println(PaillierScheme.Dec(kp.getPrivateKey(), cipher));

    }
}
