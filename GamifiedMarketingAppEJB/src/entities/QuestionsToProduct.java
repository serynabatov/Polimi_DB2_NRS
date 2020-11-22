package entities;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="questions_to_product", schema="")
public class QuestionsToProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int responseId;
	
	private int userId;
	
	private int productOfTheDayId;
	
	private int statId;
	
	private int questionId;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
	private List<User> users;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "responseId")
	private List<Response> responses;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "statId")
	private List<StatisticalQuestions> statisticalQuestions;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "questionId")
	private List<Questions> questions;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "questionId")
	private List<ProductOfTheDay> pOTD;
	
	public QuestionsToProduct() { }

	public QuestionsToProduct(int id, int responseId, int userId, int productId, int statId, 
							  int questionId) {
		this.id = id;
		this.responseId = responseId;
		this.userId = userId;
		this.productOfTheDayId = productId;
		this.statId = statId;
		this.questionId = questionId;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getStatId() {
		return this.statId;
	}
	
	public int getProductOfTheDayId() {
		return this.productOfTheDayId;
	}
	
	public int getResponseId() {
		return this.responseId;
	}
	
	public int getQuestionId() {
		return this.questionId;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public List<User> getUsers() {
		return this.users;
	}
	
	public List<Response> getResponses() {
		return this.responses;
	}
	
	public List<StatisticalQuestions> getStatisticalQuestions() {
		return this.statisticalQuestions;
	}
	
	public List<Questions> getQuestions() {
		return this.questions;
	}
	
	public List<ProductOfTheDay> getProductsOfTheDay() {
		return this.pOTD;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setProductOfTheDayId(int productId) {
		this.productOfTheDayId = productId;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
	
	public void setStatId(int statId) {
		this.statId = statId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	// custom setting lists
}