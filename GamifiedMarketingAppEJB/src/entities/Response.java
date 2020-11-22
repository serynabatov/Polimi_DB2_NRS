package entities;

import javax.persistence.*;

@Entity
@Table(name="Response", schema="")
public class Response {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int responseId;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name="responseId")
	private QuestionsToProduct questionsToProduct;
	
	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
	
	public void setText(String text) {
		if (text.length() > 200)
			System.out.println("SHIIT");
		else
			this.text = text;
	}
	
	public void setQuestionsToProduct(QuestionsToProduct questionsToProduct) {
		this.questionsToProduct = questionsToProduct;
	}
	
	public int getResponseId() {
		return this.responseId;
	}
	
	public QuestionsToProduct getQuestionsToProduct() {
		return this.questionsToProduct;
	}
	
	public String getText() {
		return this.text;
	}
}
