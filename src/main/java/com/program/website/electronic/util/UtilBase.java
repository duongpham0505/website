package com.program.website.electronic.util;

import com.program.website.electronic.constant.WebElectronicConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class UtilBase {
    private static final Logger logger = LogManager.getLogger(UtilBase.class);
    private static final UtilBase utilBase = new UtilBase();
    public static UtilBase getInstance(){return utilBase;}

    public String convertPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte [] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        }catch (Exception e) {
            logger.error(e);
        }
        return WebElectronicConstant.IS_EXITS;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
