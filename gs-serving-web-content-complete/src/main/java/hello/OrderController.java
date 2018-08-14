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
public class OrderController {

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

	@GetMapping("/order/print")
	public String greeting(@ModelAttribute("order") Order order, Model model) {
		if (order != null) {
			System.out.println("printing order");
			// release session
		}
		return "print-page";
	}

	@GetMapping("/")
	public String greeting(@RequestParam(value = "id", required = false) String id,
			@ModelAttribute("order") Order order, Model model) {
		return "start-page";
	}

	@GetMapping("/order")
	public String getOrder(@ModelAttribute("order") Order order, Model model) {
		return "order-view";
	}

	@GetMapping("/order/{id}")
	public String updateOrder(@PathVariable("id") String id, @ModelAttribute("order") Order order, Model model) {
		Product product = productRepository.findOneById(id);
		Item item = new Item(product);
		if (order.getItems().contains(item)) {
			order.addItem(item);
		} else {
			order.addItem(item);
		}
		model.addAttribute("updatedItem", item);

		System.out.println("updated: " + item);
		System.out.println(order);
		return "order-view";
	}

	@PostMapping("/order/{id}")
	public String uptateOrder(@PathVariable("id") String id, @ModelAttribute(value = "quantity") int quantity,
			@ModelAttribute("order") Order order, Model model) {
		Product product = productRepository.findOneById(id);
		Item item = new Item(product);
		if (order.getItems().contains(item)) {
			item.setQuantity(quantity);
			order.addItem(item);
			model.addAttribute("updatedItem", item);
		} else {
			order.addItem(item);
		}
		System.out.println("updated: " + item);
		System.out.println(order);
		return "order-view";
	}
}
