package skplanet.recopick.demo.mall.exception;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-30
 */
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
    }

    public CartNotFoundException(String message) {
        super(message);
    }

    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
