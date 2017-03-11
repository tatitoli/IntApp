package geneticalgorythm;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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
import app.sort.PresentationState;

public class GeneticAlgorithm {
	DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	LinkedList<Parent> parentList;
	LinkedList<Parent> childrenList;
	PresentationProblem p;
	int min;
	PresentationProblem seged;

	public GeneticAlgorithm(PresentationProblem p) {
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
		initalizeStartParents(p.getOperators());
		getCostMap(parentList, p.getOperators());
		Collections.sort(parentList, new Comparator<Parent>(){
			@Override
			public int compare(Parent o1, Parent o2) {
				if(o1.getCost() < o2.getCost()){
			           return -1; 
			        }
			        if(o1.getCost() >  o2.getCost()){
			           return 1; 
			        }
				return 0;
			}
			}); 
//		int index = getLowCost(parentList);
		childrenList.add(parentList.get(0));
		childrenList.add(parentList.get(1));
		boolean change = false;
		int tryred =0;
		while (true) {
			for (int i = 0; i < parentList.size(); i++) {
				if (i % 2 == 0) {
					p1 = parentList.get(i);
					continue;
				} else if (i % 2 == 1) {
					p2 = parentList.get(i);
				}
				if (p1 != null && p2 != null) {
					tmpMap = p1.parentState.getMapTabel();
					for (Map.Entry<String, LinkedList<Integer>> map : tmpMap.entrySet()) {
						tmpList = map.getValue();
						p1List.addAll(map.getValue());
						for (int j = 0; j < tmpList.size(); j++) {
							p1Map.put(tmpList.get(j), map.getKey());
						}
					}
					tmpMap = p2.parentState.getMapTabel();
					for (Map.Entry<String, LinkedList<Integer>> map : tmpMap.entrySet()) {
						tmpList = map.getValue();
						p2List.addAll(map.getValue());
						for (int j = 0; j < tmpList.size(); j++) {
							p2Map.put(tmpList.get(j), map.getKey());
						}
					}
				}
				selectedList = kivalasztas(p1List, p2List);
				childrenMap = keresztezes(p1Map, p2Map, selectedList, p.getSection().getSections());
				if (checkState(childrenMap, p.getOperators(), p.getSection())) {
					PresentationState state = new PresentationState();
					state.setMapTabel(childrenMap);
					Parent parent = new Parent(state);
					childrenList.add(parent);
				}
				p1List = new LinkedList<>();
				p2List = new LinkedList<>();
				p1 = null;
				p2 = null;
				if(childrenList.size() == 10){
					change = true;
					break;
				}
			}
			if(change){
				parentList = new LinkedList<>();
				for (int i = 0; i < childrenList.size(); i++) {
					PresentationState tmpState = childrenList.get(i).getParentState();
					Map<String, LinkedList<Integer>> tmpM = tmpState.getMapTabel();
					Map<String, LinkedList<Integer>> insertMap = new HashMap<>();
					for (Map.Entry<String, LinkedList<Integer>> map : tmpM.entrySet()) {
						insertMap.put(new String(map.getKey()),new LinkedList<>(map.getValue()));
					}
					PresentationState insertState = new PresentationState();
					insertState.setMapTabel(insertMap);
					Parent tmp = new Parent(insertState);
					parentList.add(tmp);
				}
				childrenList = new LinkedList<>();
				change = false;
				getCostMap(parentList, p.getOperators());
				Collections.sort(parentList, new Comparator<Parent>(){
					@Override
					public int compare(Parent o1, Parent o2) {
						if(o1.getCost() < o2.getCost()){
					           return -1; 
					        }
					        if(o1.getCost() >  o2.getCost()){
					           return 1; 
					        }
						return 0;
					}
					}); 
//				index = getLowCost(parentList);
				childrenList.add(parentList.get(0));
				childrenList.add(parentList.get(1));
				tryred++;
			}
			if(tryred == 101){
				return false;
			}
		}
	}

	private int getLowCost(LinkedList<Parent> parentLi) {
		int index = 0;
		int cost = Integer.MAX_VALUE;
		for (int i = 0; i < parentLi.size(); i++) {
			if (parentLi.get(i).getCost() < cost) {
				cost = parentLi.get(i).getCost();
				index = i;
			}
		}
		return index;
	}

	public void getCostMap(LinkedList<Parent> parent, LinkedList<PresentationOperator> operators) {
		Map<Integer, PresentationOperator> presentationMap = new HashMap<Integer, PresentationOperator>();
		for (PresentationOperator operator : operators) {
			presentationMap.put(operator.getId(), operator);
		}
		List<String> typeList = new ArrayList<>();
		int db = 0;
		int cost = 0;
		for (int i = 0; i < parent.size(); i++) {
			cost = 0;
			Map<String, LinkedList<Integer>> temp = parent.get(i).parentState.getMapTabel();
			PresentationOperator presentation = null;
			for (Map.Entry<String, LinkedList<Integer>> entry : temp.entrySet()) {
				db = 0;
				typeList = new ArrayList<>();
				LinkedList<Integer> lista = entry.getValue();
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
				if (typeList != null) {
					db += typeList.size() - 1;
				}
				if (db < 0) {
					db = 0;
				}
				cost += db;
			}
			parent.get(i).setCost(cost);
		}
	}

