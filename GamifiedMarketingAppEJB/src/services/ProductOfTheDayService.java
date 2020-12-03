package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Product;
import entities.ProductOfTheDay;
import entities.Questions;
import entities.Response;


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
	private ProductOfTheDay todayProductOfTheDay() {
		LocalDate now = LocalDate.now();
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", 
											           ProductOfTheDay.class)
							  .setParameter(1, convertToDateViaSqlDate(now))
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
	
	private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	
	// return the name, the image, review
	// not good method, maybe redo it in database
	public Map<Integer, List<Object>> getNameImageReview() {
		LocalDate now = LocalDate.now();
		if (checkForTheDate(convertToDateViaSqlDate(now))) {
			ProductOfTheDay p = todayProductOfTheDay();
			if (p == null) {
				return null;
			} else {
				List<Object> li = new ArrayList<Object>();
				Product prod = p.getProduct();
				
				li.add(prod.getProductName());
	
				if (prod.getLinkImage() == null)
					li.add(prod.getImage());
				else
					li.add(prod.getLinkImage());
				
				//List<String> questions = new ArrayList<String>();
				Set<Questions> q = p.getQuestions();
				Map<String, Response> reviews = new HashMap<String, Response>();
				
				for (Questions question : q) {
					//question.getText();
					Set<Response> resp = question.getResponses();
				}
			}
		} else {
			return null;
		}
	}
	
}
