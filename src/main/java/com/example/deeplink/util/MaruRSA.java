package com.example.deeplink.util;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

public class MaruRSA {

    static private final Base64.Encoder encoder = Base64.getEncoder();
    static private final Base64.Decoder decoder = Base64.getDecoder();

    public MaruRSA() throws Exception {
        KeyPair keyPair = getKeyPair();

        this.generatePublicKeyAndPrivateKey(keyPair);

        byte[] data = "test".getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(this.getPrivateKey("./keys/deep-link/private.key"));
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        String abc = new BASE64Encoder().encode(signatureBytes);
        System.out.println(abc);
        System.out.println("Signature:" + abc);

        sig.initVerify(this.getPublicKey("./keys/deep-link/public.pub"));
        sig.update(data);

        System.out.println(sig.verify(signatureBytes));


    }
    private KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        return kpg.genKeyPair();
    }

    private void generatePublicKeyAndPrivateKey(KeyPair keypair)
            throws IOException {
        Writer writePrivate = new FileWriter("keys/deep-link/" + "private.key");

        writePrivate.write("-----BEGIN RSA PRIVATE KEY-----\n");
        writeBase64(writePrivate, keypair.getPrivate());
        writePrivate.write("-----END RSA PRIVATE KEY-----");
        writePrivate.close();

        Writer writePublic = new FileWriter("keys/deep-link/" + "public.pub");
        writePublic.write("-----BEGIN RSA PUBLIC KEY-----\n");
        writeBase64(writePublic, keypair.getPublic());
        writePublic.write("-----END RSA PUBLIC KEY-----");
        writePublic.close();
    }

    static private void writeBase64(Writer out, Key key)
            throws java.io.IOException {
        byte[] buf = key.getEncoded();
        out.write(encoder.encodeToString(buf));
        out.write("\n");
    }

    public PrivateKey getPrivateKey(String filename) throws Exception {

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoder.decode(this.getKeyFromFile(filename)));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);

    }

    public PublicKey getPublicKey(String filename) throws Exception {
        System.out.println("gg");
        System.out.println("gg");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoder.decode(this.getKeyFromFile(filename)));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private String getKeyFromFile(String filename) throws IOException {

        List<String> keyString = Files.readAllLines(new File(filename).toPath());
        StringBuilder content = new StringBuilder();
        for (int i = 0; i< keyString.size(); i++){
            if(i == 0 || i == (keyString.size() -1)) continue;
            content.append(keyString.get(i));
        }
        return content.toString();
    }

}
