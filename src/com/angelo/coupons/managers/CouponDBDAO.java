package com.angelo.coupons.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CouponDAO;
import com.angelo.coupons.utils.UtilDBDAO;

/*
 * Coupon Manager Class 
 * Including C.R.U.D. and other Data Base functions Coupons may be used for
 * Used by CouponFacade 
 */

public class CouponDBDAO implements CouponDAO {
	private final UtilDBDAO getUtilFunction;

	public CouponDBDAO() throws CouponSystemException {
		getUtilFunction = new UtilDBDAO();
	}

	/**
	 * Creating a new Coupon Checking for duplications in coupon titles Checking
	 * for improper dates
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void createCoupon(Coupon coupon, Company company) throws CouponSystemException{
		if (!CouponTitleExists(coupon)) {
			java.sql.Date startDate = new java.sql.Date(coupon.getStartDate().getTime());
			java.sql.Date endDate = new java.sql.Date(coupon.getEndDate().getTime());
			if (startDate.before(endDate) || startDate.equals(endDate)) {
				String sql = "INSERT INTO coupon(title,start_date,end_date,amount,type,message,price,image)VALUES('"
						+ coupon.getTitle()	+ "','"
						+ startDate + "','"
						+ endDate 	+ "',"
						+ coupon.getAmount()+ ",'"
						+ coupon.getType() + "','"
						+ coupon.getMessage() + "',"
						+ coupon.getPrice() + ",'"
						+ coupon.getImage() + "'); ";
				ResultSet result = getUtilFunction.activateQuery(sql, true);
				try {
					if (result.next()) {
						String sqlCompCoup = "INSERT INTO company_coupon(comp_id,coupon_id) " + "VALUES("
								+ company.getId() + "," + result.getLong(1) + ");";
						getUtilFunction.activateQuery(sqlCompCoup, true);
					} else {
						throw new CouponSystemException("SQL DAO Exception... Creating Coupon Failed");
					}
				} catch (SQLException e) {
					throw new CouponSystemException("SQL DAO Exception... Creating Coupon Failed" + e.getMessage());
				}
			} else {
				throw new CouponSystemException("SQL DAO Exception... Coupon end date is before start date");
			}
		} else {
			throw new CouponSystemException("SQL DAO Exception... Coupon Title Exists");
		}

	}

	/**
	 * Checking existence of similar titles in the Data Base
	 * 
	 * @param coupon
	 * @return true if coupon exists or false if not
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	private boolean CouponTitleExists(Coupon coupon) throws CouponSystemException{
		String sql = "select * from coupon where title='" + coupon.getTitle() + "'";
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			if (result.next())
				return true;
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Coupon Name Exists\n" + e.getMessage());
		}
		return false;
	}

	/**
	 * Deleting coupons from Data Base
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException{
		String sql = "DELETE from coupon where id= " + coupon.getId();
		String sqlCompCoup = "DELETE FROM company_coupon WHERE coupon_id=" + coupon.getId();
		String sqlCoup = "DELETE FROM coupon WHERE id=" + coupon.getId();
		getUtilFunction.activateQuery(sqlCompCoup, true);
		getUtilFunction.activateQuery(sqlCoup, true);
		getUtilFunction.activateQuery(sql, true);
	}

	/**
	 * Updating coupons in the Data Base
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException{
		java.sql.Date endDate = new java.sql.Date(coupon.getEndDate().getTime());
		String sql = "UPDATE coupon SET end_date = '"
				+ endDate + "',price = " + coupon.getPrice() + " WHERE id ="
				+ coupon.getId();
		getUtilFunction.activateQuery(sql, true);
	}

	/**
	 * Getting coupons by id
	 * 
	 * @param id
	 * @return coupon
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Coupon getCoupon(long id) throws CouponSystemException{
		Coupon coupon = null;
		String sql = "select * from coupon where id=" + id;
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		try {
			if (result.next()) {
				coupon = getUtilFunction.setCouponByResult(result);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception Getting Coupon by id\n" + e.getMessage());
		}
		return coupon;
	}

	/**
	 * Getting all coupons
	 * 
	 * @param coupon
	 * @return coupon array
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException{
		String sql = "select * from coupon";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result);
	}

	/**
	 * Getting coupons by type
	 * 
	 * @param coupon
	 * @return Array
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException{
		String sql = "select * from coupon where type='" + couponType + "'";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result);
	}

	/**
	 * Removing expired coupons by date
	 * 
	 * @param Date
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void removeExpiredCoupon(java.util.Date date) throws CouponSystemException{
		java.sql.Date yesterday = new java.sql.Date(date.getTime());
		String sql = "select * from coupon where end_date < '" + yesterday + "';";
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			while (result.next()) {
				removeCoupon(new Coupon(result.getLong("id")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Removing Expired Coupons\n" + e.getMessage());
		}
	}

}
