package skplanet.recopick.demo.mall.exception;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
public class MemberNotFountException extends RuntimeException {

    public MemberNotFountException() {
    }

    public MemberNotFountException(String message) {
        super(message);
    }

    public MemberNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
