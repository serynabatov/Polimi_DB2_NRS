package it.mirea.marketing.entities;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="time_logged_in", schema="marketing")
public class LogInTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int idLog;
	
	// FK
	@Column(name="user_id")
	private int userId;
	
	@Column(name="logged_in")
	private Timestamp loggedIn;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	public LogInTime() { }
	
	public LogInTime(int userId, Timestamp loggedIn, int idLog) {
		this.userId = userId;
		this.loggedIn = loggedIn;
		this.idLog = idLog;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public Timestamp getLogged() {
		return this.loggedIn;
	}
	
	public int getIdLog() {
		return this.idLog;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setLogg(Timestamp loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}