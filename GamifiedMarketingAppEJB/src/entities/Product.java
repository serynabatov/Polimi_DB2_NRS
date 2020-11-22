package entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="product", schema="")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	private String linkImage;
	
	private int productOfTheDayId;
	
	private String productName;
	
	@Lob
	private Byte[] image;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productId")
	private List<ProductOfTheDay> pOTD;

	public Product() { }
	
	public Product(int productId, int productOfTheDayId, String link, String productName, 
			Byte[] image) {
		this.productId = productId;
		this.productName = productName;
		this.linkImage = link;
		this.productOfTheDayId = productOfTheDayId;	
	}
	
	public int getProductId() {
		return this.productId;
	}
	
	public String getLinkImage() {
		return this.linkImage;
	}

	public int getProductOfTheDayId() {
		return this.productOfTheDayId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public Byte[] getImage() {
		return this.image;
	}
	
	public List<ProductOfTheDay> getPOTD() {
		return this.pOTD;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
		
	public void setLinkImage(String link) {
		this.linkImage = link;
	}
	
	public void setProductOfTheDayId(int productOfTheDayId) {
		this.productOfTheDayId = productOfTheDayId;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setImage(Byte[] im) {
		this.image = im;
	}
}
