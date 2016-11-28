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
	List<Integer> presentationIds = new ArrayList<>();

	public PresentationProblem() {
//		operators = new HashSet<>();
//		operators.add(new PresentationOperator(2, "8:00", "9:00", 1, 1));
//		operators.add(new PresentationOperator(1, "9:00", "10:00", 1, 1));
//		operators.add(new PresentationOperator(1, "10:00", "10:30", 2, 1));
//		operators.add(new PresentationOperator(3, "10:00", "10:00", 1, 1));
//		operators.add(new PresentationOperator(4, "11:00", "12:00", 3, 3));
//		operators.add(new PresentationOperator(8, "12:00", "13:00", 1, 2));
//		operators.add(new PresentationOperator(3, "10:00", "11:00", 1, 2));
//		operators.add(new PresentationOperator(7, "13:00", "14:00", 2, 2));
//		operators.add(new PresentationOperator(3, "10:00", "10:00", 1, 2));
//		operators.add(new PresentationOperator(6, "14:00", "15:00", 3, 2));
//		operators.add(new PresentationOperator(3, "10:00", "10:00", 1, 2));

//		for (Operator o : operators) {
//			PresentationOperator operator = (PresentationOperator) o;
//			if (!presentationIds.contains(operator.id)) {
//				presentationIds.add(operator.id);
//			}
//		}
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
		for (Operator o : operators) {
			PresentationOperator operator = (PresentationOperator) o;
			if (!presentationIds.contains(operator.id)) {
				presentationIds.add(operator.id);
			}
		}
		return presentationIds;
	}

	public void setPresentationIds(List<Integer> presentationIds) {
		this.presentationIds = presentationIds;
	}

	public int cost(Operator op, State state) {
		List<Integer> typeList = new ArrayList<>();
		PresentationState s = (PresentationState) state;
		PresentationOperator operator = (PresentationOperator) op;
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		actualEvent = s.getEvent();
		for (Map.Entry<Integer, List<Presentation>> entry : actualEvent.entrySet()) {
			presentations = entry.getValue();
			typeList = new ArrayList<>();
			typeList.add(operator.topic);
			for (int i = 0; i < presentations.size(); i++) {
				if (!typeList.contains(presentations.get(i).getTopic())) {
					typeList.add(presentations.get(i).getTopic());
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
