
package com.mnhmilu.poc.security;

import org.apache.commons.codec.binary.Base64;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;


public class SignatureGeneration {
    public static void main(String args[]) throws Exception {

        System.out.println("Step:1 Creating the signature------------------------------------>");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the privatekey from the key pair
        PrivateKey privKey = pair.getPrivate();

        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withDSA");

        //Initializing the signature
        sign.initSign(privKey);
        byte[] bytes = "Hello how are you nahid".getBytes();

        //Adding data to the signature
        sign.update(bytes);

        //Calculating the signature
        byte[] signature = sign.sign();
        System.out.println("Signature is " + signature.toString() + " will be attach with api attributes");

        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        String encodedPrivateKey = Base64.encodeBase64String(publicKey);
        String encodedPublicKey = Base64.encodeBase64String(privateKey);

        System.out.println("public key is " + encodedPublicKey + "\n private key is " + encodedPrivateKey);

        System.out.println("Step:2 Verifying the signature------------------------------------>");

        //Initializing the signature
        sign.initVerify(pair.getPublic());
        sign.update(bytes);
        boolean bool = sign.verify(signature);
        System.out.println("Save digital signature and the public key to a file---------------------->");
        Files.write(Paths.get("signature.txt"), Base64.encodeBase64String(signature).getBytes());
        Files.write(Paths.get("publickey.txt"), Base64.encodeBase64String(publicKey).getBytes());

        if (bool) {
            System.out.println("Signature verified");
        } else {
            System.out.println("Signature failed");
        }
    }
}


