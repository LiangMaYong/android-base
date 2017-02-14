package com.liangmayong.base.support.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by LiangMaYong on 2017/2/13.
 */
public class RSAUtils {

    private static String KEY_RSA = "RSA";
    private static String CIPHER_RSA = "RSA/ECB/PKCS1Padding";

    /**
     * generateRSAKeyPair
     *
     * @return KeyPair
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(1024);
    }

    /**
     * generateRSAKeyPair
     *
     * @param keyLength keyLength
     * @return KeyPair
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * encryptData
     *
     * @param data      data
     * @param publicKey publicKey
     * @return bytes
     */
    public static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * decryptData
     *
     * @param encryptedData encryptedData
     * @param privateKey    privateKey
     * @return bytes
     */
    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * getPublicKey
     *
     * @param keyBytes keyBytes
     * @return PublicKey
     * @throws Exception e
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Public key illegal");
        } catch (NullPointerException e) {
            throw new Exception("Public key is NULL");
        }
    }

    /**
     * getPrivateKey
     *
     * @param keyBytes keyBytes
     * @return PrivateKey
     * @throws Exception e
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Public key illegal");
        } catch (NullPointerException e) {
            throw new Exception("Public key is NULL");
        }
    }

    /**
     * getPublicKey
     *
     * @param modulus        modulus
     * @param publicExponent privateExponent
     * @return PublicKey
     * @throws Exception e
     */
    public static PublicKey getPublicKey(String modulus, String publicExponent) throws Exception {
        try {
            BigInteger bigIntModulus = new BigInteger(modulus);
            BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Public key illegal");
        } catch (NullPointerException e) {
            throw new Exception("Public key is NULL");
        }
    }

    /**
     * getPrivateKey
     *
     * @param modulus         modulus
     * @param privateExponent privateExponent
     * @return PrivateKey
     * @throws Exception e
     */
    public static PrivateKey getPrivateKey(String modulus, String privateExponent) throws Exception {
        try {
            BigInteger bigIntModulus = new BigInteger(modulus);
            BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Private key illegal");
        } catch (NullPointerException e) {
            throw new Exception("Private key is NULL");
        }
    }

    /**
     * loadPublicKey
     *
     * @param publicKeyStr privateKeyStr
     * @return PublicKey
     * @throws Exception e
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(clearKeyComment(publicKeyStr));

            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Public key illegal");
        } catch (NullPointerException e) {
            throw new Exception("Public key is NULL");
        }
    }

    /**
     * loadPrivateKey
     *
     * @param privateKeyStr privateKeyStr
     * @return PrivateKey
     * @throws Exception e
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(clearKeyComment(privateKeyStr));

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Private key illegal");
        } catch (NullPointerException e) {
            throw new Exception("Private key is NULL");
        }
    }

    /**
     * clearKeyComment
     *
     * @param key key
     * @return key
     */
    private static String clearKeyComment(String key) {
        int BEGIN_START = key.indexOf("-----");
        int BEGIN_END = -1;
        if (BEGIN_START >= 0) {
            BEGIN_END = key.indexOf("-----", BEGIN_START + "-----".length());
        }
        while (BEGIN_START >= 0 && BEGIN_END > 0) {
            key = key.subSequence(0, BEGIN_START) + key.substring(BEGIN_END + "-----".length());
            BEGIN_START = key.indexOf("-----");
            if (BEGIN_START >= 0) {
                BEGIN_END = key.indexOf("-----", BEGIN_START + "-----".length());
            }
        }
        return key;
    }

    /**
     * loadPublicKey
     *
     * @param inputStream inputStream
     * @return PublicKey
     * @throws Exception e
     */
    public static PublicKey loadPublicKey(InputStream inputStream) throws Exception {
        try {
            return loadPublicKey(readKey(inputStream));
        } catch (IOException e) {
            throw new Exception("Public key data read error");
        } catch (NullPointerException e) {
            throw new Exception("Public key input stream is NULL");
        }
    }

    /**
     * loadPrivateKey
     *
     * @param inputStream inputStream
     * @return PrivateKey
     * @throws Exception e
     */
    public static PrivateKey loadPrivateKey(InputStream inputStream) throws Exception {
        try {
            return loadPrivateKey(readKey(inputStream));
        } catch (IOException e) {
            throw new Exception("Private key data read error");
        } catch (NullPointerException e) {
            throw new Exception("Private key input stream is NULL");
        }
    }

    /**
     * readKey
     *
     * @param inputStream input
     * @return key
     * @throws Exception e
     */
    private static String readKey(InputStream inputStream) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        return sb.toString();
    }

    /**
     * printPublicKeyInfo
     *
     * @param publicKey publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    /**
     * printPrivateKeyInfo
     *
     * @param privateKey privateKey
     */
    public static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());
    }

}
