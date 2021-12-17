package com.mnhmilu.poc.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;


public class APIKeyGeneration {
    public static String generate(final int keyLen) throws NoSuchAlgorithmException {

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keyLen);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return DatatypeConverter.printHexBinary(encoded).toLowerCase();
    }


    public static void main(String[] args) {
        String key = null;
        System.out.println("==================Key length is 128 and string size is 32 ");
        try {
            key = APIKeyGeneration.generate(128);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception caught");
            e.printStackTrace();
        }
        System.out.println(key);

        System.out.println("==================Key length is 256 and string size is 64");


        try {
            key = APIKeyGeneration.generate(256);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception caught");
            e.printStackTrace();
        }
        System.out.println(key);
        System.out.println("==================");


    }
}