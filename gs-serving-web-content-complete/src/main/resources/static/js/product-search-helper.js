	var host = "http://" + window.location.host;
	
	var findProductMatches = function() {
		console.log(window.location.host);
		var query = document.getElementById("productQuery").value;
		var uri =  host +"/rest/search/findByNameLikeIgnoreCase?name="
			+ query + "&size=20";
		console.log(uri);
		var req = new XMLHttpRequest();
		req.open("GET", uri, true);

		req.onreadystatechange = function() {
			if (this.readyState == 4) {
				var result = JSON.parse(this.responseText);
			  var div = document.getElementById("product-matches");

				var content = result._embedded.products
					.map(p => '<a class="dropdown-item" href="' + host + '/product/?name=' + p.name + '">' + p.name +'</a>')
					.reduce((outcome, value)=>{
						return outcome + value;
				});
				console.log("names: " + content);
				
				div.innerHTML = content; 
			}
		};
		req.send();
	}