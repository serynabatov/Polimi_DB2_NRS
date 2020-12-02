package services;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Product;

public class ProductService {

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;

	// method to search for the product when created product of the day
	public List<Product> searchByName(String productName) {
		// We suppose that it can be a lot of products with the same name
		List<Product> p = em.createNamedQuery("Product.findByName", Product.class)
			.setParameter(1, productName)
			.getResultList();
		if (p == null)
			return null;
		else
			return p;
	}
	
	
	
}
