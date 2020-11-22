package entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="product_of_the_day", schema="")
public class ProductOfTheDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productOfTheDayId;
	
	private Date productOTD;
	
	private int productId;
	
	@ManyToOne
	@JoinColumn(name="productOfTheDayId")
	private QuestionsToProduct questionsToProduct;

	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;
	
	public ProductOfTheDay() { }
	
	public ProductOfTheDay(int productOfTheDayId, Date productOTD, int productId, 
				QuestionsToProduct questionsToProduct, Product product) {
		this.productOfTheDayId = productOfTheDayId;
		this.productOTD = productOTD;
		this.productId = productId;
		this.questionsToProduct = questionsToProduct;
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
	
	public QuestionsToProduct getQuestionsToProduct() {
		return this.questionsToProduct;
	}
	
	public Product getProduct() {
		return this.product;
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
	
	public void setQuestionsToProduct(QuestionsToProduct questionsToProduct) {
		this.questionsToProduct = questionsToProduct;
	}
	
	public void setProduct(Product prod) {
		this.product = prod;
	}
	
}
