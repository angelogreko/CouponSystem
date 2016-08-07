package com.angelo.coupons.exceptions;

public class CouponSystemException extends Exception {

	/*
	 * Exceptions thrown if connection threads tackle problems
	 */
	private static final long serialVersionUID = 1L;

	public CouponSystemException(String message) {
		super(message);
	}
}
