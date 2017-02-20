package app.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PresentationProblem{

	private int x;
	private int y;
	static Set<PresentationOperator> operators = new HashSet<>();
	List<Integer> presentationIds = new ArrayList<>();

	public PresentationProblem() {
		operators = new HashSet<>();
		for (PresentationOperator o : operators) {
			if (!presentationIds.contains(o.getId())) {
				presentationIds.add(o.getId());
			}
		}
	}

	public PresentationState startState() {
		return new PresentationState(x, y);
	}

	public Collection<PresentationOperator> operators() {
		return operators;
	}

	public List<Integer> getPresentationIds() {
		for (PresentationOperator o : operators) {
			if (!presentationIds.contains(o.getId())) {
				presentationIds.add(o.getId());
			}
		}
		return presentationIds;
	}

	public void setPresentationIds(List<Integer> presentationIds) {
		this.presentationIds = presentationIds;
	}

	public Set<PresentationOperator> getOperators() {
		return operators;
	}

	public static void setOperators(Set<PresentationOperator> operators) {
		PresentationProblem.operators = operators;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
