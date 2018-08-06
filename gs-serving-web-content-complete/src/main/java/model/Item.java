package model;

public class Item implements Comparable<Item> {

	private Product product;
	private int quantity = 1;
	private double subTotal;

	public Item() {
	}

	public Item(Product product) {
		this.setProduct(product);
	}

	@Override
	public String toString() {
		return String.format("Item[quantity='%d', %s]", this.quantity, this.product);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getProduct().equals(((Item) obj).getProduct());
	}

	@Override
	public int hashCode() {
		return this.getProduct().hashCode();
	}

	@Override
	public int compareTo(Item item) {
		return this.getProduct().compareTo(item.getProduct());
	}

}
