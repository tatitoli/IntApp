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
            this.cost = parent == null ? 0 : getCost(state);
            this.op = op;
            this.parent = parent;
            this.state = state;
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
                if (op.isApplicable(node.state,op)) {
                    State newState = op.apply(node.state, op);
//                    if (search(openNodes, newState) != null) {
//                        frissithajobb(node, op, newState);//megkapja a sz�l� cs�csot, az el��l��t� oper�tort
//                        // �s hogy melyik �llapot �lt el�
//                    }
//                    if (search(closedNodes, newState) != null) {
//                        continue;
//                    }
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

//    void frissithajobb(Node parentNode, Operator op, State s) {
//        int k=0;//ha jobb utat tal�ltam ugyanahoz a cs�cshoz akkor a list�ban fel�l�rom azt a cs�csot
//        //�rai jegyzetemben �gy van hogy m�dos�tjuk az el��ll�t� oper�tor�t �s a sz�l��t 
//        // k�lts�get meg a konstrukt�r �gy is �jrasz�molja, sz�val nekem �gy �tl�that�bb
//        for (Iterator<Node> it = openNodes.iterator(); it.hasNext();) {
//            Node n = it.next();
//            if (n.state.equals(parentNode.state) && n.roadCost > parentNode.roadCost + p.cost(op, parentNode.state)) {
//                it.remove();
//                openNodes.add(k,new Node(s, op, parentNode));k++;
//                
//            }
//
//        }
//    }
   
    
    private static int getCost(State state){
    	List<Integer> typeList = new ArrayList<>();
		PresentationState s = (PresentationState) state;
		Map<Integer, List<Presentation>> actualEvent = new HashMap<>();
		List<Presentation> presentations = new ArrayList<>();
		int db = 0;
		actualEvent = s.getEvent();
		for (Map.Entry<Integer, List<Presentation>> entry : actualEvent.entrySet()) {
			presentations = entry.getValue();
			typeList = new ArrayList<>();
			for (int i = 0; i < presentations.size(); i++) {
				if (!typeList.contains(presentations.get(i).getTopic())) {
					typeList.add(presentations.get(i).getTopic());
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
