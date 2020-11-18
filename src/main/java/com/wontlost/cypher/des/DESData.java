package com.wontlost.cypher.des;

public class DESData {

    public final byte[] salt;
    public final String encryptedString;

    public DESData(byte[] salt, String encryptedString) {
        this.salt = salt;
        this.encryptedString = encryptedString;
    }

}
