package app.sort;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PresentationOperator{

	final String FORMAT = "mm:ss";

	private int id;
	private String presentationTitle;
	private String actor;
	private String topic;
	private String from;
	private String to;
	private boolean piority;
	private int weight;

	public PresentationOperator(int id, String presentationTitle, String actor, String topic, String from, String to, boolean piority, int weight) {
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

	public boolean isApplicable(PresentationState s, PresentationOperator o) {
		DateFormat formatter = new SimpleDateFormat(FORMAT);
		Date toDateOperator = null;
		Date toDatePresentation = null;
		Date fromDateOperator = null;
		Date fromDatePresentation = null;
		PresentationState state = (PresentationState) s;
		PresentationOperator operator = (PresentationOperator) o;
		Presentation tabla[][] = state.getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null && tabla[i][j].getId() == operator.getId()) {
					return false;
				}
			}
		}
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null) {
					try {
						fromDateOperator = formatter.parse(operator.getFrom());
						fromDatePresentation = formatter.parse(tabla[i][j].getFrom());
						toDateOperator = formatter.parse(operator.getTo());
						toDatePresentation = formatter.parse(tabla[i][j].getTo());
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

	public int getLowIndex(PresentationState state, PresentationOperator operator){
		List<String> typeList = new ArrayList<>();
		int maxCost = Integer.MAX_VALUE;
		int db = 0;
		int index = 0;
		Presentation tabla[][] = state.getTable();
		for (int i = 0; i < tabla.length; i++) {
			db = 0;
			typeList = new ArrayList<>();
			typeList.add(operator.getTopic());
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null && !typeList.contains(tabla[i][j].getTopic())) {
					typeList.add(tabla[i][j].getTopic());
				}
			}
			db = typeList.size();
			if(db<0){
				db = 0;
			}
			if(db < maxCost){
				maxCost = db;
				index = i;
			}
		}
		return index;
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

	public int getInsertIndex(PresentationState state, PresentationOperator operator) {
		DateFormat formatter = new SimpleDateFormat(FORMAT);
		Date toDateOperator = null;
		Date toDatePresentation = null;
		Date fromDateOperator = null;
		Date fromDatePresentation = null;
		int index = 0;
		boolean insert = true;
		Presentation tabla[][] = state.getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if(tabla[i][j] == null){
					return i;
				}
				if (tabla[i][j] != null) {
					try {
						fromDateOperator = formatter.parse(operator.getFrom());
						fromDatePresentation = formatter.parse(tabla[i][j].getFrom());
						toDateOperator = formatter.parse(operator.getTo());
						toDatePresentation = formatter.parse(tabla[i][j].getTo());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (fromDateOperator.getTime() == fromDatePresentation.getTime()
							&& toDateOperator.getTime() <= toDatePresentation.getTime()) {
						insert = false;
					}
					if (fromDateOperator.getTime() >= fromDatePresentation.getTime()
							&& toDateOperator.getTime() == toDatePresentation.getTime()) {
						insert = false;
					}
					if (fromDateOperator.getTime() <= fromDatePresentation.getTime()
							&& toDateOperator.getTime() >= toDatePresentation.getTime()) {
						insert = false;
					}
					if (fromDateOperator.getTime() <= fromDatePresentation.getTime()
							&& toDateOperator.getTime() > fromDatePresentation.getTime()) {
						insert = false;
					}
					if (toDateOperator.getTime() >= toDatePresentation.getTime()
							&& fromDateOperator.getTime() < toDatePresentation.getTime()) {
						insert = false;
					}
				}
				if(insert){
					index =i;
				}
			}
		}
		return index;
	}

	public PresentationState apply(PresentationState state, PresentationOperator operator, LinkedList<app.sort.Node> closedNodes) {
		List<Integer> lista = new ArrayList<>();
		List<Integer> hasIndex = new ArrayList<Integer>(); 
		Presentation insertPresentation = new Presentation(operator.getId(), operator.getPresentationTitle(), operator.getActor(), operator.getTopic(), operator.getFrom(), operator.getTo(), operator.isPiority(),operator.getWeight());
		for (Node node : closedNodes) {
			PresentationState tempState = (PresentationState) node.getState();
			Presentation tabla[][] = tempState.getTable();
			for (int i = 0; i < tabla.length; i++) {
				for (int j = 0; j < tabla[i].length; j++) {
					if(insertPresentation.equals(tabla[i][j])){
						hasIndex.add(i);
						break;
					}
				}
			}
		}
		Presentation tabla[][] = state.getTable();
		for (int i = 0; i < tabla.length; i++) {
			lista.add(i);
		}
		lista.removeAll(hasIndex);
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(lista.size());
//		boolean insert = false;
//		for (int i = 0; i < tabla.length; i++) {
//			if (insert) {
//				break;
//			}
//			if (!hasIndex.contains(i)) {
//				for (int j = 0; j < tabla[i].length; j++) {
//					if (tabla[i][j] == null) {
//						tabla[i][j] = insertPresentation;
//						insert = true;
//						break;
//					}
//
//				}
//			}
//		}
		for (int i = 0; i < tabla[randomInt].length; i++) {
			if (tabla[randomInt][i] == null) {
				tabla[randomInt][i] = insertPresentation;
				break;
			}
		}
		PresentationState newState = new PresentationState(tabla.length, tabla[0].length);
		newState.setTable(tabla);
		return newState;
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
}
