package entities;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="time_logged_in", schema="marketing")
public class LogInTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLog;
	
	// FK
	private int userId;
	
	private Date loggedIn;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	public LogInTime() { }
	
	public LogInTime(int userId, Date loggedIn, int idLog) {
		this.userId = userId;
		this.loggedIn = loggedIn;
		this.idLog = idLog;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public Date getLogged() {
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
	
	public void setLogg(Date loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
