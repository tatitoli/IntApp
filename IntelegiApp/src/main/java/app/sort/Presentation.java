package app.sort;

public class Presentation {
	private int iD;
	private int day;
	private String from;
	private String to;
	
	public Presentation(){
		
	}
	
	public Presentation(int iD, int day, String from, String to) {
		this.iD = iD;
		this.day = day;
		this.from = from;
		this.to = to;
	}
	public int getiD() {
		return iD;
	}
	public void setiD(int iD) {
		this.iD = iD;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
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
