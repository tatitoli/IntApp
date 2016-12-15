package app.sort;

import java.util.ArrayList;
import java.util.List;

public class Node {
    State state;
    Operator op;
    Node parent;
    int cost;

    public Node(State state, Operator op, Node parent) {
        this.cost = parent == null ? 0 : parent.cost + getCost(state) + getWightPay(op);
        this.op = op;
        this.parent = parent;
        this.state = state;
    }

    private int getWightPay(Operator op) {
    	PresentationOperator operator = (PresentationOperator)op;
		return 100 - operator.getWight();
	}

	@Override
    public String toString() {
        return state.toString();
    }
	
	static int getCost(State state){
    	List<String> typeList = new ArrayList<>();
		PresentationState s = (PresentationState) state;
		int db = 0;
		Presentation tabla[][] = s.getTable();
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null && !typeList.contains(tabla[i][j].getTopic())) {
					typeList.add(tabla[i][j].getTopic());
				}
			}
			if(typeList!= null){
				db += typeList.size()-1;
			}
			if(db<0){
				db = 0;
			}
		}
		return db;
    }

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Operator getOp() {
		return op;
	}

	public void setOp(Operator op) {
		this.op = op;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
