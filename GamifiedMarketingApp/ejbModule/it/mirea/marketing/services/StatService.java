//package it.mirea.marketing.services;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import it.mirea.marketing.entities.ProductOfTheDay;
//import it.mirea.marketing.entities.StatisticalResponse;
//
//public class StatService {
//
//	@PersistenceContext(unitName = "GamifiedMarketingApp")
//	private EntityManager em;
//
//	public Map<Boolean, List<Integer, Integer>> getAllStatAnswers(ProductOfTheDay p) {
//		
//		List<StatisticalResponse> statList =  p.getStatResponses();
//		List<String> uText = null;
//		
//		for (StatisticalResponse statist : statList) {
//			uText.add(statist)
//		}
//	}
//	
//}
