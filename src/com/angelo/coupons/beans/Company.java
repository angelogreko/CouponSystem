package com.angelo.coupons.beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

/*
 * Company Bean Class 
 * Including constructors,to string, getters and setters
 */

public class Company {

	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	public Company() {
		coupons = new ArrayList<Coupon>();
	}
	
	public Company(long id) {
		this.id = id;
		coupons=new ArrayList<Coupon>();
	}

	public Company(long id, String compName, String password, String email) {
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
		coupons=new ArrayList<Coupon>();
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String sCoupons = ", coupons : NO Coupons";
		if (coupons.size() > 0) {
			sCoupons = "\n\tcoupons :";
			for (Coupon coupon : coupons) {
				sCoupons += "\n\t" + coupon;
			}
		}
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + sCoupons
				+ "]";
	}

}
