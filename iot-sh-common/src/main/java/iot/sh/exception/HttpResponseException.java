package iot.sh.exception;
/**
 *
 * @author KenelLiu
 *
 */
 public class HttpResponseException extends Exception{

	 public HttpResponseException(String message) {
		 super(message);
	 }

	 public HttpResponseException(String message, Throwable cause) {
		 super(message, cause);
	 }

	 public HttpResponseException(Throwable cause) {
		 super(cause);
	 }
}
