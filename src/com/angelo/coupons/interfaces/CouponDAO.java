package com.angelo.coupons.interfaces;

import java.util.Collection;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;

/*
 * Interface of Coupon
 * Provides all information of coupon functionality
 * Coupon Manager class inherits from this interface
 */

public interface CouponDAO {

	public Coupon createCoupon(Coupon coupon, Company company) throws CouponSystemException;

	public void removeCoupon(Coupon coupon) throws CouponSystemException;

	public void updateCoupon(Coupon coupon) throws CouponSystemException;

	public Coupon getCoupon(long id) throws CouponSystemException;

	public Collection<Coupon> getAllCoupons() throws CouponSystemException;

	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException;
}
