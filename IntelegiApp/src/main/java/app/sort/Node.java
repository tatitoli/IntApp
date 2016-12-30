package app.sort;

import java.util.ArrayList;
import java.util.List;

public class Node {
    PresentationState state;
    PresentationOperator op;
    Node parent;
    int cost;

    public Node(PresentationState state, PresentationOperator op, Node parent) {
        this.cost = parent == null ? 0 : parent.cost + getCost(state) + getWeightPay(op);
        this.op = op;
        this.parent = parent;
        this.state = state;
    }

    private int getWeightPay(PresentationOperator op) {
    	PresentationOperator operator = (PresentationOperator)op;
		return 100 - operator.getWeight();
	}

	@Override
    public String toString() {
        return state.toString();
    }
	
	static int getCost(PresentationState state){
    	List<String> typeList = new ArrayList<>();
		PresentationState s = (PresentationState) state;
		int db = 0;
		Presentation tabla[][] = s.getTable();
		for (int i = 0; i < tabla.length; i++) {
			typeList = new ArrayList<>();
			for (int j = 0; j < tabla[i].length; j++) {
				if (tabla[i][j] != null && !typeList.contains(tabla[i][j].getTopic())) {
					typeList.add(tabla[i][j].getTopic());
				}
			}
			if(typeList!= null){
				db += typeList.size();
			}
			if(db<0){
				db = 0;
			}
		}
		return db;
    }

	public PresentationState getState() {
		return state;
	}

	public void setState(PresentationState state) {
		this.state = state;
	}

	public PresentationOperator getOp() {
		return op;
	}

	public void setOp(PresentationOperator op) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cost;
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Node other = (Node) obj;
		if (cost != other.cost)
			return false;
		if (op == null) {
			if (other.op != null)
				return false;
		} else if (!op.equals(other.op))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
}
