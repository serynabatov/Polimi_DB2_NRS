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
	
	private String productName;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private Byte[] image;
	
	// product is a field. This Collection is on the inverse 
	// side of the relation
	@OneToMany(mappedBy = "product")
	@OrderBy("name ASC")
	private List<ProductOfTheDay> pOTD;

	public Product() { }
	
	public Product(int productId, String link, String productName, Byte[] image) {
		this.productId = productId;
		this.productName = productName;
		this.linkImage = link;
	}
	
	public int getProductId() {
		return this.productId;
	}
	
	public String getLinkImage() {
		return this.linkImage;
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
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setImage(Byte[] im) {
		this.image = im;
	}
}
