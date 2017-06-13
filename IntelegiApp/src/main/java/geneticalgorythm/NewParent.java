package geneticalgorythm;

import java.util.LinkedList;
import java.util.Map;

public class NewParent {
	Map<String, LinkedList<Integer>> stateMap;
	Integer cost;
	Integer fitness;

	public NewParent(Map<String, LinkedList<Integer>> stateMap, Integer cost) {
		super();
		this.stateMap = stateMap;
		this.cost = cost;
	}

	public Map<String, LinkedList<Integer>> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<String, LinkedList<Integer>> stateMap) {
		this.stateMap = stateMap;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getFitness() {
		return fitness;
	}

	public void setFitness(Integer fitness) {
		this.fitness = fitness;
	}
}
