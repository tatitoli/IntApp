package geneticalgorythm;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import app.model.DateOperator;
import app.model.Section;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
import app.sort.PresentationState;

public class NewGenericAlgorythm {
	DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	LinkedList<NewParent> parentList;
	LinkedList<Parent> originalParentList = new LinkedList<>();
	LinkedList<NewParent> childrenList;
	PresentationProblem p;
	boolean first = true;
	int min;
	PresentationProblem seged;
	Map<String, LinkedList<Integer>> actualMap = new HashMap<>();

	public NewGenericAlgorythm(PresentationProblem p) {
		this.p = p;
	}

	public boolean run() {
		childrenList = new LinkedList<>();
		LinkedList<Integer> selectedList = new LinkedList<>();
		Map<Integer, String> p1Map = new HashMap<>();
		Map<Integer, String> p2Map = new HashMap<>();
		Parent p1 = null;
		Parent p2 = null;
		LinkedList<Integer> p1List = new LinkedList<>();
		LinkedList<Integer> p2List = new LinkedList<>();
		LinkedList<Integer> tmpList = null;
		Map<String, LinkedList<Integer>> tmpMap = new HashMap<>();
		Map<String, LinkedList<Integer>> childrenMap = new HashMap<>();
		initalizeStartParents(p.getOperators(), p.getSection());
		getCostMap(parentList, p.getOperators(), p.getSection());
		calcFitness(parentList);
		Collections.sort(parentList, (a, b) -> a.getCost().compareTo(b.getCost()));
		// childrenList.add(parentList.get(0));
		// childrenList.add(parentList.get(1));
		// for (int i = 0; i < parentList.size(); i++) {
		// originalParentList.add(parentList.get(i));
		// }
		// boolean change = false;
		// int tryred = 0;
		// boolean checkFalse = false;
		while (true) {
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100);
			int sum = 0;
			Map<String, LinkedList<Integer>> child1 = new HashMap<>();
			Map<String, LinkedList<Integer>> child2 = new HashMap<>();
			for (NewParent parent : parentList) {
				sum += parent.getFitness();
				if (sum > randomInt) {
					child1 = parent.getStateMap();
					break;
				}
			}
			randomInt = randomGenerator.nextInt(100);
			sum = 0;
			for (NewParent parent : parentList) {
				sum += parent.getFitness();
				if (sum > randomInt) {
					child2 = parent.getStateMap();
					break;
				}
			}
			keresztezes(child1, child2, p.operators());
			if(childrenList.size()>=10){
				getCostMap(childrenList, p.getOperators(), p.getSection());
				parentList.addAll(childrenList);
//				Collections.sort(parentList, (a, b) -> a.getCost().compareTo(b.getCost()));
				childrenList = new LinkedList<>();
			}
			
		}
	}

	private void keresztezes(Map<String, LinkedList<Integer>> child1, Map<String, LinkedList<Integer>> child2,
			Collection<PresentationOperator> operators) {
		int i = 0;
		Map<String, LinkedList<Integer>> temp1Map = new HashMap<>();
		Map<String, LinkedList<Integer>> temp2Map = new HashMap<>();
		for (PresentationOperator presentationOperator : operators) {
			if (i < operators.size() / 2) {
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
			if (i >= operators.size() / 2) {
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
//		Random rand = new Random();
//		int randomNumber = rand.nextInt(100) + 1;
//		if (randomNumber <= 10) {
//			temp1Map = mutacio(temp1Map);
//		}
//		randomNumber = rand.nextInt(100) + 1;
//		if (randomNumber <= 10) {
//			temp2Map = mutacio(temp2Map);
//		}
		childrenList.add(new NewParent(temp1Map, null));
		childrenList.add(new NewParent(temp2Map, null));
	}

	private void calcFitness(LinkedList<NewParent> parentList) {
		Double actualCost = 0.0;
		Double sumFitness = 0.0;
		for (int i = 0; i < parentList.size(); i++) {
			sumFitness += 1.0 / Double.parseDouble(parentList.get(i).getCost().toString());
		}
		for (int i = 0; i < parentList.size(); i++) {
			actualCost = 1.0 / Double.parseDouble(parentList.get(i).getCost().toString());
			parentList.get(i).setFitness((int) ((actualCost / sumFitness) * 100));
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
			parentList.add(new NewParent(tmpMap, null));
		}
	}

	private boolean checkState(Map<String, LinkedList<Integer>> stateMap, LinkedList<PresentationOperator> operators,
			Section section, LinkedList<String> dayList) {
		boolean hasNullSection = false;
		LocalTime localtime = null;
		for (int d = 0; d < dayList.size(); d++) {
			for (Map.Entry<String, LinkedList<Integer>> entry : stateMap.entrySet()) {
				if (entry.getKey().contains(dayList.get(d))) {
					LinkedList<Integer> idList = entry.getValue();
					if (idList.isEmpty()) {
						hasNullSection = true;
					}
					localtime = LocalTime.parse(section.getFrom(), formatter);
					PresentationOperator actualPresentation = null;
					for (int i = 0; i < idList.size(); i++) {
						for (PresentationOperator operator : operators) {
							if (idList.get(i).equals(operator.getId())) {
								actualPresentation = operator;
								break;
							}
						}
						String[] from = actualPresentation.getFrom().split(" ");
						String[] to = actualPresentation.getTo().split(" ");
						LocalTime fromLT = LocalTime.parse(from[3], formatter);
						// fromLT = fromLT.plusNanos(1);
						LocalTime toLT = LocalTime.parse(to[3], formatter);
						if (localtime.isBefore(fromLT) || localtime.isBefore(toLT)) {
							String tmpInter = actualPresentation.getInter();
							String[] interArray = tmpInter.split("\\.");
							localtime = localtime.plusMinutes(Integer.parseInt(interArray[0]));
						} else {
							return false;
						}
					}
				}
				if (hasNullSection) {
					return false;
				}
			}
		}
		return true;
	}

	private Map<String, LinkedList<Integer>> mutacio(Map<String, LinkedList<Integer>> childrenMap) {
		Map<String, LinkedList<Integer>> actualMap = new HashMap<>();
		String maxSection = "";
		int maxSectionNumber = Integer.MIN_VALUE;
		int minSectionNumber = Integer.MAX_VALUE;
		String minSection = "";
		// Map<String, Integer> sectionCostMap = new HashMap<String, Integer>();
		for (Map.Entry<String, LinkedList<Integer>> entry : childrenMap.entrySet()) {
			// sectionCostMap.put(entry.getKey(), entry.getValue().size());
			if (entry.getValue().size() < minSectionNumber) {
				minSection = entry.getKey();
				minSectionNumber = entry.getValue().size();
			}
			if (entry.getValue().size() > maxSectionNumber) {
				maxSection = entry.getKey();
				maxSectionNumber = entry.getValue().size();
			}
		}
		LinkedList<Integer> minList = new LinkedList<>();
		LinkedList<Integer> maxList = new LinkedList<>();
		minList = childrenMap.get(minSection);
		maxList = childrenMap.get(maxSection);
		for (int i = maxList.size() / 2; i < maxList.size(); i++) {
			minList.add(maxList.remove(i));
		}
		actualMap.put(minSection, minList);
		actualMap.put(maxSection, maxList);
		for (Map.Entry<String, LinkedList<Integer>> entry : childrenMap.entrySet()) {
			if (!actualMap.containsKey(entry.getKey())) {
				actualMap.put(new String(entry.getKey()), new LinkedList<>(entry.getValue()));
			}
		}
		return actualMap;
	}
}
