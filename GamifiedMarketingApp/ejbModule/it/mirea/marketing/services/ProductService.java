package it.mirea.marketing.services;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.mirea.marketing.entities.Product;

@Stateless
public class ProductService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
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
