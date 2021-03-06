package it.mirea.marketing.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.entities.Questions;

@Stateless
public class QuestionsService {

	@PersistenceContext(unitName = "GamifiedMarketingApp")
	private EntityManager em;

	public QuestionsService() { }


	public void createQuestions(List<String> questions, ProductOfTheDay p) {
		for (String text : questions) {
			Questions q = new Questions();

			q.setPOTDId(p.getProductOfTheDayId());
			q.setText(text);
			em.persist(q);
		}
	}
	
	public List<Questions> getQuestionsForProductOfTheDay(int pOTDId) {
		
		List<Questions> questions = em.createNamedQuery("Questions.findByProductId", Questions.class)
							  .setParameter(1, pOTDId)
							  .getResultList();
		
		return questions;
	}

}
