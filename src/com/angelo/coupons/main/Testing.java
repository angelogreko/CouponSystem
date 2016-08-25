package com.angelo.coupons.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.angelo.coupons.beans.Company;
import com.angelo.coupons.beans.Coupon;
import com.angelo.coupons.beans.Customer;
import com.angelo.coupons.enums.ClientType;
import com.angelo.coupons.enums.CouponType;
import com.angelo.coupons.exceptions.CouponSystemException;
import com.angelo.coupons.facades.AdminFacade;
import com.angelo.coupons.facades.CompanyFacade;
import com.angelo.coupons.facades.CustomerFacade;
import com.angelo.coupons.interfaces.CouponClientFacade;
import com.angelo.coupons.managers.CouponDBDAO;
import com.angelo.coupons.utils.CouponSystem;

public class Testing {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static void printListCompanies(Collection<Company> list) {
		for (Company c : list) {
			System.out.println(c.toString());
		}
	}
	
	private static void printListCustomers(Collection<Customer> list) {
		for (Customer c : list) {
			System.out.println(c.toString());
		}
	}

	private static void checkAdminFacade() {
		try {
			CouponSystem sc = CouponSystem.getInstance();
			CouponClientFacade client = sc.login("admin", "1234", ClientType.ADMIN);
			AdminFacade admin = (AdminFacade) client;

			System.out.println("\n************ Start company check **************");
			printListCompanies(admin.getAllCompanies());

			Company company = new Company(0);
			company.setCompName("Cisco1");
			company.setPassword("123456");
			company.setEmail("a@cisco.com");

			admin.createCompany(company);

			System.out.println("\n************ After create Company **************");
			printListCompanies(admin.getAllCompanies());

			Collection<Company> companies = admin.getAllCompanies();
			Iterator<Company> iterator = companies.iterator();
			while (iterator.hasNext())
				company = iterator.next();

			company.setEmail("some@test");
			admin.updateCompany(company);

			System.out.println("\n************ After update Company " + company.getId() + "**************");
			printListCompanies(admin.getAllCompanies());

			companies = admin.getAllCompanies();
			iterator = companies.iterator();
			while (iterator.hasNext())
				company = iterator.next();

			admin.removeCompany(company);
			System.out.println("\n************ After remove Company " + company.getId() + "**************");
			printListCompanies(admin.getAllCompanies());

			System.out.println("\n************ Start customer check **************");
			printListCustomers(admin.getAllCustomers());

			Customer customer = new Customer(0);
			customer.setCustName("Bill Gates2");
			customer.setPassword("123456");
			admin.createCustomer(customer);

			System.out.println("\n************ After create Customer **************");
			printListCustomers(admin.getAllCustomers());

			Collection<Customer> customers = admin.getAllCustomers();
			Iterator<Customer> iteratorCust = customers.iterator();
			while (iteratorCust.hasNext())
				customer = iteratorCust.next();

			customer.setPassword("*******");
			admin.updateCustomer(customer);

			System.out.println("\n************ After update Customer **************");
			printListCustomers(admin.getAllCustomers());

			customers = admin.getAllCustomers();
			iteratorCust = customers.iterator();
			while (iteratorCust.hasNext())
				customer = iteratorCust.next();

			admin.removeCustomer(customer);

			System.out.println("\n************ After remove Customer **************");
			printListCustomers(admin.getAllCustomers());

		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	private static void checkCompanyFacade() throws ParseException {
		try {
			CouponSystem sc = CouponSystem.getInstance();
			CouponClientFacade client = sc.login("aaa", "aaa", ClientType.COMPANY);
			CompanyFacade company = (CompanyFacade) client;

			System.out.println("\n************ Start Company Facade check **************");
			System.out.println(company.toString());

			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			Date startDate = df.parse("2017-01-24 00:00:00");
			Date endDate = df.parse("2017-02-24 00:00:00");

			Coupon coupon = new Coupon(0);
			coupon.setTitle("Pokemon GO Exploring");
			coupon.setAmount(30);
			coupon.setEndDate(endDate);
			coupon.setStartDate(startDate);
			coupon.setImage("Pikatchu.png");
			coupon.setMessage("Explore Pokemon GO");
			coupon.setPrice(2752.17);
			coupon.setType(CouponType.SPORTS);
			company.createCoupon(coupon);

			System.out.println("\n************ After create coupon **************");
			System.out.println(company.toString());

			Collection<Coupon> coupons = company.getAllCoupons();
			Iterator<Coupon> iterator = coupons.iterator();
			while (iterator.hasNext()) {
				coupon = iterator.next();
			}

			coupon.setPrice(88858.4);
			company.updateCoupon(coupon);

			System.out.println("\n************ After update coupon " + coupon.getId() + "**************");
			System.out.println(company.toString());

			coupons = company.getAllCoupons();
			iterator = coupons.iterator();
			while (iterator.hasNext()) {
				coupon = iterator.next();
			}

			company.removeCoupon(coupon);
			System.out.println("\n************ After Delete coupon " + coupon.getId() + "**************");
			System.out.println(company.toString());

		} catch (CouponSystemException e) {
			e.printStackTrace();
		} 
	}

	private static void checkCustomerFacade() {

		try {
			CouponDBDAO couponDB = new CouponDBDAO();
			CouponSystem sc = CouponSystem.getInstance();
			CouponClientFacade client = sc.login("aa", "aa", ClientType.CUSTOMER);
			CustomerFacade customer = (CustomerFacade) client;

			System.out.println("\n************ Start Customer Facade check **************");
			System.out.println(customer.toString());

			Coupon coupon = new Coupon(0);
			Collection<Coupon> coupons = couponDB.getAllCoupons();
			Iterator<Coupon> iteratorComp = coupons.iterator();
			while (iteratorComp.hasNext())
				coupon = iteratorComp.next();

//			customer.purchaseCoupon(coupon);

			System.out.println("\n************ After purchase coupon id 12**************");
			System.out.println(customer.toString());

		} catch (CouponSystemException e) {
			e.printStackTrace();
		} 

	}

	public static void checkThreadActivity() throws CouponSystemException, InterruptedException {
		try {
			CouponSystem sc = CouponSystem.getInstance();
			Thread.sleep(300000);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		} 
	}


	public static void main(String[] args) throws ParseException {

		 checkAdminFacade();
		 checkCompanyFacade();
		 checkCustomerFacade();

		
	}

}
