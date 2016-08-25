package com.angelo.coupons.facades;

import java.util.Collection;
import java.util.Date;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CouponClientFacade;
import com.angelo.coupons.managers.CompanyDBDAO;
import com.angelo.coupons.managers.CouponDBDAO;

/*
 * CompanyFacade... Main page of a company Administrator 
 * Controlling functions the company and its coupons 
 */

public class CompanyFacade implements CouponClientFacade {

	private CouponDBDAO couponDBDAO;
	private CompanyDBDAO companyDBDAO;
	public Company company;

	public CompanyFacade(Company company) throws CouponSystemException{
		couponDBDAO = new CouponDBDAO();
		companyDBDAO = new CompanyDBDAO();
		this.company = company;
	}

	/**
	 * Creating a new Coupon Checking for duplications in coupon titles Checking
	 * for improper dates
	 * @return 
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Coupon createCoupon(Coupon coupon) throws CouponSystemException{
		Coupon createdCoupon = couponDBDAO.createCoupon(coupon, company);
		company.setCoupons(companyDBDAO.getCoupons(company.getId()));
		return createdCoupon;
	}

	/**
	 * Deleting coupons from Data Base
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException{
		couponDBDAO.removeCoupon(coupon);
		company.setCoupons(companyDBDAO.getCoupons(company.getId()));
	}

	/**
	 * Updating coupons in the Data Base
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException{
		couponDBDAO.updateCoupon(coupon);
		for (Coupon couponOfCompany : company.getCoupons()) {
			if (couponOfCompany.getId() == coupon.getId())
				couponOfCompany = coupon;
		}
	}

	/**
	 * Getting coupons by id
	 * 
	 * @param id
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Coupon getCoupon(long id) throws CouponSystemException{
		return couponDBDAO.getCoupon(id);
	}

	/**
	 * Getting all coupons
	 * 
	 * @return collection of all coupons related to company
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException{
		return companyDBDAO.getCoupons(company.getId());
	}

	/**
	 * Getting all coupons by type
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByType(CouponType type) throws CouponSystemException{
		return companyDBDAO.getCouponsByType(type, company.getId());
	}

	/**
	 * Getting coupons by price
	 * 
	 * @param MaxPrice
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByPrice(double maxPrice) throws CouponSystemException{
		return companyDBDAO.getCouponsByPrice(maxPrice, company.getId());
	}

	/**
	 * Getting coupons by date
	 * 
	 * @param Date
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByDate(Date date) throws CouponSystemException{
		return companyDBDAO.getCouponsByDate(date, company.getId());
	}

}
