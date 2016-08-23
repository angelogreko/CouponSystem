package com.angelo.coupons.enums;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * Client Type Class
 * Used during login for identification of type
 */
@XmlRootElement
public enum ClientType {
	ADMIN, COMPANY, CUSTOMER
}
