package com.angelo.coupons.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.beans.Customer;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CustomerDAO;
import com.angelo.coupons.utils.UtilDBDAO;

/*
 * Customer Manager Class 
 * Including C.R.U.D., Login and other Data Base functions Coupons may be used for
 * Used by CouponFacade and Coupon System
 */

public class CustomerDBDAO implements CustomerDAO {
	private final UtilDBDAO getUtilFunction;

	public CustomerDBDAO() throws CouponSystemException {
		getUtilFunction = new UtilDBDAO();
	}

	/**
	 * Creating a new Customer & Checking for duplications in customer names
	 * 
	 * @return
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Customer createCustomer(Customer customer) throws CouponSystemException {
		String sql = "INSERT INTO customer (cust_name, password) VALUES ('" + customer.getCustName() + "','"
				+ customer.getPassword() + "'); ";
		Customer createdCustomer;
		if (!CustomerNameExists(customer)) {
			ResultSet result = getUtilFunction.activateQuery(sql, true);
			try {
				if (result.next()) {
					createdCustomer = new Customer(result.getLong(1), customer.getCustName(), customer.getPassword());
				} else {
					throw new CouponSystemException("  Customer Create Failed");
				}
			} catch (SQLException e) {
				throw new CouponSystemException("SQL DAO Exception... Function create Customer\n" + e.getMessage());
			}
		} else {
			throw new CouponSystemException("SQL DAO Exception... Customer Name Exists\n");
		}
		return createdCustomer;
	}

	/**
	 * Checking for duplications in customer names
	 * 
	 * @return true if name exists and false if not
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	private boolean CustomerNameExists(Customer customer) throws CouponSystemException {
		String sql = "select * from customer where cust_name='" + customer.getCustName() + "'";
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			if (result.next())
				return true;
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Customer Name Exists\n" + e.getMessage());
		}
		return false;
	}

	/**
	 * Deleting customer and his coupons
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		String sqlCustCoup = "DELETE FROM customer_coupon WHERE cust_id=" + customer.getId();
		String sqlCustomer = "DELETE FROM customer WHERE id=" + customer.getId();
		if (getCoupons(customer.getId()).size() > 0) {
			getUtilFunction.activateQuery(sqlCustCoup, true);
		}
		getUtilFunction.activateQuery(sqlCustomer, true);
	}

	/**
	 * Updating customer
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		String sql = "UPDATE customer SET password = '" + customer.getPassword() + "' WHERE id = " + customer.getId();
		getUtilFunction.activateQuery(sql, true);
	}

	/**
	 * Getting customer by id
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		Customer customer = null;
		String sql = "SELECT customer.id cust_id ,cust_name,password,coupon.* " + "FROM customer "
				+ "left join customer_coupon on customer.id=customer_coupon.cust_id "
				+ "left join coupon on customer_coupon.coupon_id=coupon.id " + "where customer.id=" + id;
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			if (result.next()) {
				customer = setCustomerByResult(result);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Getting Customer)\n" + e.getMessage());
		}
		return customer;
	}

	/**
	 * Getting all customers
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		Collection<Customer> customerArray = new ArrayList<Customer>();
		String sql = "SELECT customer.id cust_id ,cust_name,password,coupon.* " + "FROM customer "
				+ "left join customer_coupon on customer.id=customer_coupon.cust_id "
				+ "left join coupon on customer_coupon.coupon_id=coupon.id ";
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			while (result.next()) {
				Customer customer = setCustomerByResult(result);
				customerArray.add(customer);
			}
		} catch (SQLException e) {
			throw new CouponSystemException(
					"Sql Exception **CustomerDBDAO\nFunction getAllCustomers()\n" + e.getMessage());
		}
		return customerArray;
	}

	/**
	 * Getting coupons of a customer by id
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getCoupons(long id) throws CouponSystemException {
		String sql = "select coupon.* from coupon " + "join customer_coupon on coupon.id=customer_coupon.coupon_id "
				+ "where customer_coupon.cust_id=" + id;
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result, "id", 1L);
	}

	/**
	 * Login of customer
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		Customer customer = getCustomer(custName, password);
		if (customer != null)
			return true;
		return false;
	}

	/**
	 * Purchased coupons of customer
	 * 
	 * @return
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Coupon purchaseCoupon(Coupon coupon, long customerId) throws CouponSystemException {
		long DAY_IN_MILISECONDS = 24 * 60 * 60 * 1000;
		Coupon purchasedCoupon = null;
		Date allDayOfToday = new Date(System.currentTimeMillis() - DAY_IN_MILISECONDS + 1000);
		if (coupon.getEndDate().after(allDayOfToday)) {
			if (!CustomerAlredyBoughtThisCoupon(coupon, customerId)) {
				if (coupon.getAmount() > 0) {
					ResultSet result = getUtilFunction
							.activateQuery("INSERT INTO customer_coupon(cust_id,coupon_id) VALUES(" + customerId + ","
									+ coupon.getId() + ");", true);
					try {
						if (result.next()) {
							getUtilFunction.activateQuery("UPDATE coupon SET amount = " + (coupon.getAmount() - 1)
									+ " WHERE id =" + coupon.getId(), true);
							purchasedCoupon = new Coupon(result.getLong(1), coupon.getTitle(), coupon.getStartDate(),
									coupon.getEndDate(), coupon.getAmount(), coupon.getType(), coupon.getMessage(),
									coupon.getPrice(), coupon.getImage());
						}
					} catch (SQLException e) {
						throw new CouponSystemException(
								"Sql Exception **CustomerDBDAO\nFunction purchaseCoupon(Coupon coupon, long customerId)\n"
										+ e.getMessage());
					}

				} else
					throw new CouponSystemException("Alert!!!  Coupon Amount Sold");
			} else
				throw new CouponSystemException("Alert!!!  This Customer Already Buy This Coupon");
		} else
			throw new CouponSystemException("Alert!!!  Coupon Expired");
		return purchasedCoupon;
	}

	/**
	 * Checking if customer had bought a duplicate coupon
	 * 
	 * @param coupon
	 * @param customerId
	 * @return
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */

