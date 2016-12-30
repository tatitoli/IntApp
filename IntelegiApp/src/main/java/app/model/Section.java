package app.model;

public class Section {
	
	private int sectionNumber;
	private String from;
	private String to;
	
	public Section(int sectionNumber, String from, String to) {
		super();
		this.sectionNumber = sectionNumber;
		this.from = from;
		this.to = to;
	}

	public int getSectionNumber() {
		return sectionNumber;
	}
	
	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
}
