package app.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationState implements State {

	Map<Integer,List<Presentation>> event = new HashMap<>();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
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
		PresentationState other = (PresentationState) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		return true;
	}

	public PresentationState() {
	}
	
	@Override
	public boolean isGoal() {
		return false;
	}

	@Override
	public String toString() {
		return "PresentationState [event=" + event + "]";
	}

	@Override
	public boolean isGoal(Problem p) {
		PresentationProblem problem = (PresentationProblem)p;
		ArrayList<Integer> pIds =  (ArrayList<Integer>) problem.getPresentationIds();
		ArrayList<Integer> actualId =  new ArrayList<>();
		List<Presentation> presentations;
		for (Map.Entry<Integer, List<Presentation>> entry : event.entrySet())
		{
			presentations = entry.getValue();
			for(int i=0; i < presentations.size(); i++){
				if(pIds.contains(presentations.get(i).getiD())){
					actualId.add(presentations.get(i).getiD());
				}
			}
		}
		return actualId.containsAll(pIds);
	}
	
	public String getGoal(){
		StringBuilder sb = new StringBuilder();
		List<Presentation> presentations;
		for (Map.Entry<Integer, List<Presentation>> entry : event.entrySet())
		{
			presentations = entry.getValue();
			for(int i=0; i < presentations.size(); i++){
				sb.append(presentations.get(i).toString());
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Map<Integer, List<Presentation>> getEvent() {
		return event;
	}

	public void setEvent(Map<Integer, List<Presentation>> event) {
		this.event = event;
	}
}
