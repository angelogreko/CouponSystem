package com.angelo.coupons.utils;

import com.angelo.coupons.enums.ClientType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.facades.AdminFacade;
import com.angelo.coupons.facades.CompanyFacade;
import com.angelo.coupons.facades.CustomerFacade;
import com.angelo.coupons.interfaces.CouponClientFacade;
import com.angelo.coupons.managers.CompanyDBDAO;
import com.angelo.coupons.managers.CustomerDBDAO;

/*
 * Main Class of program
 * Controls Log In, Expiration Coupon Task, Instances and ShutDown 
 */

public class CouponSystem {

	private static CouponSystem instance;
	
	private ConnectionPool pool;
	private ExpirationManager couponExpiritionTask = new ExpirationManager();
	private Thread thread;

	/**
	 * Getting a thread to activate expiration task
	 * 
	 * @throws CouponSystemException
	 */
	public CouponSystem() throws CouponSystemException {
		thread = new Thread(couponExpiritionTask);
		thread.start();
		pool = ConnectionPool.getInstance();
	}

	/**
	 * Initialization of instances
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null)
			instance = new CouponSystem();

		return instance;
	}

	/**
	 * Logs In and Switches between clients according to type
	 * 
	 * @param name
	 * @param password
	 * @param type
	 * @return CouponClientFacade for Administrator, Company, Customer
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public CouponClientFacade login(String name, String password,
			ClientType clientType) throws CouponSystemException{
		switch (clientType) {
		case ADMIN:
			if (name == "admin" && password == "1234")
				return (CouponClientFacade) new AdminFacade();
			else
				throw new CouponSystemException("User Not Found Exception: "
						+ clientType + ": name=" + name + ", password="
						+ password);
		case COMPANY:
			CompanyDBDAO companyDBDAO = new CompanyDBDAO();
			if (companyDBDAO.login(name, password))
				return (CouponClientFacade) new CompanyFacade(name, password); 
			else
				throw new CouponSystemException("User Not Found Exception: "
						+ clientType + ": name=" + name + ", password="
						+ password);
		case CUSTOMER:
			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
			if (customerDBDAO.login(name, password))
				return (CouponClientFacade) new CustomerFacade(name, password);
			else
				throw new CouponSystemException("User Not Found Exception: "
						+ clientType + ": name=" + name + ", password="
						+ password);
		default:
			throw new CouponSystemException("Wrong clientType");
		}
	}

	/**
	 * ShutDown task
	 * 
	 * @throws CouponSystemException
	 */
	public void shutDown() throws CouponSystemException {
		couponExpiritionTask.stopTask();
		pool.closeAllConnections();
	}
}
