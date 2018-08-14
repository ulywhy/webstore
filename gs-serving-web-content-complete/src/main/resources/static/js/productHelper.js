var productMatchId;
var host = "http://" + window.location.host;

var productExist = function() {
	var query = document.getElementById("product-name-input").value;
	console.log("checking for: " + query);
	var req = new XMLHttpRequest();
	req.open("GET", host + "/rest/search/findByNameLikeIgnoreCase?name="
					+ query, true);

	req.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById("load-product-button").classList
			.replace("visible", "invisible");
			var result = JSON.parse(this.responseText);
			var product = result._embedded.products[0];
			console.log(product);
			if (product != null
					&& product.name.toLowerCase().localeCompare(
							query.toLowerCase()) == 0) {
				productMatchId = product.id;
				document.getElementById("load-product-button").classList
						.replace("invisible", "visible");
			}
		}
	};
	req.send();
}
var loadProduct = function() {
	console.log("redirecting");
	window.location.replace(host + "/product/"
			+ productMatchId);
}