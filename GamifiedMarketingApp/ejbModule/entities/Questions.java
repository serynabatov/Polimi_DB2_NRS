package entities;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="questions", schema="marketing")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int questionId;
	
	@Lob
	private String text;
	
	// FK
	private int pOTDId;
	
	@ManyToOne
	@JoinColumn(name="pOTDId")
	private ProductOfTheDay pOTD;
	
	@OneToMany(mappedBy="question")
	private Set<Response> responses;
	
	public Questions() { }
	
	public Questions(int questionId, String text, ProductOfTheDay pOTD,
			Set<Response> responses) {
		this.questionId = questionId;
		this.text = text;
		this.pOTD = pOTD;
		this.responses = responses;
	}
	
	public int getQuestionId() {
		return this.questionId;
	}
	
	public String getText() {
		return this.text;
	}
	
	public ProductOfTheDay getPOTD() {
		return this.pOTD;
	}
	
	public Set<Response> getResponses() {
		return this.responses;
	}
	
	public int getPOTDId() {
		return this.pOTDId;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setPOTD(ProductOfTheDay pOTD) {
		this.pOTD = pOTD;
	}

	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}
	
	public void setPOTDId(int id) {
		this.pOTDId = id;
	}
}
