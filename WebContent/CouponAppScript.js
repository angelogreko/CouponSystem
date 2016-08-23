//------------------------------------------------------------log in
function logIn() {
	var formField = getFormFields('loginForm');
	document.getElementById("loginbtn").disabled = true;
	sendFormLogin(formField);
}

function getFormFields(formName) {
	var formElements = document.getElementById(formName).elements;
	var paramString = '';
	for (var i = 0; i < formElements.length; i++) {
		paramString += formElements[i].name + '=' + formElements[i].value + '&';
	}
	return paramString;
}

function sendFormLogin(formFields) {
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("POST",
			'http://localhost:8080/angelocouponapp/rest/LoginService/login',
			true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processResponse(AJAX.response);
		}
	};
	AJAX.send(formFields);
}

function processResponse(jsonObj) {
	if (jsonObj != null) {
		jsonObj.name
		jsonObj.password

		jsonObj.clienType
		switch (jsonObj.clienType) {
		case "ADMIN":
			document.getElementById('loginleft').style.display = "none";
			document.getElementById('loginCenter').style.display = "none";
			document.getElementById('adminleft').style.display = "block";
			break;

		case "COMPANY":
			document.getElementById('loginleft').style.display = "none";
			document.getElementById('loginCenter').style.display = "none";
			document.getElementById('compleft').style.display = "block";
			document.getElementById('company_user_name').innerText = jsonObj.name;
			break;

		case "CUSTOMER":
			document.getElementById('loginleft').style.display = "none";
			document.getElementById('loginCenter').style.display = "none";
			document.getElementById('custleft').style.display = "block";
			document.getElementById('customer_user_name').innerText = jsonObj.name;
			break;
		}
	}
}

// -------------------------------------------------------------------Admin

function existingcomp() {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/getAllCompanies'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processExistingComp(AJAX.response);
		}
	};
	AJAX.send(null);
}

function processExistingComp(jsonObj) {
	document.getElementById('admincomp').style.display = "none";
	document.getElementById('admincust').style.display = "none";
	document.getElementById('tblcust').style.display = "none";
	var buff = '';
	if (jsonObj != null) {

		for (var i = 0; i < jsonObj.company.length; i++) {
			var itemComp = jsonObj.company[i];
			buff += '<tr>';
			buff += '<td>' + itemComp.id + '</td>';
			buff += '<td   >' + itemComp.compName + '</td>';
			buff += '<td  >' + itemComp.password + '</td>';
			buff += '<td  >' + itemComp.email + '</td>';

			buff += '<td><button onClick="removeCompany(' + itemComp.id
					+ ' )">Delete</button>' + '</td>';
			buff += '<td><button onClick="showUpdateComp(' + itemComp.id + ','
					+ itemComp.password + ',' + itemComp.email
					+ ')">Update</button></td>';

			buff += '</tr>';
		}
	} else {
		alert("No existing companies");
	}
	document.getElementById('tableCompany').innerHTML = buff;
	document.getElementById('tblcomp').style.display = "block";

}

function removeCompany(comp_id) {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/removeCompany?id='
			+ comp_id;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			existingcomp()
		}
	};
	AJAX.send(comp_id);

}

function showUpdateComp(id, password, email) {
	document.getElementById('admincompupd').style.display = "block";
	document.getElementById('updcompForm_id').value = id;
	document.getElementById('updcompForm_password').value = password;
	document.getElementById('updcompForm_email').value = email;
}

function updateCompany() {
	var formField = getFormFields('updcompForm');
	sendUpdateCompany(formField);
}

function sendUpdateCompany(formFields) {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/updateCompany?'
			+ formFields;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			existingcomp()
		}
	};
	AJAX.send();

}

function showcreatecomp() {
	document.getElementById('admincust').style.display = "none";
	document.getElementById('tblcomp').style.display = "none";
	document.getElementById('tblcust').style.display = "none";
	document.getElementById('admincomp').style.display = "block";
}

function createComp() {
	var formField = getFormFields('addcompForm');
	sendFormCreateComp(formField);
}

