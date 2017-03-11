/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Optimal {

	PresentationProblem p;
	int min;
	PresentationProblem seged;

	public Optimal(PresentationProblem p) {
		this.p = p;
	}

	public Optimal(PresentationProblem p, int min) {
		this.p = p;
		this.min = min;
	}

	private LinkedList<Node> openNodes;
	private LinkedList<Node> closedNodes;
	private Node node;

	public boolean run() {
		closedNodes = new LinkedList<>();
		openNodes = new LinkedList<>();
//		openNodes.add(new Node(p.startState(), null, null, null));
		openNodes.add(new Node(p.startMapState(PresentationProblem.operators), null, null, null));
		while (true) {
			if (openNodes.isEmpty()) {
				return false;
			}

			node = openNodes.remove(getLowCostNode(openNodes));
			if (node.state.isGoalMap(p)) {
				return true;
			}

			closedNodes.add(node);
			for (PresentationOperator op : p.operators()) {
				if (op.isPiority() == true) {
					LinkedList<Integer> prioList = getPiorityList(node.state);
					if (!op.isApplicable(node.state, op, PresentationProblem.operators) && !prioList.contains(op.getId())) {
						break;
					}
				}
//				if (op.isApplicable(node.state, op, PresentationProblem.operators)) {
				if (op.isApplicableMap(node.state, op, PresentationProblem.operators,p.getSection())) {	
					PresentationState newState = op.applyMap(node.state, op, p.getSection(), PresentationProblem.operators);
					if (newState != null) {
						if (searchMap(closedNodes, newState)) {
							continue;
						}
						Node newNode = null;
						newNode = new Node(newState, op, node, PresentationProblem.operators);
						if(newNode.typecost > 3){
							continue;
						}
						openNodes.add(newNode);
					}
				}
			}
		}
	}

	private LinkedList<Integer> getPiorityList(PresentationState state) {
		int temp[][] = state.getTable();
		LinkedList<Integer> tempList = new LinkedList<>();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				if(temp[i][j]!=0){
					tempList.add(temp[i][j]);
				}
			}
		}
		return tempList;
	}

	private int getLowCostNode(LinkedList<Node> openNodes) {
		int index = 0, i = 0;
		int cost = Integer.MAX_VALUE;
		int stateCost = 0;
		for (Node node : openNodes) {
			stateCost = node.getCost();
			if (cost > stateCost) {
				cost = stateCost;
				index = i;
			}
			i++;
		}
		return index;
	}
	
	private boolean search(List<Node> nodeList, PresentationState state) {
		int[][] actualState = state.getTable();
		ArrayList<Integer> nodeArrayList = new ArrayList<>();
		ArrayList<Integer> stateArrayList = new ArrayList<>();
		for (Node node : nodeList) {
			int[][]nodeState = node.getState().getTable();
			for (int i = 0; i < nodeState.length; i++) {
				nodeArrayList = new ArrayList<>();
				stateArrayList = new ArrayList<>();
				for (int j = 0; j < nodeState[i].length; j++) {
					if(nodeState[i][j] != 0){
						nodeArrayList.add(nodeState[i][j]);
					}
					if(actualState[i][j] != 0){
						stateArrayList.add(actualState[i][j]);
					}
				}
				if(nodeArrayList.contains(stateArrayList)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean searchMap(List<Node> nodeList, PresentationState state) {
		Map<String, LinkedList<Integer>> actualMap = state.getMapTabel();
		for (Node node : nodeList) {
			Map<String, LinkedList<Integer>> nodeStateMap = node.getState().getMapTabel();
			if(nodeStateMap.equals(actualMap)){
				return true;
			}
		}
		return false;
	}

	public String getGoal() {
		return (String) node.state.getGoal();
	}

	public int[][] GetTabla() {
		return node.state.getTable();
	}
	
	public Map<String, LinkedList<Integer>> GetMapTabla() {
		return node.state.getMapTabel();
	}
}
