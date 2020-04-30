package hu.adatb.util;

import java.math.BigInteger;
import java.security.*;

public class Encoder {
    public static String GetMD5(String input){
        String hash = "";

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger bi = new BigInteger(1, messageDigest);

            hash = bi.toString();

            while(hash.length() < 32){
                hash = "0" + hash;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return hash;

    }
}
