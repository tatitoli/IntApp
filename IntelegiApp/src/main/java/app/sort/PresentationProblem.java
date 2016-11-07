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
		operators.add( new PresentationOperator(2,"10",1) );
		operators.add( new PresentationOperator(2,"10",1) );
		operators.add( new PresentationOperator(3,"11",1) );
		operators.add( new PresentationOperator(4,"12",2) );
		operators.add( new PresentationOperator(6,"19",1) );
		operators.add( new PresentationOperator(5,"17",2) );
		operators.add( new PresentationOperator(7,"15",1) );
		operators.add( new PresentationOperator(8,"14",2) );
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
