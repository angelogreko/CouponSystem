package com.angelo.coupons.facades;

import java.util.Collection;

import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.beans.Customer;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CouponClientFacade;
import com.angelo.coupons.managers.CustomerDBDAO;

/*
 * CustomerFacade... Main page of a customer Administrator 
 * Controlling functions of customer and its coupons 
 */

public class CustomerFacade implements CouponClientFacade {
	private CustomerDBDAO customerDBDAO;
	public Customer customer;

	public CustomerFacade(Customer customer) throws CouponSystemException{
		customerDBDAO = new CustomerDBDAO();
		this.customer = customer;
	}

	/**
	 * Purchasing coupons
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException {
		Coupon purchaseCoupon = customerDBDAO.purchaseCoupon(coupon, customer.getId());
		customer.setCoupons(getAllPurchasedCoupon());
		return purchaseCoupon;
	}
	/**
	 * Getting all Purchased coupons
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCoupon() throws CouponSystemException{
		return customerDBDAO.getCoupons(customer.getId());
	}

	/**
	 * Getting all Purchased coupons by type
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponByType(CouponType couponType)
			throws CouponSystemException{
		return customerDBDAO.getAllPurchasedCouponByType(couponType, customer.getId());
	}

	/**
	 * Getting all Purchased coupons by price
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponByPrice(double maxPrice)
			throws CouponSystemException{
		return customerDBDAO.getAllPurchasedCouponByPrice(maxPrice, customer.getId());

	}

	public Collection<Coupon> getAvailableCouponsByType(CouponType couponType)
			throws CouponSystemException {
		return customerDBDAO.getAvailableCouponsByType(couponType,
				customer.getId());
	}
	
	public Collection<Coupon> getAvailableCouponsByPrice(double maxPrice)
			throws CouponSystemException {
		return customerDBDAO.getAvailableCouponsByPrice(maxPrice,
				customer.getId());
	}
	
	public Collection<Coupon> getAvailableCoupons()
			throws CouponSystemException {
		return customerDBDAO.getAvailableCoupons(customer.getId());
	}

	


}
