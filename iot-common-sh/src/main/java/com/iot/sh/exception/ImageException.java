package com.iot.sh.exception;
 
/**
 * 
 * @author KenelLiu
 */
 public class ImageException extends Exception{
	 
	private static final long serialVersionUID = -9151850155683559654L;
	public ImageException(String message) {
		super(message);
	}

	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageException(Throwable cause) {
		super(cause);
	}
}
