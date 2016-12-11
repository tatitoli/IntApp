package app.sort;

public class Presentation {
	private String presentationTitle;
	private String actor;
	private String topic;
	private String from;
	private String to;
	private boolean poirity;
	private int weight;
	
	public Presentation(){
		
	}

	public Presentation(String presentationTitle, String actor, String topic, String from, String to, boolean piority, int weight) {
		super();
		this.presentationTitle = presentationTitle;
		this.actor = actor;
		this.topic = topic;
		this.from = from;
		this.to = to;
		this.poirity = piority;
		this.weight = weight;
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

	public String getPresentationTitle() {
		return presentationTitle;
	}

	public void setPresentationTitle(String presentationTitle) {
		this.presentationTitle = presentationTitle;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public boolean isPoirity() {
		return poirity;
	}

	public void setPoirity(boolean poirity) {
		this.poirity = poirity;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Presentation [presentationTitle=" + presentationTitle + ", actor=" + actor + ", topic=" + topic
				+ ", from=" + from + ", to=" + to + "]";
	}
}
