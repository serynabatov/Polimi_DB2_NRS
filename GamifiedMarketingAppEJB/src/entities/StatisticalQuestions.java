package entities;

import javax.persistence.*;

@Entity
@Table(name="statistical_questions", schema="")
public class StatisticalQuestions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statId;
	
	private int age;
	
	private Boolean sex;
	
	private int expertLevel;
	
	@ManyToOne
	@JoinColumn(name="statId")
	private QuestionsToProduct questionsToProduct;
	
	public StatisticalQuestions() { }
	
	public StatisticalQuestions(int statId, int age, Boolean sex, int expertLevel, 
								QuestionsToProduct questionsToProduct) {
		this.statId = statId;
		this.age = age;
		this.sex = sex;
		this.expertLevel = expertLevel;
		this.questionsToProduct = questionsToProduct;
	}
	
	public int getStatId() {
		return this.statId;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public Boolean getSex() {
		return this.sex;
	}
	
	public int getExpertLevel() {
		return this.expertLevel;
	}
	
	public QuestionsToProduct getQuestionsToProduct() {
		return this.questionsToProduct;
	}
	
	public void setStatId(int statId) {
		this.statId = statId;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	
	public void setExpertLevel(int expertLevel) {
		this.expertLevel = expertLevel;
	}
	
	public void setQuestionsToProduct(QuestionsToProduct questionsToProduct) {
		this.questionsToProduct = questionsToProduct;
	}
	
}
