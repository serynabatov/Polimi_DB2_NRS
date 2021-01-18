package it.mirea.marketing.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="offensive_words", schema="marketing")
@NamedQuery(name="OffensiveWords.findAll", query="SELECT o from OffensiveWords o")
public class OffensiveWords implements Serializable{

	private static final long serialVersionUID = 1L;
	
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
