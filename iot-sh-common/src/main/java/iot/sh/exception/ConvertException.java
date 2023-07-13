package iot.sh.exception;
/**
 *
 * @author KenelLiu
 *
 */
 public class ConvertException extends Exception{

	 public ConvertException(String message) {
		 super(message);
	 }

	 public ConvertException(String message, Throwable cause) {
		 super(message, cause);
	 }

	 public ConvertException(Throwable cause) {
		 super(cause);
	 }
}
