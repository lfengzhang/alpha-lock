package net.h2so.alphalock.exception;

/**
 * @Description 调用异常
 * @Auther mikicomo
 * @Date 2019-05-17 18:11
 */
public class AlphaLockInvokeException extends RuntimeException {

    public AlphaLockInvokeException() {
    }

    public AlphaLockInvokeException(String message) {
        super(message);
    }

    public AlphaLockInvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
