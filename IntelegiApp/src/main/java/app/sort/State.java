package app.sort;

public interface State {

	boolean isGoal();

	boolean isGoal(Problem p);

	Object getGoal();

}
