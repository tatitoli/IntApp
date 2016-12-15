package app.sort;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PresentationOperator implements Operator {

	final String FORMAT = "mm:ss";

	private String presentationTitle;
	private String actor;
	private String topic;
	private String from;
	private String to;
	private boolean piority;
	private int weight;

	public PresentationOperator(String presentationTitle, String actor, String topic, String from, String to, boolean piority, int weight) {
		super();
		this.presentationTitle = presentationTitle;
		this.actor = actor;
		this.topic = topic;
		this.from = from;
		this.to = to;
		this.piority = piority;
		this.weight = weight;
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
	public State apply(State s, Operator o) {
		PresentationState state = (PresentationState) s;
		PresentationOperator operator = (PresentationOperator) o;
		Presentation tabla[][] = state.getTable();
		int index = getLowIndex(state, operator);
//		int index = getInsertIndex(state, operator);
		for(int i = 0;i<tabla[index].length;i++){
			if (tabla[index][i] == null) {
					tabla[index][i] = new Presentation(operator.getPresentationTitle(), operator.getActor(), operator.getTopic(), operator.getFrom(), operator.getTo(), false,15);
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

	public boolean isPiority() {
		return piority;
	}

	public void setPiority(boolean piority) {
		this.piority = piority;
	}	
	
	public int getWight() {
		return weight;
	}

	public void setWight(int wight) {
		this.weight = wight;
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

	@Override
	public State apply(State s, Operator op, LinkedList<app.sort.Node> closedNodes) {
		PresentationState state = (PresentationState) s;
		PresentationOperator operator = (PresentationOperator) op;
		List<Integer> hasIndex = new ArrayList<Integer>(); 
		Presentation insertPresentation = new Presentation(operator.getPresentationTitle(), operator.getActor(), operator.getTopic(), operator.getFrom(), operator.getTo(), false,15);
		for (Node node : closedNodes) {
			PresentationState tempState = (PresentationState) node.getState();
			Presentation tabla[][] = tempState.getTable();
			for (int i = 0; i < tabla.length; i++) {
				if(hasIndex.contains(i)){
					break;
				}
				for (int j = 0; j < tabla[i].length; j++) {
					if(insertPresentation.equals(tabla[i][j])){
						hasIndex.add(i);
						break;
					}
				}
			}
		}
		Presentation tabla[][] = state.getTable();
//		int index = getLowIndex(state, operator);
//		int index = getInsertIndex(state, operator);
		for(int i = 0;i<tabla.length;i++){
			if(!hasIndex.equals(i)){
				for(int j=0;j<tabla[i].length;j++){
				if (tabla[i][j] == null) {
					tabla[i][j] = insertPresentation;
					break;
				}
				
			}
		}
		}
		PresentationState newState = new PresentationState();
		newState.setTable(tabla);
		return newState;
	}
}
