package it.mirea.marketing.entities;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="product", schema="marketing")
@NamedQueries( {
	@NamedQuery(name = "Product.findByName", 
			query = "SELECT p FROM Product p  WHERE p.productName = ?1"),
	@NamedQuery(name = "Product.findAll",
			query = "SELECT p FROM Product p")
})
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private int productId;
	
	private String linkImage;
	
	@Column(name="product_name")
	private String productName;
	
	@Lob 
	@Basic(fetch=FetchType.LAZY)
	private byte[] image;
		
	// product is a field. This Collection is on the inverse 
	// side of the relation
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "product",cascade = CascadeType.REMOVE)
	@OrderBy("productOTD ASC")
	private List<ProductOfTheDay> pOTD;
	
	public Product() { }
	
	public Product(int productId, String link, String productName, byte[] image) {
		this.productId = productId;
		this.productName = productName;
		this.linkImage = link;
		this.image = image;
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
	
	public String getImage() {
		return Base64.getMimeEncoder().encodeToString(this.image);
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
	
	public void setImage(byte[] im) {
		this.image = im;
	}
	
}
