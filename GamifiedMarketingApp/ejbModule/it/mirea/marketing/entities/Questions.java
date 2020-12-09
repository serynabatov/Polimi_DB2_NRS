package it.mirea.marketing.entities;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="questions", schema="marketing")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_id")
	private int questionId;
	
	@Lob
	private String text;
			
	@OneToMany(mappedBy="question")
	private Set<Response> responses;
	
	
	public Questions() { }
	
	public Questions(int questionId, String text, ProductOfTheDay pOTD,
			Set<Response> responses) {
		this.questionId = questionId;
		this.text = text;
		this.responses = responses;
	}
	
	public int getQuestionId() {
		return this.questionId;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Set<Response> getResponses() {
		return this.responses;
	}
		
	public void setText(String text) {
		this.text = text;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}
	
}
