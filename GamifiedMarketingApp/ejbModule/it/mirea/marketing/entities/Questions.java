package it.mirea.marketing.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="questions", schema="marketing")
@NamedQuery(name = "Questions.findByProductId", 
query = "SELECT q FROM Questions q WHERE q.productOTD_id = ?1")
public class Questions implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_id")
	private int questionId;
	
	@Lob
	private String text;
	
	private int productOTD_id;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="question")
	private List<Response> responses;
	
	@ManyToOne(/*fetch=FetchType.LAZY,*/cascade = CascadeType.ALL)
	@JoinColumn(name="productOTD_id", insertable=false, updatable=false)
	private ProductOfTheDay pOTD;
	
	public Questions() { }
	
	public Questions(int questionId, String text, ProductOfTheDay pOTD,
			List<Response> responses) {
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
	
	public int getPOTDId() {
		return this.productOTD_id;
	}
	
	public List<Response> getResponses() {
		return this.responses;
	}
		
	public void setText(String text) {
		this.text = text;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setPOTDId(int id) {
		this.productOTD_id = id;
	}
	
	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
	
}
