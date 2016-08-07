package com.angelo.coupons.beans;

import java.util.Collection;

/*
 * Company Bean Class 
 * Including constructors,to string, getters and setters
 */

public class Company {

	private final long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	public Company(long id) {
		this.id = id;
	}

	public Company(long id, String compName, String password, String email) {
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
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
