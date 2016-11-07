package app.sort;

public class PresentationOperator implements Operator{

	int id;
	String time;
	int day;
	
	public PresentationOperator(int id,String time, int day) {
        this.time = time;
        this.day = day;
        this.id = id;
    }
	
	@Override
	public boolean isApplicable(State s) {
		return true;
	}

	@Override
	public State apply(State s) {
		return null;
	}

	@Override
	public boolean isApplicable(State s, Operator o) {
		PresentationState state = (PresentationState)s;
		PresentationOperator operator = (PresentationOperator)o;
		int[][] data = new int[12][2];
		data = state.getData();
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 2; j++) {
				if(data[i][j] == operator.id){
					return false;
				}
			}
		}
		if(data[Integer.parseInt(operator.getTime())-8][operator.getDay()-1] != 0){
			return false;
		}
		return true;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public State apply(State s, Operator o) {
		PresentationState state = (PresentationState)s;
		PresentationOperator operator = (PresentationOperator)o;
		int[][] data = new int[12][2];
		data = state.getData();
		data[Integer.parseInt(operator.getTime())-8][operator.getDay()-1] = operator.id;
		PresentationState newState = new PresentationState();
		newState.setData(data);
		return newState;
	}

	@Override
	public String toString() {
		return "PresentationOperator [id=" + id + ", time=" + time + ", day=" + day + "]";
	}
}