	private boolean CustomerAlredyBoughtThisCoupon(Coupon coupon, long customerId) throws CouponSystemException {
		String sql = "select * from customer_coupon where cust_id=" + customerId + " and coupon_id=" + coupon.getId();
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			if (result.next())
				return true;
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Customer Exists");
		}
		return false;
	}

	/**
	 * Getting customer
	 * 
	 * @param custName
	 * @param password
	 * @return
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(String custName, String password) throws CouponSystemException {
		Customer customer = null;
		String sql = "SELECT customer.id cust_id ,cust_name,password,coupon.* " + "FROM customer "
				+ "left join customer_coupon on customer.id=customer_coupon.cust_id "
				+ "left join coupon on customer_coupon.coupon_id=coupon.id " + "where cust_name='" + custName
				+ "' and password='" + password + "';";
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		try {
			if (result.next()) {
				customer = setCustomerByResult(result);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Get Customer");
		}
		return customer;
	}

	/**
	 * Setting customer by result
	 * 
	 * @param result
	 * @return customer
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	private Customer setCustomerByResult(ResultSet result) throws CouponSystemException {
		Customer customer = null;
		try {
			customer = new Customer(result.getLong("cust_id"), result.getString("cust_name"),
					result.getString("password"));
			customer.setCoupons(
					getUtilFunction.setCouponCollectionByResult(result, "cust_id", result.getLong("cust_id")));
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... Set Customer By Result\n" + e.getMessage());
		}
		return customer;
	}

	/**
	 * Getting all coupons purchased by type
	 * 
	 * @param couponType
	 * @param customerId
	 * @return Array
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponByType(CouponType couponType, long customerId)
			throws CouponSystemException {
		String sql = "select coupon.* from coupon " + "join customer_coupon on coupon.id=customer_coupon.coupon_id "
				+ "where customer_coupon.cust_id=" + customerId + " and coupon.type='" + couponType.name() + "'";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result, "id", 1L);
	}

	/**
	 * Getting all coupons purchased by price
	 * 
	 * @param maxPrice
	 * @param customerId
	 * @return Array
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponByPrice(double maxPrice, long customerId)
			throws CouponSystemException {
		String sql = "select coupon.* from coupon " + "join customer_coupon on coupon.id=customer_coupon.coupon_id "
				+ "where customer_coupon.cust_id=" + customerId + " and coupon.price<" + maxPrice;
		ResultSet result = getUtilFunction.activateQuery(sql, false);
		return getUtilFunction.setCouponCollectionByResult(result, "id", 1L);
	}

	public Collection<Coupon> getAvailableCoupons(long id) throws CouponSystemException {
		String sql = "select * from coupon " + "where amount > 0 " + "and id not in " + "(SELECT coupon.id FROM coupon "
				+ "join customer_coupon on customer_coupon.coupon_id=coupon.id " + "where customer_coupon.cust_id=" + id
				+ ")";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result, "id", 1L);
	}

	public Collection<Coupon> getAvailableCouponsByPrice(double maxPrice, long customerId)
			throws CouponSystemException {
		String sql = "select * from coupon " + "where amount > 0 " + " and coupon.price<" + maxPrice + " and id not in "
				+ "(SELECT coupon.id FROM coupon " + "join customer_coupon on customer_coupon.coupon_id=coupon.id "
				+ "where customer_coupon.cust_id=" + customerId + ")";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result, "id", 1L);
	}

	public Collection<Coupon> getAvailableCouponsByType(CouponType couponType, long customerId)
			throws CouponSystemException {
		String sql = "select * from coupon " + "where amount > 0 " + " and type='" + couponType.name()
				+ "' and id not in " + "(SELECT coupon.id FROM coupon "
				+ "join customer_coupon on customer_coupon.coupon_id=coupon.id " + "where customer_coupon.cust_id="
				+ customerId + ")";
		ResultSet result = getUtilFunction.activateQuery(sql, false);

		return getUtilFunction.setCouponCollectionByResult(result, "id", 1L);
	}
}
