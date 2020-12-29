package it.mirea.marketing.entities;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="statistical_response", schema="marketing")
public class StatisticalResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="stat_id")
	private int statId;
	
	private int age;
	
	private Boolean sex;
	
	@Column(name="exp_level")
	private int expertLevel;
	
	//FK
	@Column(name="productOTD_id")
	private int productOfTheDayId;
	
	//FK
	@Column(name="user_id")
	private int userId;
	
	@Column(name="response_date")
	private Timestamp responseDate;
	
	@ManyToOne(/*fetch=FetchType.LAZY,*/cascade = CascadeType.PERSIST)
	@JoinColumn(name="productOTD_id", insertable=false, updatable=false)
	private ProductOfTheDay pOTD;
	
	@ManyToOne(/*fetch=FetchType.LAZY,*/cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	public StatisticalResponse() { }
	
	public StatisticalResponse(int statId, int age, Boolean sex, int expertLevel, 
				ProductOfTheDay pOTD, Timestamp responseDate, User user) {
		this.statId = statId;
		this.age = age;
		this.sex = sex;
		this.expertLevel = expertLevel;
		this.pOTD = pOTD;
		this.responseDate = responseDate;
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
	
	public Timestamp getResponseDate() {
		return this.responseDate;
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
	
	public void setReponseDate(Timestamp respo) {
		this.responseDate = respo;
	}
}
