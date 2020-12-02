package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Product;
import entities.ProductOfTheDay;


public class ProductOfTheDayService {

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public ProductOfTheDayService() { }
	
	// get the list of the products and choose from them
	public Boolean createProductOfTheDayAsProduct(int productOfTheDayId, Date productOTD, 
			Product p) {
		
		if (checkForTheDate(productOTD)) {
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD(productOTD);
			pOTD.setProductId(p.getProductId());
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}
	
	private Boolean checkForTheDate(Date productOTD) {
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", 
													  ProductOfTheDay.class)
				  .setParameter(1, productOTD)
				  .getResultList();
		if (p.size() == 0)
			return true;
		else
			return false;
	}
	
	// return the product of the day for today's date
	public ProductOfTheDay todayProductOfTheDay() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", 
											           ProductOfTheDay.class)
							  .setParameter(1, dtf.format(now))
							  .getResultList();
		
		if (p.size() == 0)
			return p.get(0);
		else
			return null;
	}
	
	//create product of the day and then as a product (maybe do as a trigger)
	public Boolean createProductOfTheDayThenProduct(int productOfTheDayId, Date productOTD,
			int productId, String productName, String linkImage, Byte[] image) {
		if (checkForTheDate(productOTD)) {
			Product p = new Product();
			p.setProductId(productId);
			p.setProductName(productName);
			p.setImage(image);
			p.setLinkImage(linkImage);
			em.persist(p);
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD(productOTD);
			pOTD.setProductId(productId);
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean createProductOfTheDayThenProduct(int productOfTheDayId, Date productOTD,
			int productId, String productName, Byte[] image) {
		if (checkForTheDate(productOTD)) {
			Product p = new Product();
			p.setProductId(productId);
			p.setProductName(productName);
			p.setImage(image);
			em.persist(p);
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD(productOTD);
			pOTD.setProductId(productId);
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}

	public Boolean createProductOfTheDayThenProduct(int productOfTheDayId, Date productOTD,
			int productId, String productName, String linkImage) {
		if (checkForTheDate(productOTD)) {
			Product p = new Product();
			p.setProductId(productId);
			p.setProductName(productName);
			p.setLinkImage(linkImage);
			em.persist(p);
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD(productOTD);
			pOTD.setProductId(productId);
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}

	// TODO: return the name, the image and product reviews for it
	
}
