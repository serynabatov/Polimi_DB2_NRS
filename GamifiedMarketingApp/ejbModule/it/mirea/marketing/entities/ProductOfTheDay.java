package it.mirea.marketing.entities;

import java.sql.Date;
import java.util.List;

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
	@OrderBy(value="responseDate DESC")
	private List<StatisticalResponse> statResponse;
	
	@OneToMany(mappedBy="pOTD")
	@OrderBy(value="responseDatetime DESC")
	private List<Response> response;
		
	@ManyToOne
	@JoinColumn(name="product_id", insertable=false, updatable=false)
	private Product product;
	
	public ProductOfTheDay() { }
	
	public ProductOfTheDay(int productOfTheDayId, Date productOTD, int productId,
			List<StatisticalResponse> statResponse, List<Response> response) {
		this.productOfTheDayId = productOfTheDayId;
		this.productOTD = productOTD;
		this.productId = productId;
		this.response = response;
		this.statResponse = statResponse;
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
	
}