package com.angelo.coupons.webservices;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponceCode {

	private int responseCode;
	private String message;
	
	public ResponceCode(){
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
