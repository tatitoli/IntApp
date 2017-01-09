package app.sort;

import java.util.ArrayList;
import java.util.Arrays;

public class PresentationState{

	int[][] table;

	public PresentationState(int x, int y) {
		table = new int[x][y];
	}

	public PresentationState() {
	}

	@Override
	public String toString() {
		return "PresentationState [table=" + Arrays.toString(table) + "]";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(table);
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
		if (!Arrays.deepEquals(table, other.table))
			return false;
		return true;
	}
}
