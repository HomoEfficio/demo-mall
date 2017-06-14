package skplanet.recopick.demo.mall.common;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Encryptor {

    @NonNull private final MessageDigest sha256md;

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
