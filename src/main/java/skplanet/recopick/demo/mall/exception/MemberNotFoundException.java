package skplanet.recopick.demo.mall.exception;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
