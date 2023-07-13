package iot.sh.exception;

/**
 * @author Kenel Liu
 */
public class FtpException extends Exception{

    public FtpException(String message) {
        super(message);
    }

    public FtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpException(Throwable cause) {
        super(cause);
    }
}
