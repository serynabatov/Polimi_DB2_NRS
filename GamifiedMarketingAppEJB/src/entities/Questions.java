package entities;

import javax.persistence.*;

@Entity
@Table(name="questions", schema="")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int questionId;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private QuestionsToProduct questionsToProduct;

	public Questions() { }
	
	public Questions(int questionId, String text, QuestionsToProduct questionsToProduct) {
		this.questionId = questionId;
		this.text = text;
		this.questionsToProduct = questionsToProduct;
	}
	
	public int getQuestionId() {
		return this.questionId;
	}
	
	public String getText() {
		return this.text;
	}
	
	public QuestionsToProduct getQuestionsToProduct() {
		return this.questionsToProduct;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setQuestionsToProduct(QuestionsToProduct questionsToProduct) {
		this.questionsToProduct = questionsToProduct;
	}

}
