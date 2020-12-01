package entities;

import javax.persistence.*;

@Entity
@Table(name="statistical_response", schema="")
public class StatisticalResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statId;
	
	private int age;
	
	private Boolean sex;
	
	private int expertLevel;
	
	//FK
	private int productOfTheDayId;
	
	//FK
	private int userId;
	
	@ManyToOne
	@JoinColumn(name="productOfTheDayId")
	private ProductOfTheDay pOTD;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	public StatisticalResponse() { }
	
	public StatisticalResponse(int statId, int age, Boolean sex, int expertLevel, 
				ProductOfTheDay pOTD, User user) {
		this.statId = statId;
		this.age = age;
		this.sex = sex;
		this.expertLevel = expertLevel;
		this.pOTD = pOTD;
		this.user = user;
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
	
	public int getUserId() {
		return this.userId;
	}
	
	public int gerProductOfTheDayId() {
		return this.productOfTheDayId;
	}
	
	public ProductOfTheDay getPOTD() {
		return this.pOTD;
	}
	
	public User getUser() {
		return this.user;
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
	
	public void setPOTD(ProductOfTheDay pOTD)  {
		this.pOTD = pOTD;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
		
	public void setProductOfTheDayId(int id) {
		this.productOfTheDayId = id;
	}
	
	public void setUserId(int id) {
		this.userId = id;
	}
}
