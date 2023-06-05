package com.iot.sh.exception;
 
 public class HttpResponseException extends Exception{
	 
	private static final long serialVersionUID = -9151850155683559654L;

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
