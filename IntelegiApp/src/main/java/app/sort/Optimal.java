/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.sort;

import java.util.LinkedList;
import java.util.List;

public class Optimal {
    
    PresentationProblem p;
    PresentationProblem seged;
    public Optimal(PresentationProblem p) {
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
            for (PresentationOperator op : p.operators()) {
            	PresentationOperator operator = (PresentationOperator)op;
            	if(operator.isPiority() == true){
            		if (!op.isApplicable(node.state,op)) {
            			break;
            		}
            	}
                if (op.isApplicable(node.state,op)) {
                	PresentationState newState = op.apply(node.state, op, closedNodes);        
                    if (search(closedNodes, newState) != null) {
                        continue;
                    }
                    Node newNode = null;
                    newNode = new Node(newState, op, node);
                    openNodes.addLast(newNode);
                }
            }
        }
    }

    private int getLowCostNode(LinkedList<Node> openNodes) {
		int index = 0,i=0; 
		int cost = Integer.MAX_VALUE;
		int stateCost = 0;
		for (Node node : openNodes) {
//			PresentationState state = (PresentationState) node.state;
			stateCost = node.getCost();
//			stateCost = Node.getCost(state);
			if(cost > stateCost){
				cost = stateCost;
				index = i;
			}
			i++;
		}
		return index;
	}

	private Node search(List<Node> nodeList, PresentationState state) {
        for (Node node : nodeList) {
            if (state.equals(node.state)) {
                return node;
            }
        }
        return null;
    }
    
    public String getGoal() {
		return (String) node.state.getGoal();
	}
    
    public Presentation[][] GetTabla() {
		return ((PresentationState) node.state).getTable();
	}
}
