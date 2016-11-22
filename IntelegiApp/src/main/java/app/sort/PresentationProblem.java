package app.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PresentationProblem implements Problem{
	
	static Set<Operator> operators = new HashSet<>();
	List<Integer> presentationIds = new ArrayList<>();
	
	public PresentationProblem() {
		operators = new HashSet<>();
		operators.add( new PresentationOperator(2,"7:00","8:00",1) );
		operators.add( new PresentationOperator(2,"10:00","10:30",1) );
		operators.add( new PresentationOperator(3,"11:00","12:00",1) );
		operators.add( new PresentationOperator(4,"12:00","13:00",2) );
		operators.add( new PresentationOperator(6,"19:00","20:00",1) );
		operators.add( new PresentationOperator(5,"17:00","18:00",2) );
		operators.add( new PresentationOperator(7,"15:00","16:00",1) );
		operators.add( new PresentationOperator(8,"14:30","15:30",2) );
		for (Operator o : operators){
			PresentationOperator operator = (PresentationOperator)o;
			if(!presentationIds.contains(operator.id)){
				presentationIds.add(operator.id);
			}
		}
	}
	
	@Override
	public State startState() {
		return new PresentationState();
	}

	@Override
	public Collection<Operator> operators() {
		return operators;
	}

	public List<Integer> getPresentationIds() {
		return presentationIds;
	}

	public void setPresentationIds(List<Integer> presentationIds) {
		this.presentationIds = presentationIds;
	}
}
