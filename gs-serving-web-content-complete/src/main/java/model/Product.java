package model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product implements Comparable<Product> {

	@Id
	private String id;
	@Size(min = 2, message = "minimum size of name is 4 characters")
	@Indexed(unique = true)
	private String name;
	@NotNull(message = "price is required")
	@Min(value = (long) 0.0, message = "prices must be positive values")
	private Double salePrice;
	private Double buyPrice;
	private String description;

	public Product() {
	}

	public Product(String id, String name, double price) {
		this(name, price);
		this.id = id;
	}

	public Product(String name, double price) {
		this.name = name;
		this.salePrice = price;
	}

	@Override
	public String toString() {
		return String.format("Product[id='%s', name='%s', price='%f'.desciption='%s']", id, name, salePrice,
				description);
	}

	@Override
	public boolean equals(Object obj) {
		return this.getId().equals(((Product) obj).getId());
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double price) {
		this.salePrice = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Override
	public int compareTo(Product product) {
		return this.getName().compareTo(product.getName());
	}

}
