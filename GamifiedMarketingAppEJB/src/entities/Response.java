package entities;

import javax.persistence.*;

@Entity
@Table(name="Response", schema="")
public class Response {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int responseId;
	
	@Lob
	private String text;
	
	//FK
	private int questionId;
	
	private int userId;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private Questions question;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	public Response() { }
	
	public Response(int id, String text, Questions question, User user) {
		this.responseId = id;
		this.text = text;
		this.question = question;
		this.user = user;
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
	
	public int questionId() {
		return this.questionId;
	}
}
