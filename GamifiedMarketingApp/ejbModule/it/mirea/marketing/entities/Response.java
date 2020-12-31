package it.mirea.marketing.entities;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="response", schema="marketing")
public class Response {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="response_id")
	private int responseId;
	
	@Lob
	private String text;
	
	//FK
	@Column(name="question_id")
	private int questionId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="response_datetime")
	private Timestamp responseDatetime;
	
	@Column(name="productOTD_id")
	private int productOfTheDayId;
	
	@ManyToOne(/*fetch=FetchType.LAZY,*/cascade = CascadeType.PERSIST)
	@JoinColumn(name="question_id", insertable=false, updatable=false)
	private Questions question;
	
	@ManyToOne(/*fetch=FetchType.LAZY,*/cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	@ManyToOne(/*fetch=FetchType.LAZY,*/cascade = CascadeType.PERSIST)
	@JoinColumn(name="productOTD_id", insertable=false, updatable=false)
	private ProductOfTheDay pOTD;
	
	public Response() { }
	
	public Response(int id, String text, Questions question, User user, Timestamp response) {
		this.responseId = id;
		this.text = text;
		this.question = question;
		this.user = user;
		this.responseDatetime = response;
	}
	
	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
	
	public void setText(String text) {
		if (text.length() > 200)
			System.out.println("Not good");
		else
			this.text = text;
	}
	
	public void setQuestion(Questions question) {
		this.question = question;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setPOTDId(int id) {
		this.productOfTheDayId = id;
	}
	
	public void setResponseDT(Timestamp response) {
		this.responseDatetime = response;
	}
	
	public void setProductOfTheDay(ProductOfTheDay pOTD) {
		this.pOTD = pOTD;
	}
	
	public int getResponseId() {
		return this.responseId;
	}
		
	public String getText() {
		return this.text;
	}
	
	public Questions getQuestion() {
		return this.question;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public int getQuestionId() {
		return this.questionId;
	}
	
	public int getPOTDId() {
		return this.productOfTheDayId;
	}
	
	public Timestamp getResponseDT() {
		return this.responseDatetime;
	}
	
	public ProductOfTheDay getProductOfTheDay() {
		return this.pOTD;
	}
}
