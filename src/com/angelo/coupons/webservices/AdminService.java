package com.angelo.coupons.webservices;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.facades.CompanyFacade;
import com.angelo.coupons.managers.CompanyDBDAO;

@Path("/AdminService")
public class AdminService {
	@Context
	HttpServletRequest request;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String inService(){
		return "AdminService";
	}
	
	@GET
	@Path("getCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("id") long id) throws CouponSystemException{
		request.getSession().getAttribute("facade");
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company company = companyDBDAO.getCompany(id);
		return company;
	}
	
	
	@GET
	@Path("getCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@QueryParam("id") long id) throws CouponSystemException{
		request.getSession().getAttribute("facade");
		CompanyFacade companyFacade = new CompanyFacade("", "");
		Coupon coupon = companyFacade.getCoupon(id);
		return coupon;
	}
}
