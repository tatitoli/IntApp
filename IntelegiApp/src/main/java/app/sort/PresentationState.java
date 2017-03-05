package app.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PresentationState{

	private int[][] table;
	private Map<String, LinkedList<Integer>> mapTabel;

	public PresentationState(int x, int y) {
		table = new int[x][y];
	}

	public PresentationState() {
	}

	public PresentationState(LinkedList<PresentationOperator> operators, LinkedList<String >sections) {
		mapTabel = new HashMap<>();
		for (PresentationOperator presentationOperator : operators) {
			String tmp = presentationOperator.getFrom().substring(0, 11);
			for (String string : sections){
				if(!mapTabel.containsKey(string+"_"+tmp)){
					mapTabel.put(string+"_"+tmp, new LinkedList<Integer>());
				}
			}
		}
	}

	@Override
	public String toString() {
		return "PresentationState [table=" + Arrays.toString(table) + "]";
	}

	public boolean isGoalMap(PresentationProblem p) {
		ArrayList<Integer> pIds = (ArrayList<Integer>) p.getPresentationIds();
		ArrayList<Integer> actualId = new ArrayList<>();
		Map<String, LinkedList<Integer>> tempMap = getMapTabel(); 
		for (Map.Entry<String, LinkedList<Integer>> entry : tempMap.entrySet()) {
			actualId.addAll(entry.getValue());
		}
		return actualId.containsAll(pIds);
	}
	
	public boolean isGoal(PresentationProblem p) {
		ArrayList<Integer> pIds = (ArrayList<Integer>) p.getPresentationIds();
		ArrayList<Integer> actualId = new ArrayList<>();
		int tabla[][] = getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j]!=0 && pIds.contains(tabla[i][j])) {
					actualId.add(tabla[i][j]);
				}
			}
		}
		return actualId.containsAll(pIds);
	}

	public String getGoal() {
		StringBuilder sb = new StringBuilder();
		int tabla[][] = getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != 0) {
					sb.append(String.valueOf(tabla[i][j]));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public int[][] getTable() {
		return table;
	}

	public void setTable(int[][] table) {
		this.table = table;
	}

	public Map<String, LinkedList<Integer>> getMapTabel() {
		return mapTabel;
	}

	public void setMapTabel(Map<String, LinkedList<Integer>> map) {
		this.mapTabel = map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mapTabel == null) ? 0 : mapTabel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PresentationState other = (PresentationState) obj;
		if (mapTabel == null) {
			if (other.mapTabel != null)
				return false;
		} else if (!mapTabel.equals(other.mapTabel))
			return false;
		return true;
	}
}