function sendFormCreateComp(formFields) {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/createCompnay?'
			+ formFields
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Company Successfully Added");
			document.getElementById('admincust').style.display = "none";
			document.getElementById('tblcust').style.display = "none";
			document.getElementById('admincomp').style.display = "none";
			existingcomp();
			document.getElementById('tblcomp').style.display = "block";
		}
	};
	AJAX.send(formFields);
}

function existingcust() {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/getAllCustomers'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processExistingCust(AJAX.response);
		}
	};
	AJAX.send(null);
}

function processExistingCust(jsonObj) {
	document.getElementById('admincust').style.display = "none";
	document.getElementById('tblcomp').style.display = "none";
	document.getElementById('admincomp').style.display = "none";
	if (jsonObj != null) {
		var buff = '';
		for (var i = 0; i < jsonObj.customer.length; i++) {
			var itemCust = jsonObj.customer[i];

			buff += '<tr>';
			buff += '<td>' + itemCust.id + '</td>';
			buff += '<td>' + itemCust.custName + '</td>';
			buff += '<td>' + itemCust.password + '</td>';

			buff += '<td><button onClick="removeCustomer(' + itemCust.id
					+ ' )">Delete</button>' + '</td>';
			buff += '<td><button onClick="showUpdateCust(' + itemCust.id + ','
					+ itemCust.password + ')">Update</button>' + '</td>';

			buff += '</tr>';
		}
		if (jsonObj == null) {
			alert("No Existing Customers")
		}
		document.getElementById('tableCustomer').innerHTML = buff;
		document.getElementById('tblcust').style.display = "block";
	}
}

function removeCustomer(id) {

	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/removeCustomer?id='
			+ id;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Customer Removed Successfully")
			existingcomp()
		}
	};
	AJAX.send(id);
}

function showUpdateCust(id, password) {
	document.getElementById('admincustupd').style.display = "block";
	document.getElementById('updcustForm_id').value = id;
	document.getElementById('updcustForm_password').value = password;
}

function updateCustomer() {
	var formField = getFormFields('updcustForm');
	sendUpdateCustomer(formField);
}

function sendUpdateCustomer(formFields) {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/updateCustomer?'
			+ formFields;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Customer Updated Successfully")
			existingcomp()
			document.getElementById('admincustupd').style.display = "none";
		}
	};
	AJAX.send();
}

function showcreatecust() {
	document.getElementById('tblcomp').style.display = 'none';
	document.getElementById('tblcust').style.display = 'none';
	document.getElementById('admincomp').style.display = 'none';
	document.getElementById('admincust').style.display = 'block';
}

function createCustomer() {
	var formField = getFormFields('addcustForm');
	sendFormCreatecust(formField);
}

function sendFormCreatecust(formFields) {
	var url = 'http://localhost:8080/angelocouponapp/rest/AdminService/createCustomer?'
			+ formFields

	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Customer Successfully Added")
			existingcust()
			document.getElementById('tblcust').style.display = 'none';
		}
	};
	AJAX.send(formFields);
}

// ------------------------------------------------------------------Company

function processCoup(jsonObj) {
	var buff = '';
	if (jsonObj != null) {

		for (var i = 0; i < jsonObj.coupon.length; i++) {
			var coupitem = jsonObj.coupon[i];
			buff += '<tr>';
			buff += '<td>' + coupitem.id + '</td>';
			buff += '<td>' + coupitem.title + '</td>';
			buff += '<td>' + coupitem.startDate + '</td>';
			buff += '<td>' + coupitem.endDate + '</td>';
			buff += '<td>' + coupitem.amount + '</td>';
			buff += '<td>' + coupitem.type + '</td>';
			buff += '<td>' + coupitem.message + '</td>';
			buff += '<td>' + coupitem.price + '</td>';
			buff += '<td>' + coupitem.image + '</td>';
new Date()
			buff += '<td id="removecoup" style="display: none;"><button onClick="removeCoupon('
					+ coupitem.id + ')">Delete</button>' + '</td>';
			buff += '<td id="updatecoup" style="display: none;"><button onClick="updateCoupon('
					+ coupitem.id
					+ ', '
					+ coupitem.endDate
					+ ', '
					+ coupitem.price
					+ ', '
					+ coupitem.amount
					+ '   )">Update</button>' + '</td>';
			buff += '<td id="purchasecoup" style="display: none;"><button onClick="purchaseCoupon('
					+ coupitem.id + ')">Purchase</button>' + '</td>';

			buff += '</tr>';
		}
	} else {
		alert("No Existing Coupons");
	}
	document.getElementById('tableCoupons').innerHTML = buff;
}