	private LinkedList<Integer> kivalasztas(LinkedList<Integer> p1List, LinkedList<Integer> p2List) {
		LinkedList<Integer> tmpList = new LinkedList<>();
		LinkedList<Integer> reverseList = new LinkedList<>();
		for (int i = 0; i < p2List.size(); i++) {
			reverseList.addFirst(p2List.get(i));
		}
		for (int i = 0; i < p1List.size(); i++) {
			if (i % 2 == 0 && !tmpList.contains(p1List.get(i))) {
				tmpList.addFirst(p1List.get(i));
			}
			if (i % 2 == 1 && !tmpList.contains(reverseList.get(i))) {
				tmpList.addLast(reverseList.get(i));
			}
		}
		if (!p1List.contains(tmpList)) {
			for (int i = 0; i < p1List.size(); i++) {
				if (!tmpList.contains(p1List.get(i))) {
					tmpList.add(p1List.get(i));
				}
			}
		}
		return tmpList;

	}

	public void initalizeStartParents(LinkedList<PresentationOperator> operators) {
		parentList = new LinkedList<>();
		Random randomGenerator = new Random();
		while (parentList.size() < 10) {
			LinkedList<PresentationOperator> tmpOperators = new LinkedList<>();
			for (PresentationOperator op : operators) {
				tmpOperators.add(new PresentationOperator(op.getId(), op.getPresentationTitle(), op.getActor(),
						op.getTopic(), op.getInter(), op.getFrom(), op.getTo(), op.isPiority(), op.getWeight()));
			}
			PresentationState tmpState = new PresentationState(operators, p.getSection().getSections());
			Map<String, LinkedList<Integer>> stateMap = tmpState.getMapTabel();
			while (!tmpOperators.isEmpty()) {
				int randomInt = randomGenerator.nextInt(p.getSection().getSections().size());
				String insertStr = p.getSection().getSections().get(randomInt);
				for (Map.Entry<String, LinkedList<Integer>> entry : stateMap.entrySet()) {
					if (entry.getKey().contains(insertStr)) {
						insertStr = entry.getKey();
						break;
					}
				}
				LinkedList<Integer> listId = stateMap.get(insertStr);
				listId.add(tmpOperators.removeFirst().getId());
				stateMap.put(insertStr, listId);
			}
			if (tmpState.checkState(stateMap, operators, p.getSection())) {
				parentList.add(new Parent(tmpState));
			}
		}
	}

	private void mutacio() {

	}

	private Map<String, LinkedList<Integer>> keresztezes(Map<Integer, String> p1Map, Map<Integer, String> p2Map,
			LinkedList<Integer> selectedList, LinkedList<String> sections) {
		Map<String, LinkedList<Integer>> tmpMap = new HashMap<>();
//		for (int i = 0; i < sections.size(); i++) {
//			tmpMap.put(sections.get(i), new LinkedList<>());
//		}
		for (Map.Entry<Integer, String> entry : p1Map.entrySet()) {
			{
				if (!tmpMap.containsKey(entry.getValue())) {
					tmpMap.put(entry.getValue(), new LinkedList<>());
				}
			}
		}
		for (Map.Entry<Integer, String> entry : p2Map.entrySet()) {
			{
				if (!tmpMap.containsKey(entry.getValue())) {
					tmpMap.put(entry.getValue(), new LinkedList<>());
				}
			}
		}
		// int border =
		// selectedList.size()/p.getSection().getSectionNumber();
		int border = selectedList.size() / 2;
		// boolean up = true;
		for (int i = 0; i < selectedList.size(); i++) {
			if (i < border) {
				String section = p1Map.get(selectedList.get(i));
				LinkedList<Integer> tmpList = tmpMap.get(section);
				tmpList.add(selectedList.get(i));
				tmpMap.put(section, tmpList);
			}
			if (i >= border) {
				String section = p2Map.get(selectedList.get(i));
				LinkedList<Integer> tmpList = tmpMap.get(section);
				tmpList.add(selectedList.get(i));
				tmpMap.put(section, tmpList);
			}
		}
		return tmpMap;
	}
	
	public boolean checkState(Map<String, LinkedList<Integer>> stateMap, LinkedList<PresentationOperator> operators, Section section) {
		LocalTime localtime = null;
		for (Map.Entry<String, LinkedList<Integer>> entry : stateMap.entrySet()) {
			LinkedList<Integer> idList = entry.getValue();
			localtime = LocalTime.parse(section.getFrom(), formatter);
			PresentationOperator actualPresentation = null;
			for (int i = 0; i < idList.size(); i++) {
				for (PresentationOperator operator : operators) {
					if (idList.contains(operator.getId())) {
						actualPresentation = operator;
						break;
					}
				}
				String[] from = actualPresentation.getFrom().split(" ");
				String[] to = actualPresentation.getTo().split(" ");
				LocalTime fromLT = LocalTime.parse(from[3], formatter);
				fromLT = fromLT.plusNanos(1);
				LocalTime toLT = LocalTime.parse(to[3], formatter);
				if (localtime.isBefore(fromLT) || localtime.isBefore(toLT)) {
					String tmpInter = actualPresentation.getInter();
					String[] interArray = tmpInter.split("\\.");
					localtime = localtime.plusMinutes(Integer.parseInt(interArray[0]));
				}
				else{
					return false;
				}
			}
		}
		return true;
	}
	
	public Map<String, LinkedList<Integer>> GetMapTabla() {
		return childrenList.get(0).getParentState().getMapTabel();
	}
}
