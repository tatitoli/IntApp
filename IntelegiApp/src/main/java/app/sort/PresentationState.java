package app.sort;

import java.util.ArrayList;
import java.util.Arrays;

public class PresentationState{

	Presentation[][] table;

	public PresentationState(int x, int y) {
		table = new Presentation[x][y];
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
		Presentation tabla[][] = getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null && pIds.contains(tabla[i][j].getId())) {
					actualId.add(tabla[i][j].getId());
				}
			}
		}
		return actualId.containsAll(pIds);
	}

	public String getGoal() {
		StringBuilder sb = new StringBuilder();
		Presentation tabla[][] = getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null) {
					sb.append(tabla[i][j].toString());
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Presentation[][] getTable() {
		return table;
	}

	public void setTable(Presentation[][] table) {
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
