package it.mirea.marketing.entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user", schema="marketing")
@NamedQueries( {
	@NamedQuery(name = "user.checkCredentials", 
			query = "SELECT r FROM User r  WHERE r.userName = ?1 and r.password = ?2"),
	@NamedQuery(name = "user.checkCredentialsMail",
			query = "SELECT r FROM User r WHERE r.mail = ?1"),
	@NamedQuery(name = "user.leaderboard",
			query = "SELECT r FROM User r WHERE r.role <> ?1 and r.blocked <> ?2 ORDER BY r.points DESC"),
	@NamedQuery(name = "user.leaderboardProduct",
				query = "SELECT r FROM User r WHERE r.role <> ?1 AND r.blocked <> ?2 ORDER BY r.dayPoints DESC")

})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	
	private String userName;
	
	private String mail;
	
	private String password;
	
	private int points;
	
	private Boolean blocked;
	
	private String role;
		
	@Column(name="day_points")
	private int dayPoints;
		
	@OneToMany(fetch=FetchType.EAGER, mappedBy="user", cascade = CascadeType.REMOVE)
	@OrderBy("responseDatetime DESC")
	private List<Response> responses;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="user", cascade = CascadeType.REMOVE)
	@OrderBy("statId ASC")
	private List<StatisticalResponse> statist;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="user")
	@OrderBy("loggedIn DESC")
	private List<LogInTime> logins;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="user")
	private List<Canceled> cancels;
	
	public User() { }
		
	public User(String userName, String mail, String pass) {
		
		this.userName = userName;
		this.mail = mail;
		this.password = pass;
		this.blocked = Boolean.FALSE;
		this.points = 0;
		this.responses = null;
		this.statist = null;
		this.logins = null;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Boolean getBlocked() {
		return this.blocked;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public String getRole() {
		return this.role;
	}

	public List<StatisticalResponse> getStat() {
		return this.statist;
	}
	
	public List<Response> getResponses() {
		return this.responses;
	}
	
	public List<LogInTime> getLogins() {
		return this.logins;
	}
	
	public List<Canceled> getCanceled() {
		return this.cancels;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setStat(List<StatisticalResponse> statist) {
		this.statist = statist;
	}
	
	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
	
	public void setLogins(List<LogInTime> logins) {
		this.logins = logins;
	}
	
	public void setCanceled(List<Canceled> canc) {
		this.cancels = canc;
	}
}
