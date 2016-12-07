package app.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PresentationProblem implements Problem {

	
	static Set<Operator> operators = new HashSet<>();
	List<String> presentationIds = new ArrayList<>();

	public PresentationProblem() {
		operators = new HashSet<>();
//		operators.add(new PresentationOperator(2, "8:00", "9:00", 1, false));
//		operators.add(new PresentationOperator(1, "9:00", "10:00", 1,false));
//		operators.add(new PresentationOperator(1, "10:00", "10:30", 1, true));
//		operators.add(new PresentationOperator(3, "10:00", "10:00", 1, false));
//		operators.add(new PresentationOperator(4, "11:00", "12:00", 3, false));
//		operators.add(new PresentationOperator(8, "12:00", "13:00", 2, false));
//		operators.add(new PresentationOperator(3, "10:00", "11:00", 2, false));
//		operators.add(new PresentationOperator(7, "13:00", "14:00", 2, false));
//		operators.add(new PresentationOperator(3, "10:00", "10:00", 2, false));
//		operators.add(new PresentationOperator(6, "14:00", "15:00", 2,false));
		for (Operator o : operators) {
			PresentationOperator operator = (PresentationOperator) o;
			if (!presentationIds.contains(operator.getPresentationTitle())) {
				presentationIds.add(operator.getPresentationTitle());
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

	public List<String> getPresentationIds() {
		for (Operator o : operators) {
			PresentationOperator operator = (PresentationOperator) o;
			if (!presentationIds.contains(operator.getPresentationTitle())) {
				presentationIds.add(operator.getPresentationTitle());
			}
		}
		return presentationIds;
	}

	public void setPresentationIds(List<String> presentationIds) {
		this.presentationIds = presentationIds;
	}

	public int cost(Operator op, State state) {
		List<String> typeList = new ArrayList<>();
		PresentationState s = (PresentationState) state;
		Presentation tabla[][] = s.getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (!typeList.contains(tabla[i][j].getTopic())) {
					typeList.add(tabla[i][j].getTopic());
				}
			}
			return typeList.size();
		}
		return 0;
	}

	public static Set<Operator> getOperators() {
		return operators;
	}

	public static void setOperators(Set<Operator> operators) {
		PresentationProblem.operators = operators;
	}
}
