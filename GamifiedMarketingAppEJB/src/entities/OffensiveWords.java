package entities;

import javax.persistence.*;

@Entity
@Table(name="offensive_words", schema="")
public class OffensiveWords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String words;
	
	public OffensiveWords() { }
	
	public OffensiveWords(int id, String words) {
		this.id = id;
		this.words = words;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getWords() {
		return this.words;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setWords(String words) {
		this.words = words;
	}
}
