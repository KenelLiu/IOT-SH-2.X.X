package com.iot.sh.spring.exception;

public class BussinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -674410701129851443L;

	public BussinessException(String message) {
        super(message);
    }
	
    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }	
}
