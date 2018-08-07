package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

	@Override
	public String toString() {
		return items.toArray().toString();
	}

	@Id
	private String _id;
	private LocalDateTime date;
	private Set<Item> items;
	private double total;

	public Order() {
		this.items = new HashSet<Item>();
	}

	public void addItem(Item item) {
		if (this.items.contains(item)) {
			this.items.remove(item);
		}
		this.items.add(item);
		calculateTotal();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void calculateTotal() {
		total = 0;
		for (Item i : this.items) {
			total += i.getQuantity() * i.getProduct().getSalePrice();
		}
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
