package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.Item;
import model.Order;
import model.Product;

@Controller
@SessionAttributes("order")
public class AppController {

	@Autowired
	ProductRepository productRepository;

	@ModelAttribute("order")
	public Order setOrder() {
		return new Order();
	}

	@ModelAttribute("product")
	public Product product() {
		return new Product();
	}

	@GetMapping("/")
	public String greeting(@RequestParam(value = "id", required = false) String id,
			@ModelAttribute("order") Order order, Model model) {
		if (id == null) {
			return "start-page";
		}
		System.out.println("adding item to order");
		Product product = productRepository.findOneById(id);
		Item item = new Item(product);
		if (order.getItems().contains(item)) {
			model.addAttribute("updatedItem", item);
		} else {
			order.addItem(item);
		}
		return "start-page";
	}

	@PostMapping("/{id}")
	public String uptateOrder(@PathVariable("id") String id, @ModelAttribute("quantity") int quantity,
			@ModelAttribute("order") Order order, Model model) {
		if (id == null) {
			return "start-page";
		}
		System.out.println("updating item to order");
		Product product = productRepository.findOneById(id);
		Item item = new Item(product);
		if (order.getItems().contains(item)) {
			item.setQuantity(quantity);
			order.addItem(item);
			model.addAttribute("updatedItem", item);
		} else {
			order.addItem(item);
		}
		return "start-page";
	}

}
