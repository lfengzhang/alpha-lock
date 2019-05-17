package net.h2so.alphalock.exception;

/**
 * @Description 超时异常
 * @Auther mikicomo
 * @Date 2019-05-17 18:12
 */
public class AlphaLockTimeOutException extends RuntimeException {

    public AlphaLockTimeOutException() {
    }

    public AlphaLockTimeOutException(String message) {
        super(message);
    }

    public AlphaLockTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
