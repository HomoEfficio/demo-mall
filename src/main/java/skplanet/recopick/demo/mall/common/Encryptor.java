package skplanet.recopick.demo.mall.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-26
 */
@Component
public class Encryptor {

    private final MessageDigest sha256md;

    @Autowired
    public Encryptor(MessageDigest sha256md) {
        this.sha256md = sha256md;
    }

    public String sha256hash(String original) {

        byte[] hash = null;
        try {
            hash = sha256md.digest(original.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        return sb.toString();
    }
}
