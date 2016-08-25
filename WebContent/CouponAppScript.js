//------------------------------------------------------------log in
function logIn() {
	var formField = getFormFields('loginForm');
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
alert(jsonObj);
	if (jsonObj != null && jsonObj.name != undefined) {
		document.getElementById('statlogin').innerHtml = "Loading...";
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
	} else {
		alert("error");
		document.getElementById('statlogin').innerHtml = "Wrong Information... Please Log In Again";
	}
}

function clearPage() {
	document.getElementById('admincomp').style.display = "none";
	document.getElementById('admincust').style.display = "none";
	document.getElementById('tblcust').style.display = "none";
	document.getElementById('tblhead').style.display = "none";
	document.getElementById('tblcomp').style.display = "none";
	document.getElementById('admincomp').style.display = "none";
	document.getElementById('admincompupd').style.display = "none";
	document.getElementById('admincustupd').style.display = "none";
	document.getElementById('selecttype').style.display = "none";
	document.getElementById('selectdate').style.display = "none";
	document.getElementById('selectprice').style.display = "none";
	document.getElementById('createcoup').style.display = "none";
	document.getElementById('selecttypecust').style.display = "none";
	document.getElementById('selectpricecust').style.display = "none";
	document.getElementById('selecttypeavail').style.display = "none";
	document.getElementById('selectpriceavail').style.display = "none";
	document.getElementById('tableCoupons').style.display = "none";
	document.getElementById('tblexist').style.display = "none";
	document.getElementById('tbltype').style.display = "none";
	document.getElementById('tbldate').style.display = "none";
	document.getElementById('tblprice').style.display = "none";
	document.getElementById('tblavilable').style.display = "none";
	document.getElementById('tblavilabletype').style.display = "none";
	document.getElementById('tblavilableprice').style.display = "none";

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
		document.getElementById('tblcomp').style.display = "none";
	}
	document.getElementById('tableCompany').innerHTML = buff;
	clearPage()
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
	clearPage();
	document.getElementById('admincompupd').style.display = "block";
	document.getElementById('updcompForm_id').value = id;
	document.getElementById('updcompForm_password').value = password;
	document.getElementById('updcompForm_email').value = email;
}

function updateCompany(id) {
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
	clearPage();
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
			clearPage();
			existingcomp();
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
		clearPage();
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
	clearPage();
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
			clearPage();
			existingcomp()
		}
	};
	AJAX.send();
}

function showcreatecust() {
	clearPage();
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
			clearPage();
			existingcust()
		}
	};
	AJAX.send(formFields);
}

// ------------------------------------------------------------------Company



function processCoup(jsonObj, isNotCust, purshace) {
	var buff = '';
	if (jsonObj != null) {
		var buff = '';
		buff += '<table id=couponTabl><tr><th>Id</th><th>Title</th><th>Type</th><th>Amount</th><th>Start Date</th><th>End Date</th><th>Message</th><th>Price</th><th>Image</th>';
		if (isNotCust) {
			buff += '<th></th>';
		}

		buff += '</tr>';
		var coupons;
		if (jsonObj != null) {
			if (jsonObj.coupon.length) {
				coupons = jsonObj.coupon;
			} else {
				coupons = [];
				coupons.push(jsonObj.coupon);

			}
			for (var i = 0; i < coupons.length; i++) {
				var coupitem = coupons[i];
				buff += '<tr>';
				buff += '<td>' + coupitem.id + '</td>';
				buff += '<td>' + coupitem.title + '</td>';
				buff += '<td>' + coupitem.type + '</td>';
				buff += '<td>' + coupitem.amount + '</td>';
				buff += '<td>'
						+ new Date(coupitem.startDate).toLocaleDateString()
						+ '</td>';
				buff += '<td>'
						+ new Date(coupitem.endDate).toLocaleDateString()
						+ '</td>';
				buff += '<td>' + coupitem.message + '</td>';
				buff += '<td>' + coupitem.price + '</td>';
				buff += '<td>' + coupitem.image + '</td>';
				if (isNotCust) {
					buff += '<td><div class="updatecoup"><button onClick="updateCoupon('
							+ coupitem.id
							+ ', '
							+ coupitem.endDate
							+ ', '
							+ coupitem.price
							+ ', '
							+ coupitem.amount
							+ ' )">Update</button></div></td><td></td><td><div class="removecoup"><button onClick="removeCoupon('
							+ coupitem.id + ')">Delete</button></div></td>';
				}else if(purshace){
					buff += '<td><div class="purchasecoup"><button onClick="purchaseCoupon('
							+ coupitem.id + ')">Purchase</button></div></td>';
				}
			}
			buff += '</tr>';
		}

		document.getElementById('tableCoupons').innerHTML = buff;
	}
}

