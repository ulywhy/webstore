package hello;

import java.util.List;

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

	@Autowired
	ProductRepository productRepository;

	@ModelAttribute("product")
	public Product product() {
		return new Product();
	}

	@GetMapping("/product")
	public String productByName(@RequestParam(value = "name", required = false) String name, Model model) {
		if (name != null) {
			Product productMatch = findByName(name);
			if (productMatch != null) {
				System.out.println("productMatch" + productMatch);
				model.addAttribute("product", productMatch);
			} else {
				List<Product> productMatches = findLikeName(name);
				model.addAttribute("products", productMatches);
			}
			model.addAttribute("productState", "view");
		} else {
			System.out.println("creating new product");
			model.addAttribute("product", new Product());
			model.addAttribute("productState", "create");
		}
		return "product-view";
	}

	@GetMapping("/product/{id}")
	public String productForm(@PathVariable("id") String id, Model model) {
		Product actualProduct = findProductById(id);
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
		} else {
			System.out.println(product);
			Product actualProduct = findByName(product.getName());
			// the product name changes on update
			if (!actualProduct.equals(product)) {
				Product matchProduct = findByName(product.getName());
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
		Product insertedProduct = productRepository.save(product);
		model.addAttribute("product", insertedProduct);
		model.addAttribute("productState", "view");
	}

	private Product findProductById(String id) {
		return productRepository.findOneById(id);
	}

	private Product findByName(String name) {
		return productRepository.findByNameIgnoreCase(name);
	}

	private List<Product> findLikeName(String name) {
		return productRepository.findByNameLikeIgnoreCase(name);
	}

}
