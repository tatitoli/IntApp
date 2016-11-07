package app.sort;

public interface Operator {

	boolean isApplicable(State s);
	
	State apply(State s);

	boolean isApplicable(State state, Operator o);

	State apply(State state, Operator op);

}
