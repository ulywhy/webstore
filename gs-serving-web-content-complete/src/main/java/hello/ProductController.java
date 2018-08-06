package hello;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Product;

@Controller
public class ProductController {

	Product matchProduct, insertedProduct, actualProduct;
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/product")
	public String productByName(@RequestParam(value = "name", required = false) String name, Model model) {
		if (name != null) {
			model.addAttribute("product", productRepository.findByName(name));
			model.addAttribute("productState", "view");
		} else {
			model.addAttribute("product", new Product());
			model.addAttribute("productState", "create");
		}
		return "product-view";
	}

	@GetMapping("/product/{id}")
	public String productForm(@PathVariable("id") String id, Model model) {
		actualProduct = productRepository.findById(id).get();
		if (actualProduct != null) {
			System.out.println("### found product id: " + actualProduct.getId());
			model.addAttribute("product", actualProduct);
			model.addAttribute("productState", "edit");
		} else {
			model.addAttribute("product", new Product());
			model.addAttribute("productState", "create");
		}
		return "product-view";
	}

	@PostMapping("/product")
	public String productInsert(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("product", "create");
		} else if (product.getId() == null) {
			insertProduct(product, model);
		} else {
			Product actualProduct = productRepository.findOneById(product.getId());
			// the product name changes on update
			if (!actualProduct.getName().equals(product.getName())) {
				Product matchProduct = productRepository.findByNameIgnoreCase(product.getName());
				// edit name matching product instead
				if (matchProduct != null) {
					model.addAttribute("product", matchProduct);
					model.addAttribute("productState", "edit");
				} else {
					insertProduct(product, model);
				}
			} else {
				updateProduct(product, model);
			}
		}
		return "product-view";
	}

	private void updateProduct(Product product, Model model) {
		System.out.println("### updating product: " + product);
		Product insertedProduct = productRepository.save(product);
		model.addAttribute("product", insertedProduct);
		model.addAttribute("productState", "view");
	}

	private void insertProduct(Product product, Model model) {
		System.out.println("### inserting new product: " + product);
		insertedProduct = productRepository.save(product);
		model.addAttribute("product", insertedProduct);
		model.addAttribute("productState", "view");
	}

}
