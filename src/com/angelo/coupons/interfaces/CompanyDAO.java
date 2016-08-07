package com.angelo.coupons.interfaces;

import java.util.Collection;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.exceptions.CouponSystemException;

/*
 * Interface of Company
 * Provides all information of company functionality
 * Company Manager class inherits from this interface
 */

public interface CompanyDAO {

	public void createCompany(Company company) throws CouponSystemException;

	public void removeCompany(Company company) throws CouponSystemException;

	public void updateCompany(Company company) throws CouponSystemException;

	public Company getCompany(long id) throws CouponSystemException;

	public boolean login(String compName, String password) throws CouponSystemException;

	public Collection<Company> getAllCompanies() throws CouponSystemException;

	public Collection<Coupon> getCoupons(long id) throws CouponSystemException;

}
