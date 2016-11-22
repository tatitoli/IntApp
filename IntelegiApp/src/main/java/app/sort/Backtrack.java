package app.sort;

import java.util.Collection;
import java.util.LinkedList;

public class Backtrack {
	private class Node {
		State state;

		Operator op;

		LinkedList<Operator> unused;
		double cost;

		Node(State state, Operator op, Collection<Operator> ops) {
			this.state = state;
			this.op = op;

			unused = new LinkedList<>();
			for (Operator o : ops)
				if (o.isApplicable(state, o))
					unused.add(o);
		}

	}

	Problem p;
	LinkedList<Node> actPath = null;

	public Backtrack(Problem p) {
		this.p = p;
	}
	
	public String getGoal() {
		return (String) actPath.getLast().state.getGoal();
	}
	
	public String[] getGoalOperatorsAndCausedState() {
		String res[] = new String[actPath.size()];
		int poz = 0;
		for (Node n : actPath) {
			res[poz] = n.op + " " + n.state;
			poz++;
		}
		return res;
	}

	public boolean run() {
		actPath = new LinkedList<>();
		actPath.add(new Node(p.startState(), null, p.operators()));

		while (true) {

			if (actPath.isEmpty())
				return false;
			Node actNode = actPath.getLast();
			if (actNode.state.isGoal(p))
				return true;

			if (!actNode.unused.isEmpty()) {
				Operator op = actNode.unused.removeFirst();
				State newState = op.apply(actNode.state, op);
				boolean found = false;
				for (Node node : actPath)
					if (node.state.equals(newState)) {
						found = true;
						break;
					}
				if (!found) {
					actPath.addLast(new Node(newState, op, p.operators()));
				}

			} else {
				actPath.removeLast();
			}
		}
	}
}
