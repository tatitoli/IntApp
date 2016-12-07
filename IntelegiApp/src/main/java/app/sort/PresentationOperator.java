package app.sort;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationOperator implements Operator {

	final String FORMAT = "mm:ss";

	private String presentationTitle;
	private String actor;
	private String topic;
	private String from;
	private String to;

	public PresentationOperator(String presentationTitle, String actor, String topic, String from, String to) {
		super();
		this.presentationTitle = presentationTitle;
		this.actor = actor;
		this.topic = topic;
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean isApplicable(State s) {
		return true;
	}

	@Override
	public State apply(State s) {
		return null;
	}

	@Override
	public boolean isApplicable(State s, Operator o) {
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
				if (tabla[i][j] != null && tabla[i][j].getPresentationTitle() == operator.getPresentationTitle()) {
					return false;
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
							|| toDateOperator.getTime() == toDatePresentation.getTime()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public State apply(State s, Operator o) {
		boolean add = false;
		PresentationState state = (PresentationState) s;
		PresentationOperator operator = (PresentationOperator) o;
		Presentation tabla[][] = state.getTable();
		int index = getLowIndex(state, operator);
		for(int i = 0;i<tabla[index].length;i++){
			if (tabla[index][i] == null) {
					tabla[index][i] = new Presentation(operator.getPresentationTitle(), operator.getActor(), operator.getTopic(), operator.getFrom(), operator.getTo());
					add = true;
					break;
			}
		}
		PresentationState newState = new PresentationState();
		newState.setTable(tabla);
		return newState;
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
}
