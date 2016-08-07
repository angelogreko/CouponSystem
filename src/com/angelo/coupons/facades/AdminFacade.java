package com.angelo.coupons.facades;

import java.util.Collection;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Customer;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CouponClientFacade;
import com.angelo.coupons.managers.CompanyDBDAO;
import com.angelo.coupons.managers.CustomerDBDAO;

/*
 * AdminFacade... Main page of Administrator 
 * Controlling functions of companies and customers 
 */

public class AdminFacade implements CouponClientFacade {

	public CompanyDBDAO companyDBDAO;
	public CustomerDBDAO customerDBDAO;

	public AdminFacade()throws CouponSystemException {
		customerDBDAO = new CustomerDBDAO();
		companyDBDAO = new CompanyDBDAO();
	}

	/**
	 * Creating a new Company & Checking for duplications in company names.
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void createCompany(Company company) throws CouponSystemException{
		companyDBDAO.createCompany(company);
	}

	/**
	 * Deleting a Company and all Coupons it owns from coupon and customer Data
	 * Base.
	 *
	 * @param company
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void removeCompany(Company company) throws CouponSystemException{
		companyDBDAO.removeCompany(company);
	}

	/**
	 * Updating data of a company
	 * 
	 * @param company
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException{
		companyDBDAO.updateCompany(company);
	}

	/**
	 * Getting a company by ID
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Company getCompany(long id) throws CouponSystemException{
		return companyDBDAO.getCompany(id);
	}

	/**
	 * Getting all companies in an Array
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException{
		return companyDBDAO.getAllCompanies();
	}

	/**
	 * Creating a new Customer & Checking for duplications in customer names
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void createCustomer(Customer customer) throws CouponSystemException{
		customerDBDAO.createCustomer(customer);
	}

	/**
	 * Deleting customer and his coupons
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException{
		customerDBDAO.removeCustomer(customer);
	}

	/**
	 * Updating customer
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException{
		customerDBDAO.updateCustomer(customer);
	}

	/**
	 * Getting customer by id
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(long id) throws CouponSystemException{
		return customerDBDAO.getCustomer(id);
	}

	/**
	 * Getting all customers
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException{
		return customerDBDAO.getAllCustomers();
	}

}
