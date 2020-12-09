package it.mirea.marketing.entities;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name="productotd", schema="marketing")
@NamedQuery(name = "ProductOfTheDay.findByDate", query = "SELECT p FROM ProductOfTheDay p WHERE p.productOTD = ?1")
public class ProductOfTheDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="productOTD_id")
	private int productOfTheDayId;
	
	@Column(name="productOTD_date")
	private Date productOTD;
	
	// FK
	@Column(name="product_id")
	private int productId;

	@OneToMany(mappedBy="pOTD")	
	private Collection<StatisticalResponse> response;
	
	//@OneToMany(mappedBy="pOTD")
	//private Set<Questions> questions;
	
	@ManyToOne
	@JoinColumn(name="product_id", insertable=false, updatable=false)
	private Product product;
	
	public ProductOfTheDay() { }
	
	public ProductOfTheDay(int productOfTheDayId, Date productOTD, int productId
			/*Set<StatisticalResponse> response*/ /*Set<Questions> questions*/) {
		this.productOfTheDayId = productOfTheDayId;
		this.productOTD = productOTD;
		this.productId = productId;
		//this.response = response;
		//this.questions = questions;
	}
	
	public ProductOfTheDay(int productOfTheDayId, Date productOTD, int productId, Product product) {
		this.productOfTheDayId = productOfTheDayId;
		this.productOTD = productOTD;
		this.productId = productId;
		this.product = product;
	}
	
	public int getProductOfTheDayId() {
		return this.productOfTheDayId;
	}
	
	public Date getProductOTD() {
		return this.productOTD;
	}
	
	public int getProductId() {
		return this.productId;
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	//public Set<StatisticalResponse> getResponses() {
	//	return this.response;
	//}
	
//	public Set<Questions> getQuestions() {
	//	return this.questions;
	//}
	
	public void setProductOfTheDay(int productOfTheDayId) {
		this.productOfTheDayId = productOfTheDayId;
	}
	
	public void setProductOTD(Date productOTD) {
		this.productOTD = productOTD;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public void setProduct(Product prod) {
		this.product = prod;
	}
	
	//public void setResponse(Set<StatisticalResponse> response) {
	//	this.response = response;
	//}
	
	//public void setQuestion(Set<Questions> questions) {
	//	this.questions = questions;
	//}
	
}
