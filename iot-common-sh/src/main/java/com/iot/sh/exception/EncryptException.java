package com.iot.sh.exception;

/**
 * 
 * @author KenelLiu
 *
 */
public class EncryptException extends Exception {

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }
    public EncryptException(Throwable cause) {
        super(cause);
    }
}
