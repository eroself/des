package com.wontlost.cypher.des;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DESCypher {

    public static DESData encrypt(String value, char[] secret) throws Exception{
        byte[] salt = "^2&j)_v%".getBytes(StandardCharsets.UTF_8);
        byte[] finalBytes = crypt(value, secret, salt, Cipher.ENCRYPT_MODE);
        return new DESData(salt, new String(Base64.getEncoder().encode(finalBytes), StandardCharsets.UTF_8));
    }

    public static String decrypt(DESData encryptedData, char[] secret) throws Exception{
        String value = encryptedData.encryptedString;
        byte[] salt = encryptedData.salt;
        byte[] finalBytes = crypt(value,secret, salt, Cipher.DECRYPT_MODE);
        return new String(finalBytes, StandardCharsets.UTF_8);
    }

    private static byte[] crypt(String value, char[] secret, byte[] salt, int cipher) throws Exception{
        byte[] bytes = new byte[0];
        if(cipher == Cipher.ENCRYPT_MODE) {
            bytes = value != null ? value.getBytes(StandardCharsets.UTF_8) : new byte[0];
        }else if(cipher == Cipher.DECRYPT_MODE) {
            bytes = value != null ? Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)) : new byte[0];
        }
        
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(secret));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(cipher, key, new PBEParameterSpec(salt, 20));
        return pbeCipher.doFinal(bytes);
    }

}
