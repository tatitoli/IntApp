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

	Map<Integer, Integer> idPerDay = new HashMap<>();
	final String FORMAT = "mm:ss";

	int id;
	String fromTime;
	String toTime;
	int day;
	int topic;

	public PresentationOperator(int id, String fromTime, String toTime, int day, int topic) {
		this.id = id;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.day = day;
		this.topic = topic;
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
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		actualEvent = state.getEvent();
		for (Map.Entry<Integer, List<Presentation>> entry : actualEvent.entrySet()) {
			presentations = entry.getValue();
			for (int i = 0; i < presentations.size(); i++) {
				if (presentations.get(i).getiD() == operator.id) {
					return false;
				}
				try {
					fromDateOperator = formatter.parse(operator.fromTime);
					fromDatePresentation = formatter.parse(presentations.get(i).getFrom());
					toDateOperator = formatter.parse(operator.toTime);
					toDatePresentation = formatter.parse(presentations.get(i).getTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 if(fromDateOperator.getTime() == fromDatePresentation.getTime() 
						 || toDateOperator.getTime() == toDatePresentation.getTime()){
					 return false;
				 }
			}
		}
		return true;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public State apply(State s, Operator o) {
//		boolean canIns = true;
		PresentationState state = (PresentationState) s;
//		Map<Integer, Integer> costByDay = new HashMap<>();
		PresentationOperator operator = (PresentationOperator) o;
//		costByDay = getCostByDay(state, operator);
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		actualEvent = state.getEvent();
//		int cost = 0;
//		canIns = checkDate(actualEvent, operator);
//		List<Integer> typeList = new ArrayList<>();
//		if (idPerDay.containsKey(operator.id)) {
//			presentations = actualEvent.get(idPerDay.get(operator.id));
//			typeList.add(operator.topic);
//			Presentation actPresentation = null;
//			for (int i = 0; i < presentations.size(); i++) {
//				if (presentations.get(i).getiD() == operator.id) {
//					actPresentation = presentations.get(i);
//				}
//			}
//			presentations = actualEvent.get(operator.day);
//			if (presentations != null) {
//				for (int i = 0; i < presentations.size(); i++) {
//					if (!typeList.contains(presentations.get(i).getTopic())) {
//						typeList.add(presentations.get(i).getTopic());
//					}
//				}
//				if (typeList != null) {
//					cost = typeList.size();
//				}
//			}
//			presentations = actualEvent.get(idPerDay.get(operator.id));
//			if ((costByDay.get(idPerDay.get(operator.id)) >= cost) && canIns) {
//				presentations.remove(actPresentation);
//				actualEvent.put(idPerDay.get(operator.id), presentations);
//			}
//		}
		if (actualEvent.containsKey(operator.getDay())) {
			presentations = actualEvent.get(operator.day);
			presentations.add(
					new Presentation(operator.id, operator.day, operator.fromTime, operator.toTime, operator.topic));
			actualEvent.put(operator.day, presentations);
		} else{
			presentations = new ArrayList<>();
			presentations.add(
					new Presentation(operator.id, operator.day, operator.fromTime, operator.toTime, operator.topic));
			actualEvent.put(operator.day, presentations);
		}
		PresentationState newState = new PresentationState();
		newState.setEvent(actualEvent);
		return newState;
	}

	private Map<Integer, Integer> getCostByDay(State s, Operator o) {
		Map<Integer, Integer> costByDay = new HashMap<>();
		idPerDay = new HashMap<>();
		PresentationState state = (PresentationState) s;
		PresentationOperator operator = (PresentationOperator) o;
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		actualEvent = state.getEvent();
		for (Map.Entry<Integer, List<Presentation>> entry : actualEvent.entrySet()) {
			List<Integer> typeList = new ArrayList<>();
			int day = entry.getKey();
			presentations = entry.getValue();
			for (Presentation presentation : presentations) {
				if (!typeList.contains(presentation.getTopic())) {
					typeList.add(presentation.getTopic());
				}
				if (operator.id == presentation.getiD()) {
					idPerDay.put(presentation.getiD(), day);
				}
			}
			costByDay.put(day, typeList.size());
		}
		return costByDay;
	}
	
	private boolean checkDate(Map<Integer, List<Presentation>> actualEvent, PresentationOperator operator){
		DateFormat formatter = new SimpleDateFormat(FORMAT);
		Date toDateOperator = null;
		Date toDatePresentation = null;
		Date fromDateOperator = null;
		Date fromDatePresentation = null;
		List<Presentation> presentations = new ArrayList<>();
		for (Map.Entry<Integer, List<Presentation>> entry : actualEvent.entrySet()) {
			presentations = entry.getValue();
			for (int i = 0; i < presentations.size(); i++) {
				try {
					fromDateOperator = formatter.parse(operator.fromTime);
					fromDatePresentation = formatter.parse(presentations.get(i).getFrom());
					toDateOperator = formatter.parse(operator.toTime);
					toDatePresentation = formatter.parse(presentations.get(i).getTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 if(fromDateOperator.getTime() == fromDatePresentation.getTime() 
						 || toDateOperator.getTime() == toDatePresentation.getTime()){
					 return false;
				 }
			}
		}
		return true;
	}
	
	

	@Override
	public String toString() {
		return "PresentationOperator [id=" + id + ", fromTime=" + fromTime + ", toTime=" + toTime + ", day=" + day
				+ "]";
	}
}
