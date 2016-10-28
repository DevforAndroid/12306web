package com.exception;

/**
 * Service异常类
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 5786746916662131826L;

	public ServiceException() {
	}

	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
