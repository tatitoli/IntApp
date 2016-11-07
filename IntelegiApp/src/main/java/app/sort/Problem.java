package app.sort;

import java.util.Collection;

public interface Problem {

	State startState();
	
	Collection<Operator> operators();
}
