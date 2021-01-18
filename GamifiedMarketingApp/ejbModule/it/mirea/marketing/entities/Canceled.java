package it.mirea.marketing.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "canceled", schema="marketing")
@NamedQueries({
	@NamedQuery(name = "canceled.getCancel", query = "SELECT c FROM Canceled c WHERE c.canceled = ?1 and c.pOTDDate = ?2"),
	@NamedQuery(name = "canceled.byUserId", query = "SELECT c FROM Canceled c WHERE c.userId = ?1 and c.pOTDDate = ?2")
})

public class Canceled implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_canceled")
	private int idCanceled;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="pOTD_date")
	private Date pOTDDate;
	
	private int canceled;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	public Canceled() { }
	
	public int getUserId() {
		return this.userId;
	}
	
	public int getCanceled() {
		return this.canceled;
	}
	
	public Date getDate() {
		return this.pOTDDate;
	}
	
	public int getCancelId() {
		return this.idCanceled;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}
	
	public void setDate(Date dat) {
		this.pOTDDate = dat;
	}
	
	public void setCanceledId(int id) {
		this.idCanceled = id;
	}
}
