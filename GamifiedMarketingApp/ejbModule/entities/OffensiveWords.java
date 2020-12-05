package entities;

import javax.persistence.*;

@Entity
@Table(name="offensive_words", schema="marketing")
public class OffensiveWords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String word;
	
	public OffensiveWords() { }
	
	public OffensiveWords(int id, String words) {
		this.id = id;
		this.word = words;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getWords() {
		return this.word;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setWords(String words) {
		this.word = words;
	}
}
