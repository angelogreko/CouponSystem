package com.angelo.coupons.webservices;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.facades.CompanyFacade;
import com.angelo.coupons.utils.UtilDBDAO;

@Path("/CompanyService")
public class CompanyService {

	@Context
	HttpServletRequest request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String inService() {
		return "CompanyService";
	}
	
	@SuppressWarnings("finally")
	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		if (request.getSession().getAttribute("facade") != null) {
			CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			try {
				coupons = companyFacade.getAllCoupons();
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return coupons;
			}
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("createCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon createCoupon(@QueryParam("title") String title, @QueryParam("type") CouponType type,
			@QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
			@QueryParam("amount") int amount, @QueryParam("message") String message, @QueryParam("price") Double price,
			@QueryParam("image") String image) {
		System.out.println("createCoupon");
		Coupon coupon = new Coupon(0, title, startDate, endDate, amount, type, message, price, image);
		if (request.getSession().getAttribute("facade") != null) {
			CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
			Coupon createdCoupon = new Coupon();
			try {
				createdCoupon = companyFacade.createCoupon(coupon);
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return createdCoupon;
			}
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Coupon> updateCoupon(@QueryParam("id") long id) throws CouponSystemException {
		CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
		Coupon coupon = companyFacade.getCoupon(id);
		Collection<Coupon> couponArray = companyFacade.getAllCoupons();
		try {
			companyFacade.updateCoupon(coupon);
		} catch (Exception e) {
		} finally {
			return couponArray;
		}
	}

	@SuppressWarnings("finally")
	@GET
	@Path("removeCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> removeCoupon(@QueryParam("id") long id) throws CouponSystemException {
		CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
		Coupon coupon = companyFacade.getCoupon(id);
		Collection<Coupon> couponArray = companyFacade.getAllCoupons();
		try {
			companyFacade.removeCoupon(coupon);
		} catch (Exception e) {
		} finally {
			return couponArray;
		}
	}

	@SuppressWarnings("finally")
	@GET
	@Path("getCouponsByType")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("type") CouponType type)
			throws CouponSystemException {
		if (request.getSession().getAttribute("facade") != null) {
			CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			try {
				coupons = companyFacade.getCouponsByType(type);
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return coupons;
			}
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("getCouponsByPrice")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("coupprice") Double maxPrice) throws CouponSystemException {
		if (request.getSession().getAttribute("facade") != null) {
			CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			// System.out.println(companyFacade);
			try {
				coupons = companyFacade.getCouponsByPrice(maxPrice);
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return coupons;
			}
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("getCouponsByDate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("endDate") String endDate) throws CouponSystemException {
		if (request.getSession().getAttribute("facade") != null) {
			CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			try {
				coupons = companyFacade.getCouponsByDate(UtilDBDAO.getDate(endDate));
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return coupons;
			}
		}
		return null;
	}
}
