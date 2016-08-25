package com.angelo.coupons.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;

/*
 * Utility Class
 * Includes functions widely used throughout this program to minimize written code in other classes   
 */

public class UtilDBDAO {

	private ConnectionPool pool;

	public UtilDBDAO() throws CouponSystemException {
		pool = ConnectionPool.getInstance();
	}
	
	/**
	 * Activates SQL Queries to Data Base
	 * 
	 * @param sql
	 * @param update
	 *            (true or false)
	 * @return result
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public ResultSet activateQuery(String sql, boolean update) throws CouponSystemException {
		System.out.println(sql);
		Statement statement;
		ResultSet result = null;
		Connection connection;
		try {
			connection = pool.getConnection();
			if (update) {
				statement = connection.prepareStatement(sql, new String[] { "id" });
				statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				result = statement.getGeneratedKeys();

			} else {
				statement = connection.createStatement();
				result = statement.executeQuery(sql);
			}
			pool.returnConnection(connection);
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... **UtilDBDAO** \n" + e.getMessage());
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Thread Exception... **UtilDBDAO** \n" + e.getMessage());
		}
		return result;
	}

	/**
	 * Coupon Collection function
	 * 
	 * @param result
	 * @return coupon Array
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> setCouponCollectionByResult(ResultSet result, String idField, long id)
			throws CouponSystemException {
		Collection<Coupon> couponArray = new ArrayList<Coupon>();
		try {
			if (idField != "id") {
				result.previous();
			}
			while (result.next()) {
				if (result.getLong(idField) == id || idField == "id") {
					if (result.getLong("id") != 0) {
						Coupon coupon = setCouponByResult(result);
						couponArray.add(coupon);
					}
				} else {
					result.previous();
					return couponArray;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException(
					"SQL DAO Exception... Setting Coupon Collection by Result \n" + e.getMessage());
		}
		return couponArray;
	}

	public Coupon setCouponByResult(ResultSet result) throws CouponSystemException {
		Coupon coupon;
		try {
			coupon = new Coupon(result.getLong("id"), result.getString("title"), 
					result.getDate("start_date"), result.getDate("end_date"), 
					result.getInt("amount"), CouponType.valueOf(result.getString("type")),
					result.getString("message"), result.getDouble("price"), 
					result.getString("image"));
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception Setting Coupon by Result\n" + e.getMessage());
		}
		return coupon;
	}

	public static Date getDate(String date) throws CouponSystemException {
		DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new CouponSystemException("Parse Exception **UtilDBDAO" + e.getMessage());
		}
	}
}
