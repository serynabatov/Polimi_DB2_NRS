package entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "User", schema="")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private String userName;
	
	private String mail;
	
	private String password;
	
	private int points;
	
	private Boolean blocked;
		
	@OneToMany(mappedBy="question")
	@OrderBy("name ASC")
	private List<Response> responses;
	
	@OneToMany(mappedBy="user")
	@OrderBy("name ASC")
	private List<StatisticalResponse> statist;
	
	public User() { }
		
	public User(String userName, String mail, String pass, 
				List<Response> responses, List<StatisticalResponse> statist) {
		
		this.userName = userName;
		this.mail = mail;
		this.password = pass;
		this.blocked = Boolean.FALSE;
		this.points = 0;
		this.responses = null;
		this.statist = null;
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
	
	public List<StatisticalResponse> getStat() {
		return this.statist;
	}
	
	public List<Response> getResponses() {
		return this.responses;
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
	
	public void setStat(List<StatisticalResponse> statist) {
		this.statist = statist;
	}
	
	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
}
