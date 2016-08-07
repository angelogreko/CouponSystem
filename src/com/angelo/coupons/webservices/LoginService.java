package com.angelo.coupons.webservices;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.angelo.coupons.enums.ClientType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.interfaces.CouponClientFacade;
import com.angelo.coupons.utils.CouponSystem;

@Path("/LoginService")
public class LoginService {

	@Context
	HttpServletRequest request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String inService() {
		return "LoginService";
	}

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.TEXT_PLAIN)
	public String login(@FormParam("name") String name, @FormParam("password") String password,
			@FormParam("clientType") ClientType clientType) throws CouponSystemException {
		CouponSystem couponSystem = CouponSystem.getInstance();
		CouponClientFacade facade = couponSystem.login(name, password, clientType);
		if (facade != null) {
			request.getSession().setAttribute("facade", facade);
			return "logedIn";
		}
		return "notLogedIn";
	}
}
