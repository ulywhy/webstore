package hello;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import model.Product;

@RepositoryRestResource(path = "rest")
public interface ProductRepository extends MongoRepository<Product, String> {

	Product findByNameIgnoreCase(String name);

	Product findByName(String name);

	List<Product> findByNameLike(String name);

	List<Product> findByNameLikeIgnoreCase(String name);

	Product findOneById(String id);

	@Override
	Optional<Product> findById(String id);

	@Override
	List<Product> findAll();

}