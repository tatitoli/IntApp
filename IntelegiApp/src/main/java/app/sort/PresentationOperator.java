package app.sort;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class PresentationOperator {

	final String FORMAT = "mm:ss";

	private int id;
	private String presentationTitle;
	private String actor;
	private String topic;
	private String from;
	private String to;
	private boolean piority;
	private int weight;

	public PresentationOperator(int id, String presentationTitle, String actor, String topic, String from, String to,
			boolean piority, int weight) {
		super();
		this.id = id;
		this.presentationTitle = presentationTitle;
		this.actor = actor;
		this.topic = topic;
		this.from = from;
		this.to = to;
		this.piority = piority;
		this.weight = weight;
	}

	public PresentationOperator() {
		super();
	}

	public boolean isApplicable(PresentationState s, PresentationOperator o, LinkedList<PresentationOperator> operators) {
		DateFormat formatter = new SimpleDateFormat(FORMAT);
		Date toDateOperator = null;
		Date toDatePresentation = null;
		Date fromDateOperator = null;
		Date fromDatePresentation = null;
		PresentationState state = (PresentationState) s;
		PresentationOperator operator = (PresentationOperator) o;
		int tabla[][] = state.getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != 0 && tabla[i][j] == operator.getId()) {
					return false;
				}
			}
		}
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != 0) {
					PresentationOperator presentation = null;
					for (PresentationOperator ope : operators) {
						if (tabla[i][j] == ope.getId()) {
							presentation = ope;
							break;
						}
					}
					try {
						fromDateOperator = formatter.parse(operator.getFrom());
						fromDatePresentation = formatter.parse(presentation.getFrom());
						toDateOperator = formatter.parse(operator.getTo());
						toDatePresentation = formatter.parse(presentation.getTo());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (fromDateOperator.getTime() == fromDatePresentation.getTime()
							&& toDateOperator.getTime() <= toDatePresentation.getTime()) {
						return false;
					}
					if (fromDateOperator.getTime() >= fromDatePresentation.getTime()
							&& toDateOperator.getTime() == toDatePresentation.getTime()) {
						return false;
					}
					if (fromDateOperator.getTime() <= fromDatePresentation.getTime()
							&& toDateOperator.getTime() >= toDatePresentation.getTime()) {
						return false;
					}
					if (fromDateOperator.getTime() <= fromDatePresentation.getTime()
							&& toDateOperator.getTime() > fromDatePresentation.getTime()) {
						return false;
					}
					if (toDateOperator.getTime() >= toDatePresentation.getTime()
							&& fromDateOperator.getTime() < toDatePresentation.getTime()) {
						return false;
					}
					if (toDateOperator.getTime() <= fromDatePresentation.getTime()) {
						return true;
					}
					if (fromDateOperator.getTime() >= toDatePresentation.getTime()) {
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "PresentationOperator [presentationTitle=" + presentationTitle + ", actor=" + actor + ", topic=" + topic
				+ ", from=" + from + ", to=" + to + "]";
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

	public boolean isPiority() {
		return piority;
	}

	public void setPiority(boolean piority) {
		this.piority = piority;
	}

	public PresentationState apply(PresentationState state, PresentationOperator operator) {
		int tabla[][] = state.getTable();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(tabla.length);
		boolean insert = false;
		int newTabla[][] = new int[tabla.length][tabla[0].length];
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != 0) {
					newTabla[i][j] = tabla[i][j];
				}
			}
		}
		for (int i = 0; i < newTabla[randomInt].length; i++) {
			if (newTabla[randomInt][i] == 0) {
				newTabla[randomInt][i] = operator.getId();
				insert = true;
				break;
			}
		}
		if (insert) {
			PresentationState newState = new PresentationState();
			newState.setTable(newTabla);
			return newState;
		}
		else{
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((FORMAT == null) ? 0 : FORMAT.hashCode());
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + id;
		result = prime * result + (piority ? 1231 : 1237);
		result = prime * result + ((presentationTitle == null) ? 0 : presentationTitle.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		result = prime * result + weight;
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
		PresentationOperator other = (PresentationOperator) obj;
		if (FORMAT == null) {
			if (other.FORMAT != null)
				return false;
		} else if (!FORMAT.equals(other.FORMAT))
			return false;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id != other.id)
			return false;
		if (piority != other.piority)
			return false;
		if (presentationTitle == null) {
			if (other.presentationTitle != null)
				return false;
		} else if (!presentationTitle.equals(other.presentationTitle))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	public PresentationState apply(PresentationState state, PresentationOperator op, LinkedList<PresentationOperator> operators) {
		LinkedList<String> typeList = new LinkedList<>();
		int tabla[][] = state.getTable();
		boolean insert = false;
		int newTabla[][] = new int[tabla.length][tabla[0].length];
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != 0) {
					newTabla[i][j] = tabla[i][j];
				}
			}
		}
		PresentationOperator presentation = null;
		int typeCount = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < tabla.length; i++) {
			typeList = new LinkedList<>();
			typeList.add(op.getTopic());
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != 0) {
					for (PresentationOperator operator : operators) {
						if(operator.getId() == tabla[i][j]){
							presentation  = operator;
						}
					}
					if(!typeList.contains(presentation.getTopic())){
						typeList.add(presentation.getTopic());
					}
				}
			}
			if(typeList.size() < typeCount){
				typeCount = typeList.size()-1;
				index = i;
			}
		}
		for (int i = 0; i < newTabla[index].length; i++) {
			if (newTabla[index][i] == 0) {
				newTabla[index][i] = op.getId();
				insert = true;
				break;
			}
		}
		if (insert) {
			PresentationState newState = new PresentationState();
			newState.setTable(newTabla);
			return newState;
		}
		else{
			return null;
		}
	}
}
