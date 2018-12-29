package rnk.l10.realm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HashUtils {

    private SecureRandom random;
    private static final String CHARSET = "UTF-8";
    private static final String ENCRYPTION_ALGORITHM = "SHA-512";
    private BASE64Decoder decoder = new BASE64Decoder();
    private BASE64Encoder encoder = new BASE64Encoder();

    public byte[] salt(int length) {
        random = new SecureRandom();
        byte bytes[] = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    public byte[] hash(String password, byte[] salt) {
        byte[] hashBytes = null;
        try {
            byte[] bytesOfMessage = password.getBytes(CHARSET);
            MessageDigest md;
            md = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
            md.reset();
            md.update(salt);
            md.update(bytesOfMessage);
            hashBytes = md.digest();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, "Encoding Problem", ex);
        }
        return hashBytes;
    }

    public byte[] hash_strong(String password, byte[] salt) {
        SecretKeyFactory factory;
        Key key = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keyspec = new PBEKeySpec(password.toCharArray(), salt, 1000, 512);
            key = factory.generateSecret(keyspec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key.getEncoded();
    }

    public String toBase64(byte[] text) {
        return encoder.encode(text);
    }

    public byte[] fromBase64(String text) {
        byte[] textBytes = null;
        try {
            textBytes = decoder.decodeBuffer(text);
        } catch (IOException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, "Encoding failed!", ex);
        }
        return textBytes;
    }
}
