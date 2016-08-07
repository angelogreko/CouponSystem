package com.angelo.coupons.utils;


import java.util.Date;

import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.managers.CouponDBDAO;

/*
 * Manages expired coupons and deletes them from Company and Customer's Data Base 
 */

public class ExpirationManager implements Runnable {

	private boolean quit = false;
	private static final long DAY_IN_MILISECONDS = 24 * 60 * 60 * 1000;

	/**
	 * Activates a Thread daily calculated in milliseconds
	 */
	@Override
	public void run() {
		while (quit != true) {
			try {
				dailyCouponExpirationTask();
				Thread.sleep(DAY_IN_MILISECONDS);
			} catch (InterruptedException | CouponSystemException e) {
				try {
					throw new CouponSystemException("SQL Exception... Getting Coupon\n" + e.getMessage());
				} catch (CouponSystemException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Removes expired coupons
	 * 
	 * @throws CouponSystemException
	 */
	public void dailyCouponExpirationTask() throws CouponSystemException {
		CouponDBDAO couponDBDAO;
		try {
			couponDBDAO = new CouponDBDAO();
			couponDBDAO.removeExpiredCoupon(new Date());
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops task
	 */
	public void stopTask() {
		quit = true;
	}

}
