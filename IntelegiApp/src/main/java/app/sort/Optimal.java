/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Optimal {

    private static class Node {
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
    }
    
    Problem p;
    Problem seged;
    public Optimal(Problem p) {
        this.p = p;
    }
    private LinkedList<Node> openNodes;
    private LinkedList<Node> closedNodes;
    private Node node;

    public boolean run() {
        closedNodes = new LinkedList<>();
        openNodes = new LinkedList<>();
        openNodes.add(new Node(p.startState(), null, null));
        while (true) {
            if (openNodes.isEmpty()) {
                return false;
            }
            
            node = openNodes.remove(getLowCostNode(openNodes));
            if (node.state.isGoal(p)){
				return true;
			}
         
            closedNodes.add(node);
            for (Operator op : p.operators()) {
            	PresentationOperator operator = (PresentationOperator)op;
            	if(operator.isPiority() == true){
            		if (!op.isApplicable(node.state,op)) {
            			break;
            		}
            	}
                if (op.isApplicable(node.state,op)) {
                    State newState = op.apply(node.state, op);        
                    if (search(closedNodes, newState) != null) {
                        continue;
                    }
                    openNodes.addLast(new Node(newState, op, node));
                }
            }


        }
    }

    private int getLowCostNode(LinkedList<Node> openNodes) {
		int index = 0,i=0; 
		int cost = Integer.MAX_VALUE;
		int stateCost = 0;
		for (Node node : openNodes) {
			PresentationState state = (PresentationState) node.state;
			stateCost = getCost(state);
			if(cost > stateCost){
				cost = stateCost;
				index = i;
			}
			i++;
		}
		return index;
	}

	private Node search(List<Node> nodeList, State state) {
        for (Node node : nodeList) {
            if (state.equals(node.state)) {
                return node;
            }
        }
        return null;
    }
    
    private static int getCost(State state){
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
    
    public String getGoal() {
		return (String) node.state.getGoal();
	}
}
