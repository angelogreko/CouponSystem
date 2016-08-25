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
	@Path("logout")
	@Produces(MediaType.TEXT_PLAIN)
	public String logout() {
		System.out.println("logout");
		return "LoginService";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String inService() {
		return "LoginService";
	}

	@SuppressWarnings("finally")
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public LoginClass login(@FormParam("name") String name, @FormParam("password") String password,
			@FormParam("clientType") ClientType clientType) {
		// System.out.println("in loginservice");
		CouponSystem couponSystem;
		System.out.println(name+","+password+","+clientType);
		CouponClientFacade facade = null;
		LoginClass loginClass = new LoginClass();
		try {
			couponSystem = CouponSystem.getInstance();
			facade = couponSystem.login(name, password, clientType);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally");
			System.out.println("facade"+facade);
			if (facade != null) {
				request.getSession().setAttribute("facade", facade);
				loginClass.setName(name);
				loginClass.setPassword(password);
				loginClass.setClienType(clientType);
			}
			System.out.println("loginClass"+loginClass);
			return loginClass;
		}
	}

	

}
