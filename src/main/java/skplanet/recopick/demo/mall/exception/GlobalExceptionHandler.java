package skplanet.recopick.demo.mall.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author homo.efficio@gmail.com
 *         Created on 2017-06-17.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MemberNotFoundException.class})
    public ResponseEntity<Object> handleMemberNotFountException(MemberNotFoundException e,
                                                                HttpServletRequest req,
                                                                WebRequest webReq) {
        log.error("requested URL: {}, message: {}",
                req.getRequestURI(), e.getMessage());

        return handleExceptionInternal(e, "존재하지 않는 사용자입니다.", new HttpHeaders(), HttpStatus.NOT_FOUND, webReq);
    }

    @ExceptionHandler({CartNotFoundException.class})
    public ResponseEntity<Object> handleCartNotFountException(CartNotFoundException e,
                                                              HttpServletRequest req,
                                                              WebRequest webReq) {
        log.error("requested URL: {}, message: {}",
                req.getRequestURI(), e.getMessage());

        return handleExceptionInternal(e, "존재하지 않는 카트입니다.", new HttpHeaders(), HttpStatus.NOT_FOUND, webReq);
    }
}
