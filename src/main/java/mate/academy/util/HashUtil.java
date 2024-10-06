package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITM = "SHA-512";

    private HashUtil() {

    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b: digest) {
                hashedPassword.append(b);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create hash using SHA-512 algoritm", e);
        }
        return hashedPassword.toString();
    }
}
