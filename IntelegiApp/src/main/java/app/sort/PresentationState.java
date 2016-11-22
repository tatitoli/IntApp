package app.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationState implements State {

	Map<Integer,List<Presentation>> event = new HashMap<>();
	
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
		return "Siker";
	}

	public Map<Integer, List<Presentation>> getEvent() {
		return event;
	}

	public void setEvent(Map<Integer, List<Presentation>> event) {
		this.event = event;
	}
}
