package app.sort;

import java.util.ArrayList;

public class PresentationState implements State {

	int[][] data;

	public PresentationState() {
		data = new int[12][2];
		data[5][0] = -1;
		data[5][1] = -1;
	}
	
	public int cell(int row, int coll) {
        return data[row - 1][coll - 1];
    }

	@Override
	public boolean isGoal() {
		
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= 12; i++) {
			for (int j = 1; j <= 2; j++) {
				sb.append(cell(i, j) == 1 ? "# " : "- ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	@Override
	public boolean isGoal(Problem p) {
		PresentationProblem problem = (PresentationProblem)p;
		ArrayList<Integer> pIds =  (ArrayList<Integer>) problem.getPresentationIds();
		ArrayList<Integer> actualId =  new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 2; j++) {
				if(pIds.contains(data[i][j])){
					actualId.add(data[i][j]);
				}
			}
		}
		return actualId.containsAll(pIds);
	}
	
	public String getGoal(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 2; j++) {
				sb.append(data[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
