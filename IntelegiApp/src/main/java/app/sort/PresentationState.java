package app.sort;

import java.util.ArrayList;
import java.util.Arrays;

public class PresentationState implements State {

	Presentation[][] table = new Presentation[3][10];

	public PresentationState() {
		table = new Presentation[3][10];
	}

	@Override
	public boolean isGoal() {
		return false;
	}

	@Override
	public String toString() {
		return "PresentationState [table=" + Arrays.toString(table) + "]";
	}

	@Override
	public boolean isGoal(Problem p) {
		PresentationProblem problem = (PresentationProblem) p;
		ArrayList<String> pIds = (ArrayList<String>) problem.getPresentationIds();
		ArrayList<String> actualId = new ArrayList<>();
		Presentation tabla[][] = getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null && pIds.contains(tabla[i][j].getPresentationTitle())) {
					actualId.add(tabla[i][j].getPresentationTitle());
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
}