function existingcoup() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getAllCoupons'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblexist').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('removecoup').style.display = "block";
			document.getElementById('updatecoup').style.display = "block";
			document.getElementById('createcoup').style.display = "none";
			document.getElementById('selectprice').style.display = "none";
			document.getElementById('selectdate').style.display = "none";
			document.getElementById('selecttype').style.display = "none";
		}
	};
	AJAX.send(null);
}

function showcreateCoupon() {
	document.getElementById('createcoup').style.display = "block";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('selectprice').style.display = "none";
	document.getElementById('selectdate').style.display = "none";
	document.getElementById('selecttype').style.display = "none";
}

function createCouponForm() {
	var formField = getFormFields('addcoupForm');
	sendFormCreateCoupon(formField);
}

function sendFormCreateCoupon(formFields) {
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/createCoupon?'
			+ formFields
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			// processCouponResponse(AJAX.response);
			alert("Coupon Created Successfuly")
			existingcoup();
		}
	};
	AJAX.send(formFields);
}

// function processCouponResponse(jsonObj) {
// if (jsonObj != null) {
// alert("Coupon Created Successfuly")
// existingcoup()
// } else {
// alert("Coupon Was Not Created Successfuly")
// }
// }

function removeCoupon(id) {

	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/removeCoupon?id='
			+ id;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Coupon Removed Successfully")
			existingcoup()
		}
	};
	AJAX.send();
}

function showUpdateCoup(id, endDate, price, amount) {
	document.getElementById('admincustupd').style.display = "block";
	document.getElementById('coupForm_id').value = id;
	document.getElementById('coupForm_endDate').value = endDate;
	document.getElementById('coupForm_amount').value = amount;
	document.getElementById('coupForm_price').value = price;
}

function updateCoupon() {
	var formField = getFormFields('updcoupForm');
	sendUpdateCoupon(formField);
}

function sendUpdateCoupon(formFields) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/updateCoupon?'
			+ formFields;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Customer Updated Successfully")
			existingcomp()
			document.getElementById('admincustupd').style.display = "none";
		}
	};
	AJAX.send(null);
}
function showgetCouponsByType() {
	document.getElementById('createcoup').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('selectprice').style.display = "none";
	document.getElementById('selectdate').style.display = "none";
	document.getElementById('selecttype').style.display = "block";
}

function getCouponsByType() {
	var formField = getFormFields('typeForm');
	sendGetCouponsByType(formField);
}

function sendGetCouponsByType() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getCouponsByType'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tbltype').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('removecoup').style.display = "block";
			document.getElementById('updatecoup').style.display = "block";
		}
	};
	AJAX.send(null);
}

function showgetCouponsByPrice() {
	document.getElementById('createcoup').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('selectprice').style.display = "block";
	document.getElementById('selectdate').style.display = "none";
	document.getElementById('selecttype').style.display = "none";
}

function getCouponsByPrice() {
	var formField = getFormFields('priceForm');
	sendGetCouponsByPrice(formField);
}

function sendGetCouponsByPrice() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getCouponsByPrice'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblprice').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('removecoup').style.display = "block";
			document.getElementById('updatecoup').style.display = "block";
		}
	};
	AJAX.send(null);
}

