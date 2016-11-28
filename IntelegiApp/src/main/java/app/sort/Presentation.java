package app.sort;

public class Presentation {
	private int iD;
	private int day;
	private String from;
	private String to;
	private int topic;
	
	public Presentation(){
		
	}
	
	public Presentation(int iD, int day, String from, String to, int topic) {
		this.iD = iD;
		this.day = day;
		this.from = from;
		this.to = to;
		this.topic = topic;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + iD;
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + topic;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presentation other = (Presentation) obj;
		if (day != other.day)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (iD != other.iD)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (topic != other.topic)
			return false;
		return true;
	}

	public int getTopic() {
		return topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
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

	@Override
	public String toString() {
		return "Presentation [iD=" + iD + ", day=" + day + ", from=" + from + ", to=" + to + "]";
	}
}
