package com.angelo.coupons.beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
/*
 * Customer Bean Class 
 * Including constructors, to string, getters and setters
 */

public class Customer {

	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
		coupons=new ArrayList<Coupon>();
	}

	public Customer(long id) {
		this.id = id;
		coupons=new ArrayList<Coupon>();
	}
	
	public Customer(long id, String custName, String password) {
		this.id = id;
		this.custName = custName;
		this.password = password;
		coupons=new ArrayList<Coupon>();
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
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
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + sCoupons + "]";
	}
}
