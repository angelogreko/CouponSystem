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

import com.angelo.coupons.facades.AdminFacade;
import com.angelo.coupons.facades.CompanyFacade;
import com.angelo.coupons.facades.CustomerFacade;
import com.angelo.coupons.utils.CouponSystem;

@Path("/MyClassService")
public class Rest {
	
	@Context
	HttpServletRequest request;
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.TEXT_PLAIN)
	public ResponseCode login(@FormParam("name") String userName, @FormParam("password") String password, @FormParam("clientType") String role ){
		ResponseCode loginResponseCode = new ResponseCode();
		boolean loginSuccess = false;
		System.out.println("boo");
		try {
			switch (role) {
			case "admin":
				String legitAdminUser = CouponSystem.getAdminname();
				String legitAdminPass = CouponSystem.getAdminpassword();
				System.out.println("permitted Credentials for admin:" + legitAdminUser + ", " + legitAdminPass );

				if (userName.equals(legitAdminUser) && password.equals(legitAdminPass)){
					enterSystem = CouponSystem.getInstance();
					AdminFacade adminInstance = (AdminFacade)(enterSystem.login(userName, password, "admin"));
					request.getSession().setAttribute("facade", adminInstance);
					request.getSession().setAttribute("loginFlag", "active");
					loginSuccess = true;
				}
				else {
					loginSuccess = false;
				}
				break;
			case "company":
				if (companyDBDAO.login(userName, password)){
					enterSystem = CouponSystem.getInstance();
					CompanyFacade companyInstance = (CompanyFacade)(enterSystem.login(userName, password, "company"));
					request.getSession().setAttribute("facade", companyInstance);
					request.getSession().setAttribute("loginFlag", "active");
					loginSuccess = true;
				}
				else {
					loginSuccess = false;
				}
				break;
			case "customer":
				if (customerDBDAO.login(userName, password)){
					loginSuccess = true;
					enterSystem = CouponSystem.getInstance();
					CustomerFacade customerInstance = (CustomerFacade)(enterSystem.login(userName, password, "customer"));
					request.getSession().setAttribute("facade", customerInstance);
					request.getSession().setAttribute("loginFlag", "active");
				}
				else {
					loginSuccess = false;
				}
				break;
			}
			if(loginSuccess){
				loginResponseCode.setResponseCode(1);
				loginResponseCode.setMessage("Login was sucessful");
			}
			else {
				loginResponseCode.setResponseCode(0);
				loginResponseCode.setMessage("Login failed");
			}
		} catch (DAOManagerException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ClientFacadeException e) {
			System.out.println("WTF");
		}


		return loginResponseCode;


	}
	
	@GET
	@Path("seeTeacher")
	@Produces(MediaType.APPLICATION_JSON)
	public Teacher seeTeacher(){
		System.out.println("hi");
		Teacher ethan = new Teacher();
		ethan.setName("Batman");
		ethan.setAge(28);
		ethan.setSubject("free");
		return ethan;
		
	}
	@GET
	@Path("seeSession")
	@Produces(MediaType.APPLICATION_JSON)
	public String seeSession(){
		if(request.getSession().getAttribute("Teacher") == null){
			Teacher ethan = new Teacher();
			ethan.setName("Batman");
			ethan.setAge(28);
			ethan.setSubject("free");
			request.getSession().setAttribute("Teacher", ethan);
			return "hello stranger";
		}
		else{
			Teacher stranger = (Teacher) request.getSession().getAttribute("Teacher");
			return "hi " + stranger.getName();
		}
	}

}
