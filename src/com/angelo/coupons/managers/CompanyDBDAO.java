package com.angelo.coupons.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CompanyDAO;
import com.angelo.coupons.utils.UtilDBDAO;

/*
 * Company Manager Class 
 * Including C.R.U.D., Log In and other Data Base functions a Company may use
 * Used by CompanyFacade and Coupon System  
 */

public class CompanyDBDAO implements CompanyDAO {
	
	private final UtilDBDAO getUtilFunction;

	public CompanyDBDAO() throws CouponSystemException {
		getUtilFunction = new UtilDBDAO();
	}

	/**
	 * Creating a new Company & Checking for duplications in company names.
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void createCompany(Company company) throws CouponSystemException{
		String sql = "INSERT INTO company (comp_name, password,email) VALUES ( '" + company.getCompName() + "', " + "'"
				+ company.getPassword() + "', '" + company.getEmail() + "' );";
		if (!CheckName(company)) {
			getUtilFunction.activateQuery(sql, true);
		} else {
			throw new CouponSystemException("Company Name Exists!");
		}
	}

	/**
	 * Checks the added Company Name existence in the Data Base.
	 * 
	 * @param company
	 * @return true if same name exists.
	 * @throws CouponSystemException
	 *             of Company Name Exists
	 * @throws CouponSystemException
	 */
	public boolean CheckName(Company company) throws CouponSystemException{
		String sqlCheckName = "select * from company where comp_name='" + company.getCompName() + "'";
		ResultSet result = getUtilFunction.activateQuery(sqlCheckName, false);
		try {
			if (result.next()) {
				return true;
			}
		} catch (Exception e) {
			throw new CouponSystemException("Company Name Exists!");
		}
		return false;
	}

	/**
	 * Deleting a Company and all Coupons it owns from coupon and customer Data
	 * Base.
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void removeCompany(Company company) throws CouponSystemException{
		Collection<Coupon> couponArray = company.getCoupons();
		String inValues = "";
		String sqlCompCoup = "DELETE FROM company_coupon WHERE comp_id=" + company.getId();
		String sqlCustCoup = "DELETE FROM customer_coupon WHERE coupon_id in(" + inValues + ")";
		String sqlCoup = "DELETE FROM coupon WHERE id in(" + inValues + ")";
		String sqlComp = "DELETE FROM company WHERE id=" + company.getId();
		if (couponArray.size() > 0) {
			getUtilFunction.activateQuery(sqlCompCoup, true);
			for (Coupon coupon : couponArray) {
				inValues += coupon.getId() + ",";
			}
			inValues = inValues.substring(0, inValues.length() - 1);
			getUtilFunction.activateQuery(sqlCustCoup, true);

			getUtilFunction.activateQuery(sqlCoup, true);
		}

		getUtilFunction.activateQuery(sqlComp, true);
	}

	/**
	 * Updating data of a company
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException{
		String sql = "UPDATE company SET password = '" + company.getPassword() + "', email = '" + company.getEmail()
				+ "' WHERE id = " + company.getId();
		getUtilFunction.activateQuery(sql, true);
	}

	/**
	 * Getting a company by ID
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Company getCompany(long id) throws CouponSystemException{
		Company company = null;
		String sql = "select * from company where id=" + id;
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		try {
			if (result.next()) {
				company = setCompanyByResult(result);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Getting Company by id\n" + e.getMessage());
		}
		return company;

	}

	/**
	 * Getting all companies in an Array
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException{
		Collection<Company> companyArray = new ArrayList<Company>();
		String sql = "select * from company";
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			while (result.next()) {
				Company company = setCompanyByResult(result);
				companyArray.add(company);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Geting All Companies\n" + e.getMessage());
		}
		return companyArray;
	}

	/**
	 * Getting a company
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Company getCompany(String compName, String password) throws CouponSystemException{
		Company company = null;
		String sql = "SELECT * FROM company where comp_name='" + compName + "' and password='" + password + "';";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		try {
			if (result.next()) {
				company = setCompanyByResult(result);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Geting All Companies\n" + e.getMessage());
		}
		return company;
	}

	/**
	 * Getting coupons by id
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getCoupons(long id) throws CouponSystemException{
		String sql = "select coupon.* " + "from coupon join company_coupon on coupon.id=company_coupon.coupon_id "
				+ "where company_coupon.comp_id=" + id;
		ResultSet resultSet = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(resultSet);
	}

	/**
	 * Setting a company by result set
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	private Company setCompanyByResult(ResultSet result) throws CouponSystemException{
		Company company = null;
		try {
			company = new Company(result.getLong("id"), 
					result.getString("comp_name"), 
					result.getString("password"),
					result.getString("email"));
			Collection<Coupon> couponArray = getCoupons(result.getLong("id"));
			company.setCoupons(couponArray);
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Setting Company by result\n" + e.getMessage());
		}
		return company;
	}

	/**
	 * Log in of a company
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String compName, String password) throws CouponSystemException{
		Company company = getCompany(compName, password);
		if (company != null)
			return true;
		return false;
	}

	/**
	 * Getting coupons by type
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByType(CouponType couponType, long companyId)
			throws CouponSystemException{
		String sql = "select coupon.* "
				+ "from coupon join company_coupon on coupon.id=company_coupon.coupon_id "
				+ "where company_coupon.comp_id=" + companyId
				+ " and coupon.type='" + couponType.name() + "'";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result);
	}

	/**
	 * Getting coupons by price
	 * 
	 * @param maxPrice
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByPrice(double maxPrice, long companyId) throws CouponSystemException{
		String sql = "select coupon.* " + "from coupon join company_coupon on coupon.id=company_coupon.coupon_id "
				+ "where company_coupon.comp_id=" + companyId + " and coupon.price<" + maxPrice;
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result);
	}

	/**
	 * Getting coupons by date
	 * 
	 * @param date
	 * @param companyId
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByDate(Date date, long companyId) throws CouponSystemException{
		Date sqlDate = new Date(date.getTime());
		String sql = "select coupon.* "
				+ "from coupon join company_coupon on coupon.id=company_coupon.coupon_id "
				+ "where company_coupon.comp_id=" + companyId
				+ " and coupon.end_date<'" + sqlDate+"'";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result);
	}

}