function showgetCouponsByDate() {
	document.getElementById('selectdate').style.display = "block";
	document.getElementById('selecttype').style.display = "none";
	document.getElementById('selectprice').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('table').style.display = "none";
}

function getCouponsByDate() {
	var formField = getFormFields('dateForm');
	sendGetCouponsByDate(formField);
}

function sendGetCouponsByDate() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getCouponsByDate'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tbldate').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('removecoup').style.display = "block";
			document.getElementById('updatecoup').style.display = "block";
		}
	};
	AJAX.send(null);
}

// ---------------------------------------------------------Customer

function existingcoupcust() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getAllPurchasedCoupon'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblexist').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send(null);
}

function showgetCouponsByPriceCust() {
	document.getElementById('selectpricecust').style.display = "block";
	document.getElementById('selecttypecust').style.display = "none";
	document.getElementById('selecttypeavail').style.display = "none";
	document.getElementById('selectpriceavail').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('table').style.display = "none";
}

function getCouponsByPriceCust() {
	var formField = getFormFields('priceCustForm');
	sendGetCouponsByPriceCust(formField);
}
function sendGetCouponsByPriceCust() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getPurchasedCouponsByPrice'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblprice').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send(null);
}

function showgetCouponsByTypeCust() {
	document.getElementById('selecttypecust').style.display = "block";
	document.getElementById('selectpricecust').style.display = "none";
	document.getElementById('selecttypeavail').style.display = "none";
	document.getElementById('selectpriceavail').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('table').style.display = "none";

}
function getCouponsByTypeCust() {
	var formField = getFormFields('typeCustForm');
	sendGetCouponsByTypeCust(formField);
}
function sendGetCouponsByTypeCust() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getPurchasedCouponsByCouponType'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tbltype').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send(null);
}

function purchaseCoupon(id) {

	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/purchaseCoupon?id='
			+ id;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			alert("Coupon Purchased Successfully")
		}
	};
	AJAX.send();
}

function availableCoupons() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getAvailableCoupons'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblavilable').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('purchasecoup').style.display = "block";
			document.getElementById('selectpriceavail').style.display = "none";
			document.getElementById('selecttypeavail').style.display = "none";
			document.getElementById('selecttypecust').style.display = "none";
			document.getElementById('selectpricecust').style.display = "none";
		}
	};
	AJAX.send(null);
}

function showAvailCouponsByPrice() {
	document.getElementById('selectpriceavail').style.display = "block";
	document.getElementById('selecttypeavail').style.display = "none";
	document.getElementById('selecttypecust').style.display = "none";
	document.getElementById('selectpricecust').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('table').style.display = "none";

}
function availCouponsByPrice() {
	var formField = getFormFields('priceAvailForm');
	sendAvailCouponsByPrice(formField);
}
function sendAvailCouponsByPrice() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getAvailableCouponsByPrice'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblavilableprice').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('purchasecoup').style.display = "block";
		}
	};
	AJAX.send(null);
}

function showAvailableCouponsByType() {
	document.getElementById('selecttypeavail').style.display = "block";
	document.getElementById('selectpriceavail').style.display = "none";
	document.getElementById('selecttypecust').style.display = "none";
	document.getElementById('selectpricecust').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('table').style.display = "none";

}
function availCouponsByType() {
	var formField = getFormFields('typeAvailForm');
	sendAvailCouponsByType(formField);
}
function sendAvailCouponsByType() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getAvailableCouponsByType'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblavilabletype').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
			document.getElementById('purchasecoup').style.display = "block";
		}
	};
	AJAX.send(null);
}


//function ajaxCall(url,fun){
//	//Creare ajax and send with url got
//	// olace fun where he finish his job
//	alert(url);
//	fun();
//}
//
//function callback(){
//	// use data from server 
//	//like get companies to table
//	alert("I have been called");
//}
//
//
//function buttonPressed(){
//	// when link pressed (on click)
//	// like when user press get all companies
//	ajaxCall("im calling do something",callback );
//	
//	ajaxCall("im calling do something",function(){
//		alert("this is anonimus function ")
//	} );
//}