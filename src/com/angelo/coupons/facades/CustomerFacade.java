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
	CustomerDBDAO customerDBDAO;
	public Customer customer;

	public CustomerFacade(String name, String password) throws CouponSystemException{
		customerDBDAO = new CustomerDBDAO();
		this.customer = customerDBDAO.getCustomer(name, password);
	}

	/**
	 * Purchasing coupons
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException{
		customerDBDAO.purchasedCoupon(coupon, customer.getId());
		customer.setCoupons(getAllPurchasedCoupon());
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
	public Collection<Coupon> getAllPurchaseCouponByType(CouponType couponType)
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

}
