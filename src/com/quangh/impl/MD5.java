package com.quangh.impl;

import com.quangh.HashFunction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 implements HashFunction {
    private MessageDigest instance;

    public MD5() {
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
        }
    }

    @Override public long hash(String key) {
        instance.reset();
        instance.update(key.getBytes());
        byte[] digest = instance.digest();

        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= ((int) digest[i]) & 0xFF;
        }
        return h;
    }
}
