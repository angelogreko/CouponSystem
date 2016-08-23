package com.angelo.coupons.webservices;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Customer;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.facades.AdminFacade;

@Path("/AdminService")
public class AdminService {

	@Context
	HttpServletRequest request;

	@SuppressWarnings("finally")
	@GET
	@Path("getAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() {
		if (request.getSession().getAttribute("facade") != null) {
			AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
			Collection<Company> companyArray = new ArrayList<Company>();
			try {
				companyArray = adminFacade.getAllCompanies();
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return companyArray;
			}
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers() {
		if (request.getSession().getAttribute("facade") != null) {
			AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
			Collection<Customer> customerArray = new ArrayList<Customer>();
			try {
				customerArray = adminFacade.getAllCustomers();
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return customerArray;
			}
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("createCompnay")
	@Produces(MediaType.APPLICATION_JSON)
	public Company createCompany(@QueryParam("compName") String compName, @QueryParam("password") String password,
			@QueryParam("email") String email) {

		Company company = new Company();
		if (request.getSession().getAttribute("facade") != null) {
			company.setCompName(compName);
			company.setPassword(password);
			company.setEmail(email);
			AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
			try {
				adminFacade.createCompany(company);
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return company;
			}
		}
		return company;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("createCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer createCustomer(@QueryParam("custName") String custName, @QueryParam("password") String password)
			throws CouponSystemException {

		Customer customer = new Customer();
		if (request.getSession().getAttribute("facade") != null) {
			customer.setCustName(custName);
			customer.setPassword(password);
			AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
			try {
				adminFacade.createCustomer(customer);
			} catch (Exception e) {
			} finally {
				return customer;
			}
		}
		return null;

	}

	@SuppressWarnings("finally")
	@Path("removeCompany")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> removeCompany(@QueryParam("id") long id) throws CouponSystemException {
		System.out.println("remove with id " + id);
		AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
		Company company = adminFacade.getCompany(id);
		Collection<Company> companyArray = adminFacade.getAllCompanies();
		try {
			adminFacade.removeCompany(company);
		} catch (Exception e) {
		} finally {
			return companyArray;

		}

	}

	@SuppressWarnings("finally")
	@Path("updateCompany")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> updateCompany(@QueryParam("id") long id, @QueryParam("password") String password,
			@QueryParam("email") String email) throws CouponSystemException {
		System.out.println("update with id " + id);
		AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
		Company company = adminFacade.getCompany(id);
		company.setPassword(password);
		company.setEmail(email);
		Collection<Company> companyArray = adminFacade.getAllCompanies();
		try {
			adminFacade.updateCompany(company);
		} catch (Exception e) {
		} finally {
			return companyArray;
		}
	}

	@SuppressWarnings("finally")
	@Path("updateCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> updateCustomer(@QueryParam("id") long id, @QueryParam("password") String password)
			throws CouponSystemException {
		System.out.println("update with id " + id);
		AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
		Customer customer = adminFacade.getCustomer(id);
		customer.setPassword(password);
		Collection<Customer> customerArray = adminFacade.getAllCustomers();
		try {
			adminFacade.updateCustomer(customer);
		} catch (Exception e) {
		} finally {
			return customerArray;
		}
	}

	@SuppressWarnings("finally")
	@Path("removeCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> removeCustomer(@QueryParam("id") long id) throws CouponSystemException {
		System.out.println("remove with id " + id);
		AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("facade");
		Customer customer = adminFacade.getCustomer(id);
		Collection<Customer> customerArray = adminFacade.getAllCustomers();
		try {
			adminFacade.removeCustomer(customer);
		} catch (Exception e) {
		} finally {
			return customerArray;
		}
	}
}
