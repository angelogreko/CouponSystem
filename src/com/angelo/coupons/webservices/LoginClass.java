package com.angelo.coupons.webservices;

import javax.xml.bind.annotation.XmlRootElement;

import com.angelo.coupons.enums.ClientType;

@XmlRootElement
public class LoginClass {
	private String name;
	private String password;
	private ClientType clienType;
	
	public LoginClass(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getClienType() {
		return clienType;
	}

	public void setClienType(ClientType clienType) {
		this.clienType = clienType;
	}
	
	
		
	

}
