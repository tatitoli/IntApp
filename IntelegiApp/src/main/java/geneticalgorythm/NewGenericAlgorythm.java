package geneticalgorythm;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import app.model.Section;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;

public class NewGenericAlgorythm {
	DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	LinkedList<NewParent> parentList;
	LinkedList<Parent> originalParentList = new LinkedList<>();
	LinkedList<NewParent> childrenList;
	PresentationProblem p;
	boolean first = true;
	int target = 0;
	int min;
	PresentationProblem seged;
	Map<String, LinkedList<Integer>> actualMap = new HashMap<>();

	public NewGenericAlgorythm(PresentationProblem p) {
		this.p = p;
	}

	public boolean run() {
		target++;
		childrenList = new LinkedList<>();
		initalizeStartParents(p.getOperators(), p.getSection());
		getCostMap(parentList, p.getOperators(), p.getSection());
		calcFitness(parentList);
//		Collections.sort(parentList, (a, b) -> a.getCost().compareTo(b.getCost()));
		while (true) {
			target++;
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100);
			Random r = new Random();
			double randomValue = 0 + (100 - 0) * r.nextDouble();
			Double sum = 0.0;
			Map<String, LinkedList<Integer>> child1 = new HashMap<>();
			Map<String, LinkedList<Integer>> child2 = new HashMap<>();
			for (NewParent parent : parentList) {
				sum += parent.getFitness();
				if (sum > randomValue) {
					child1 = parent.getStateMap();
					break;
				}
			}
			randomValue = 0 + (100 - 0) * r.nextDouble();
			sum = 0.0;
			for (NewParent parent : parentList) {
				sum += parent.getFitness();
				if (sum > randomValue) {
					child2 = parent.getStateMap();
					break;
				}
			}
			keresztezes(child1, child2, p.operators());
			if (childrenList.size() >= 10) {
				target++;
				for (NewParent newParent : childrenList) {
					parentList.add(new NewParent(newParent.getStateMap(), null));
				}
				getCostMap(parentList, p.getOperators(), p.getSection());
				Collections.sort(parentList, (a, b) -> a.getCost().compareTo(b.getCost()));
				for(int i=0;i<10;i++){
					parentList.removeLast();
				}
				calcFitness(parentList);
				childrenList = new LinkedList<>();
			}
			if(target>=10000){
				return true;
			}
		}
	}

	private void keresztezes(Map<String, LinkedList<Integer>> child1, Map<String, LinkedList<Integer>> child2,
			Collection<PresentationOperator> operators) {
		int i = 0;
		Map<String, LinkedList<Integer>> temp1Map = new HashMap<>();
		Map<String, LinkedList<Integer>> temp2Map = new HashMap<>();
		for (PresentationOperator presentationOperator : operators) {
			if (i <= operators.size() / 2) {
				for (Map.Entry<String, LinkedList<Integer>> entry : child2.entrySet()) {
					LinkedList<Integer> lista = entry.getValue();
					if (lista != null && lista.contains(presentationOperator.getId())) {
						if (temp1Map.get(entry.getKey()) != null) {
							LinkedList<Integer> tmpList = temp1Map.get(entry.getKey());
							tmpList.add(presentationOperator.getId());
							temp1Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						} else {
							LinkedList<Integer> tmpList = new LinkedList<>();
							tmpList.add(presentationOperator.getId());
							temp1Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						}
					}
				}
				for (Map.Entry<String, LinkedList<Integer>> entry : child1.entrySet()) {
					LinkedList<Integer> lista = entry.getValue();
					if (lista != null && lista.contains(presentationOperator.getId())) {
						if (temp2Map.get(entry.getKey()) != null) {
							LinkedList<Integer> tmpList = temp2Map.get(entry.getKey());
							tmpList.add(presentationOperator.getId());
							temp2Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						} else {
							LinkedList<Integer> tmpList = new LinkedList<>();
							tmpList.add(presentationOperator.getId());
							temp2Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						}
					}
				}
			}
			if (i > operators.size() / 2) {
				for (Map.Entry<String, LinkedList<Integer>> entry : child2.entrySet()) {
					LinkedList<Integer> lista = entry.getValue();
					if (lista != null && lista.contains(presentationOperator.getId())) {
						if (temp2Map.get(entry.getKey()) != null) {
							LinkedList<Integer> tmpList = temp2Map.get(entry.getKey());
							tmpList.add(presentationOperator.getId());
							temp2Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						} else {
							LinkedList<Integer> tmpList = new LinkedList<>();
							tmpList.add(presentationOperator.getId());
							temp2Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						}
					}
				}
				for (Map.Entry<String, LinkedList<Integer>> entry : child1.entrySet()) {
					LinkedList<Integer> lista = entry.getValue();
					if (lista != null && lista.contains(presentationOperator.getId())) {
						if (temp1Map.get(entry.getKey()) != null) {
							LinkedList<Integer> tmpList = temp1Map.get(entry.getKey());
							tmpList.add(presentationOperator.getId());
							temp1Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						} else {
							LinkedList<Integer> tmpList = new LinkedList<>();
							tmpList.add(presentationOperator.getId());
							temp1Map.put(new String(entry.getKey()), new LinkedList<>(tmpList));
							break;
						}
					}
				}
			}
			i++;
		}
		 Random rand = new Random();
		 int randomNumber = rand.nextInt(100) + 1;
		 if (randomNumber <= 10) {
			 temp1Map = mutacio(temp1Map);
		 }
		 randomNumber = rand.nextInt(100) + 1;
		 if (randomNumber <= 10) {
			 temp2Map = mutacio(temp2Map);
		 }
		if (checkState(temp1Map, p.getOperators())) {
			childrenList.add(new NewParent(new HashMap<>(temp1Map), null));
		}if (checkState(temp2Map, p.getOperators())) {
			childrenList.add(new NewParent(new HashMap<>(temp2Map), null));
		}
	}

	private void calcFitness(LinkedList<NewParent> parentList) {
		Double actualCost = 0.0;
		Double sumFitness = 0.0;
		for (int i = 0; i < parentList.size(); i++) {
			sumFitness += 1.0 / Double.parseDouble(parentList.get(i).getCost().toString());
		}
		for (int i = 0; i < parentList.size(); i++) {
			actualCost = 1.0 / Double.parseDouble(parentList.get(i).getCost().toString());
			parentList.get(i).setFitness(((actualCost / sumFitness) * 100.0));
		}
	}

	public void getCostMap(LinkedList<NewParent> parent, LinkedList<PresentationOperator> operators, Section section) {
		List<String> typeList = new ArrayList<>();
		Map<String, Integer> typeCostMap = new HashMap<>();
		for (PresentationOperator operator : operators) {
			typeCostMap.put(operator.getTopic(), -1);
		}
		typeList = new ArrayList<>();
		int sumDb = 0;
		int db = 0;
		int cost = 0;
		for (int i = 0; i < parent.size(); i++) {
			cost = 0;
			Map<String, LinkedList<Integer>> temp = parent.get(i).getStateMap();
			PresentationOperator presentation = null;
			sumDb = 0;
			for (Map.Entry<String, LinkedList<Integer>> entry : temp.entrySet()) {
				typeList = new ArrayList<>();
				LinkedList<Integer> lista = entry.getValue();
				if (lista != null) {
					for (int j = 0; j < lista.size(); j++) {
						for (PresentationOperator o : operators) {
							if (lista.get(j) == o.getId()) {
								presentation = o;
								break;
							}
						}
						if (!typeList.contains(presentation.getTopic())) {
							typeList.add(presentation.getTopic());
						}
					}
				}
				if (typeList != null) {
					db += typeList.size() - 1;
				}
				if (db < 0) {
					db = 0;
				}
				cost += db;
				db = 0;
			}
			sumDb += cost;
			cost = 0;
			parent.get(i).setCost((sumDb));
		}
	}

	public void initalizeStartParents(LinkedList<PresentationOperator> operators, Section section) {
		Random randomGenerator = new Random();
		parentList = new LinkedList<>();
		while (parentList.size() < 10) {
			int count = 0;
			Map<String, LinkedList<Integer>> tmpMap = new HashMap<>();
			for (int i = 0; i < section.getSections().size(); i++) {
				tmpMap.put(section.getSections().get(i) + "DE", null);
				tmpMap.put(section.getSections().get(i) + "DU", null);
			}
			for (PresentationOperator presentationOperator : operators) {
				int randomInt = randomGenerator.nextInt(tmpMap.size());
				count = 0;
				for (Map.Entry<String, LinkedList<Integer>> entry : tmpMap.entrySet()) {
					if (count == randomInt) {
						if (tmpMap.get(entry.getKey()) != null) {
							LinkedList<Integer> tmpList = new LinkedList<>();
							tmpList = tmpMap.get(entry.getKey());
							tmpList.add(presentationOperator.getId());
							tmpMap.put(entry.getKey(), tmpList);
						} else {
							LinkedList<Integer> tmpList = new LinkedList<>();
							tmpList.add(presentationOperator.getId());
							tmpMap.put(entry.getKey(), tmpList);
						}
						break;
					} else {
						count++;
					}
				}
			}
			if (checkState(tmpMap, operators)) {
				parentList.add(new NewParent(tmpMap, null));
			}
		}
	}

	private boolean checkState(Map<String, LinkedList<Integer>> tmpMap, LinkedList<PresentationOperator> operators) {
		LocalTime localtime = null;
		LocalTime localtimeEnd = null;
		LocalTime localtimeDel = null;
		LocalTime localtime13 = null;
		for (Map.Entry<String, LinkedList<Integer>> entry : tmpMap.entrySet()) {
			LinkedList<Integer> tmpList = entry.getValue();
			if (tmpList != null) {
				localtime = LocalTime.parse(p.getSection().getFrom(), formatter);
				localtimeEnd = LocalTime.parse(p.getSection().getTo(), formatter);
				localtimeDel = LocalTime.parse("12:00", formatter);
				localtime13 = LocalTime.parse("13:00", formatter);
				for (Integer integer : tmpList) {
					for (PresentationOperator presentationOperator : operators) {
						if (integer == presentationOperator.getId()) {
							if (entry.getKey().contains("DE")) {
								if("DU".equals(presentationOperator.getFrom())){
									return false;
								}
								String tmpInter = presentationOperator.getInter();
								String[] interArray = tmpInter.split("\\.");
								localtime = localtime.plusMinutes(Integer.parseInt(interArray[0]));
								if(!localtime.isBefore(localtimeDel)){
									return false;
								}
							} else if (entry.getKey().contains("DU")) {
								if("DE".equals(presentationOperator.getFrom())){
									return false;
								}
								String tmpInter = presentationOperator.getInter();
								String[] interArray = tmpInter.split("\\.");
								localtime13 = localtime13.plusMinutes(Integer.parseInt(interArray[0]));
								if(!localtime13.isBefore(localtimeEnd)){
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	private Map<String, LinkedList<Integer>> mutacio(Map<String, LinkedList<Integer>> childrenMap) {
		Map<String, LinkedList<Integer>> actualMap = new HashMap<>();
		Random rand = new Random();
		int from = p.getOperators().size();
		int randomNumber = rand.nextInt(from);
		PresentationOperator op1 = p.getOperators().get(randomNumber);
		randomNumber = rand.nextInt(from);
		PresentationOperator op2 = p.getOperators().get(randomNumber);
		for (Map.Entry<String, LinkedList<Integer>> entry : childrenMap.entrySet()) {
			LinkedList<Integer> tmpList = entry.getValue();
			if(tmpList!= null){
				int index = 0;
				if(tmpList.contains(op1.getId())){
					for(int i = 0;i<tmpList.size();i++){
						if(tmpList.get(i).equals(op1.getId())){
							index = i;
						}
					}
					tmpList.remove(index);
					tmpList.add(op2.getId());
					childrenMap.put(new String(entry.getKey()), new LinkedList<>(tmpList));
				}if(tmpList.contains(op2.getId())){
					for(int i = 0;i<tmpList.size();i++){
						if(tmpList.get(i).equals(op2.getId())){
							index = i;
						}
					}
					tmpList.remove(index);
					tmpList.add(op1.getId());
					childrenMap.put(new String(entry.getKey()), new LinkedList<>(tmpList));
				}
			}
		}
		for (Map.Entry<String, LinkedList<Integer>> entry : childrenMap.entrySet()) {
			if (!actualMap.containsKey(entry.getKey())) {
				actualMap.put(new String(entry.getKey()), new LinkedList<>(entry.getValue()));
			}
		}
		return actualMap;
	}

	public Map<String, LinkedList<Integer>> GetMapFullTabla() {
		return parentList.getFirst().getStateMap();
	}
	
	class CostComparator implements Comparator<NewParent> {
	    @Override
	    public int compare(NewParent a, NewParent b) {
	        return a.getCost() < b.getCost() ? -1 : a.getCost() == b.getCost() ? 0 : 1;
	    }

	}
}