function existingcoup() {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getAllCoupons'
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response, true, null);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblexist').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send(null);
}

function showcreateCoupon() {
	clearPage();
	document.getElementById('createcoup').style.display = "block";
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
			clearPage();
			existingcoup();
		}
	};
	AJAX.send(formFields);
}

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
			clearPage();
			existingcoup()
		}
	};
	AJAX.send(id);
}

function showUpdateCoup(id, endDate, price, amount) {
	clearPage();
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
			clearPage();
			existingcomp()
		}
	};
	AJAX.send();
}
function showgetCouponsByType() {
	clearPage();
	document.getElementById('selecttype').style.display = "block";
}

function getCouponsByType() {
	var formField = getFormFields('typeForm');
	sendGetCouponsByType(formField);
}

function sendGetCouponsByType(formFields) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getCouponsByType?'
			+ formFields;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response, true, null);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tbltype').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}

function showgetCouponsByPrice() {
	clearPage();
	document.getElementById('selectprice').style.display = "block";
}

function getCouponsByPrice() {
	var formField = getFormFields('priceForm');
	sendGetCouponsByPrice(formField);
}

function sendGetCouponsByPrice(formField) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getCouponsByPrice?'
			+ formField;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response,true, null);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblprice').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}

function showgetCouponsByDate() {
	clearPage();
	document.getElementById('selectdate').style.display = "block";
}

function getCouponsByDate() {
	var formField = getFormFields('dateForm');
	sendGetCouponsByDate(formField);
}

function sendGetCouponsByDate(formField) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CompanyService/getCouponsByDate?'
			+ formField;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response, true, null);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tbldate').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
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
			processCoup(AJAX.response, false, false);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblexist').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}

function showgetCouponsByPriceCust() {
	clearPage();
	document.getElementById('selectpricecust').style.display = "block";
}

function getCouponsByPriceCust() {
	var formField = getFormFields('priceCustForm');
	sendGetCouponsByPriceCust(formField);
}
function sendGetCouponsByPriceCust(formField) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getPurchasedCouponsByPrice?'
			+ formField;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response,false, false);
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblprice').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}

function showgetCouponsByTypeCust() {
	clearPage();
	document.getElementById('selecttypecust').style.display = "block";
}
function getCouponsByTypeCust() {
	var formField = getFormFields('typeCustForm');
	sendGetCouponsByTypeCust(formField);
}
function sendGetCouponsByTypeCust(formField) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getPurchasedCouponsByCouponType?'
			+ formField;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response,false,false);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tbltype').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
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
			clearPage();
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
			processCoup(AJAX.response,false,true);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblavilable').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}

function showAvailableCouponsByPrice() {
	clearPage();
	document.getElementById('selectpriceavail').style.display = "block";

}
function availCouponsByPrice(formField) {
	var formField = getFormFields('priceAvailForm');
	sendAvailCouponsByPrice(formField);
}
function sendAvailCouponsByPrice(formField) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getAvailableCouponsByPrice?'
			+ formField
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response,false,true);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblavilableprice').style.display = "block";
			document.getElementById('table').style.display = "block";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}

function showAvailableCouponsByType() {
	clearPage();
	document.getElementById('selecttypeavail').style.display = "block";
}

function availCouponsByType() {
	var formField = getFormFields('typeAvailForm');
	sendAvailCouponsByType(formField);
}

function sendAvailCouponsByType(formField) {
	var url = 'http://localhost:8080/angelocouponapp/rest/CustomerService/getAvailableCouponsByType?'
			+ formField;
	var AJAX = new XMLHttpRequest();
	AJAX.responseType = "json";
	AJAX.open("GET", url, true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function() {
		if (AJAX.readyState == 4 && AJAX.status == 200) {
			processCoup(AJAX.response,false,true);
			clearPage();
			document.getElementById('tblhead').style.display = "block";
			document.getElementById('tblavilabletype').style.display = "block";
			document.getElementById('table').style.display = "table";
			document.getElementById('tableCoupons').style.display = "block";
		}
	};
	AJAX.send();
}
