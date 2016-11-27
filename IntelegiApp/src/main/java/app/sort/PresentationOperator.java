package app.sort;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationOperator implements Operator{

	final String FORMAT = "mm:ss";
	
	int id;
	String fromTime;
	String toTime;
	int day;
	
	public PresentationOperator(int id, String fromTime, String toTime, int day) {
		super();
		this.id = id;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.day = day;
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
		PresentationState state = (PresentationState)s;
		PresentationOperator operator = (PresentationOperator)o;
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		actualEvent = state.getEvent();
		for (Map.Entry<Integer, List<Presentation>> entry : actualEvent.entrySet())
		{
			presentations = entry.getValue();
			for(int i=0; i < presentations.size(); i++){
				if(presentations.get(i).getiD() == operator.id){
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
//				Timestamp fromOp = new Timestamp(fromDateOperator.getTime());
//				Timestamp fromPe = new Timestamp(fromDatePresentation.getTime());
//				Timestamp toOp = new Timestamp(toDateOperator.getTime());
//				Timestamp toPe = new Timestamp(toDatePresentation.getTime());
				if(fromDatePresentation.getTime() <= fromDateOperator.getTime() && toDatePresentation.getTime()
						 > toDateOperator.getTime()){
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
		PresentationState state = (PresentationState)s;
		PresentationOperator operator = (PresentationOperator)o;
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		actualEvent = state.getEvent();
		if(actualEvent.containsKey(operator.day)){
			presentations = actualEvent.get(operator.day);
			presentations.add(new Presentation(operator.id,operator.day, operator.fromTime,operator.toTime));
			actualEvent.put(operator.day, presentations);
		}else{
			presentations.add(new Presentation(operator.id,operator.day, operator.fromTime,operator.toTime));
			actualEvent.put(operator.day,presentations);
		}
		PresentationState newState = new PresentationState();
		newState.setEvent(actualEvent);
		return newState;
	}

	@Override
	public String toString() {
		return "PresentationOperator [id=" + id + ", fromTime=" + fromTime + ", toTime=" + toTime + ", day=" + day
				+ "]";
	}
}
