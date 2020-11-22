package entities;

import javax.persistence.*;

@Entity
@Table(name = "User", schema="")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private String userName;
	
	private String mail;
	
	private String password;
	
	private int points;
	
	private Boolean blocked;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private QuestionsToProduct questionsToProduct;
	
	public User() { }
		
	public User(String userName, String mail, String pass, 
				QuestionsToProduct questionsToProduct) {
		
		this.userName = userName;
		this.mail = mail;
		this.password = pass;
		this.questionsToProduct = questionsToProduct;
		this.blocked = Boolean.FALSE;
		this.points = 0;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Boolean getBlocked() {
		return this.blocked;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public QuestionsToProduct getQuestionsToProduct() {
		return this.questionsToProduct;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	public void setQuestionsToProduct(QuestionsToProduct questionsToProduct) {
		this.questionsToProduct = questionsToProduct;
	}
}
