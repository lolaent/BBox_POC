package com.lolatech.springkafka.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.omg.CORBA.portable.ApplicationException;

public final class HashUtil {

    /**
     * Utility class, so the constructor is private in order to avoid instantiation.
     */
    private HashUtil() {

    }

    /**
     * Hashes the input object using the MD5 algorithm.
     *
     * @param input
     * @return
     */
    public static String hash(Object input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] buffer = String.valueOf(input).getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
            return hexStr;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println("Unable to hash the message " + e);
        }
        
        return null;
    }

}