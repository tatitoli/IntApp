package app.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PresentationProblem{

	private int x;
	private int y;
	static LinkedList<PresentationOperator> operators = new LinkedList<>();
	List<Integer> presentationIds = new ArrayList<>();

	public PresentationProblem() {
		operators = new LinkedList<>();
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

	public LinkedList<PresentationOperator> getOperators() {
		return operators;
	}

	public static void setOperators(LinkedList<PresentationOperator> operators) {
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
