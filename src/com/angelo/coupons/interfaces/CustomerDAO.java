package com.angelo.coupons.interfaces;

import java.util.Collection;

import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.beans.Customer;
import com.angelo.coupons.exceptions.CouponSystemException;

/*
 * Interface of Customer
 * Provides all information of customer functionality
 * Customer Manager class inherits from this interface
 */

public interface CustomerDAO {

	public Customer createCustomer(Customer customer) throws CouponSystemException;

	public void removeCustomer(Customer customer) throws CouponSystemException;

	public void updateCustomer(Customer customer) throws CouponSystemException;

	public Customer getCustomer(long id) throws CouponSystemException;

	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	public Collection<Coupon> getCoupons(long id) throws CouponSystemException;

	public boolean login(String custName, String password) throws CouponSystemException;
}
