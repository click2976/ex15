package example01.dto;

import java.sql.Timestamp;

public class MemoDTO {

	private int id;
	private String name;
	private String content;
	private Timestamp regi_date;
	
	
	
	public MemoDTO() {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Timestamp getRegi_date() {
		return regi_date;
	}



	public void setRegi_date(Timestamp regi_date) {
		this.regi_date = regi_date;
	}

	
}
