package app.sort;

import java.util.LinkedList;

public interface Operator {

	boolean isApplicable(State s);
	
	State apply(State s);

	boolean isApplicable(State state, Operator o);

	State apply(State state, Operator op);

	State apply(State state, Operator op, LinkedList<Node> closedNodes);

}
