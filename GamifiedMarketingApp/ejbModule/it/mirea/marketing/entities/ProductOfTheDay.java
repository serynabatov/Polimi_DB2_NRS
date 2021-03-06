package it.mirea.marketing.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Cache;

@Entity
@Table(name="productotd", schema="marketing")
@NamedQueries({
	@NamedQuery(name = "ProductOfTheDay.findByDate", query = "SELECT p FROM ProductOfTheDay p WHERE p.productOTD = ?1"),
	@NamedQuery(name = "ProductOfTheDay.findNotPOTD", query = "SELECT p FROM ProductOfTheDay p WHERE p.productOTD <> ?1"),
	@NamedQuery(name = "ProductOfTheDay.inspection", query = "SELECT p FROM ProductOfTheDay p WHERE p.productOTD < ?1")
})
public class ProductOfTheDay implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="productOTD_id")
	private int productOfTheDayId;
	
	@Column(name="productOTD_date")
	private Date productOTD;
	
	// FK
	@Column(name="product_id")
	private int productId;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="pOTD",cascade = CascadeType.REMOVE)
	@OrderBy(value="responseDate DESC")
	private List<StatisticalResponse> statResponse;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="pOTD", cascade = CascadeType.REMOVE)
	@OrderBy(value="responseDatetime DESC")
	private List<Response> response;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="pOTD",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy(value="questionId DESC")
	private List<Questions> questions;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_id", insertable=false, updatable=false)
	private Product product;
	
	public ProductOfTheDay() { }
	
	public ProductOfTheDay(int productOfTheDayId, Date productOTD, int productId,
			List<StatisticalResponse> statResponse, List<Response> response, List<Questions> questions) {
		this.productOfTheDayId = productOfTheDayId;
		this.productOTD = productOTD;
		this.productId = productId;
		this.response = response;
		this.statResponse = statResponse;
		this.questions = questions;
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
	
	public List<StatisticalResponse> getStatResponses() {
			return this.statResponse;
	}
	
	public List<Response> getResponses() {
		return this.response;
	}
	
	public List<Questions> getQuestions() {
		return this.questions;
	}
	
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
	
	public void setStatResponse(List<StatisticalResponse> response) {
			this.statResponse = response;
	}
	
	public void setResponse(List<Response> response) {
		this.response = response;
	}
	
	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}
	
}
