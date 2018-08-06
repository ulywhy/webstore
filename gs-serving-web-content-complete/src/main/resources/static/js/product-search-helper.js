	var findProductMatches = function() {
		var query = document.getElementById("productQuery").value;
		var req = new XMLHttpRequest();
		req.open("GET",
				"http://localhost:8080/rest/search/findByNameLikeIgnoreCase?name="
						+ query + "&size=20", true);

		req.onreadystatechange = function() {
			if (this.readyState == 4) {
				var result = JSON.parse(this.responseText);
				  var  ul, li, a, i;
				  div = document.getElementById("product-matches");

				var content = result._embedded.products
					.map(p => '<a class="dropdown-item" href="http://localhost:8080/product/?name=' + p.name + '">' + p.name +'</a>')
					.reduce((outcome, value)=>{
						return outcome + value;
				});
				console.log("names: " + content);
				
				div.innerHTML = content; 
			}
		};
		req.send();
	}