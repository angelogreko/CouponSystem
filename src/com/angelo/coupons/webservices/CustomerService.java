package com.angelo.coupons.webservices;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.facades.CustomerFacade;

@Path("/CustomerService")
public class CustomerService {
	@Context
	HttpServletRequest request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String inService() {
		return "CustomerService";
	}

	@POST
	@Path("purchaseCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException {
		System.out.println("purchaseCoupon:" + coupon.toString());
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			System.out.println(customerFacade);
			Coupon createdCoupon = new Coupon();
			createdCoupon = customerFacade.purchaseCoupon(coupon);
			return createdCoupon;
		}
		return null;
	}

	@GET
	@Path("getAllPurchasedCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCoupon() throws CouponSystemException {
		System.out.println("getAllPurchasedCoupon");
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			System.out.println(customerFacade);
			coupons = customerFacade.getAllPurchasedCoupon();
			return coupons;
		}

		return null;
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("getPurchasedCouponsByPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getPurchasedCouponsByPrice(@QueryParam("maxPrice") Double maxPrice) throws CouponSystemException {
		System.out.println("getPurchasedCouponsByPrice");
		System.out.println(maxPrice);
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			System.out.println(customerFacade);
			coupons = customerFacade.getAllPurchasedCouponByPrice(maxPrice);
			return coupons;
		}
		return null;
	}

	@SuppressWarnings("finally")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("getPurchasedCouponsByCouponType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getPurchasedCouponsByCouponType(@QueryParam("couponType") CouponType couponType) {
		System.out.println("getPurchasedCouponsByCouponType");
		System.out.println(couponType);
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			System.out.println(customerFacade);
			try {
				coupons = customerFacade.getAllPurchasedCouponByType(couponType);
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
	@Path("getAvailableCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAvailableCoupons() {
		System.out.println("getAvailableCoupons");
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			System.out.println(customerFacade);
			try {
				coupons = customerFacade.getAvailableCoupons();
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
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("getAvailableCouponsByPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAvailableCouponsByPrice(@QueryParam("maxPrice") Double maxPrice) {
		System.out.println("getAvailableCouponsByPrice");
		System.out.println(maxPrice);
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			System.out.println(customerFacade);
			try {
				coupons = customerFacade.getAvailableCouponsByPrice(maxPrice);
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
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("getAvailableCouponsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAvailableCouponsByType(@QueryParam("couponType") CouponType couponType) {
		System.out.println("getAvailableCouponsByType");
		System.out.println(couponType);
		if (request.getSession().getAttribute("facade") != null) {
			CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("facade");
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			System.out.println(customerFacade);
			try {
				coupons = customerFacade.getAvailableCouponsByType(couponType);
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} finally {
				return coupons;
			}
		}
		return null;
	}
}
