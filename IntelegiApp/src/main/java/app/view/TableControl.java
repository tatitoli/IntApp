package app.view;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import app.daoimp.PresentationsDaoImp;
import app.model.AppButton;
import app.model.Section;
import app.sort.Presentation;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
import geneticalgorythm.NewGenericAlgorythm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TableControl {

	DateTimeFormatter formatterDateTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	LinkedList<PresentationOperator> operatorsTab = new LinkedList<>();
	Section sectionTab;
	int[][] intTable;
	Map<String, LinkedList<Presentation>> finalTable;
	Map<String, LinkedList<Presentation>> exportTable;
	LinkedList<Presentation[][]> tableList;
	PresentationsDaoImp daoImp = new PresentationsDaoImp();

	@FXML
	Button changeButton;

	@FXML
	Button reGenerate;

	@FXML
	private Label title;

	@FXML
	private Label time;

	@FXML
	private Label presentatior;

	@FXML
	private Label topic;

	@FXML
	private CheckBox piority;

	@FXML
	private TextField weight;

	private int presentationId;

	public int getPresentationId() {
		return presentationId;
	}

	public void setPresentationId(int presentationId) {
		this.presentationId = presentationId;
	}

	GridPane gridTermek = new GridPane();
	GridPane gridOneDele = new GridPane();
	GridPane gridOneDelu = new GridPane();
	GridPane gridTwoDele = new GridPane();
	GridPane gridTwoDelu = new GridPane();
	GridPane gridThreeDele = new GridPane();
	GridPane gridThreeDelu = new GridPane();

	GridPane gridOne = new GridPane();
	GridPane gridTwo = new GridPane();

	Presentation[][] tableMod;
	Button[] termek;
	AppButton[][] tableDelelott;
	AppButton[][] tableDelutan;

	Tab tab1;
	Tab tab2;
	Tab tab3;
	Tab tab4;
	@FXML
	TabPane tabPane;

	@FXML
	Button delelottButton;

	@FXML
	Button delutanButton;

	public void initialize() {
//		Tab tab1 = new Tab();
//		tab1.setText("Elsõ nap");
		// Tab tab2 = new Tab();
		// tab2.setText("Második nap");
		// Tab tab3 = new Tab();
		// tab3.setText("Haramadik nap");
		// Tab tab4 = new Tab();
		// tab4.setText("Negyedik nap");
		// Tab tab5 = new Tab();
		// tab5.setText("Ötödik nap");
//		GridPane TabgridPane = new GridPane();
//		delelottButton = new Button("Délelõtt");
//		TabgridPane.add(delelottButton, 0, 0);
//		TabgridPane.add(gridOne, 0, 1);
//		delutanButton = new Button("Délután");
//		TabgridPane.add(delutanButton, 0, 2);
//		TabgridPane.add(gridTwo, 0, 3);
//		tab1.setContent(TabgridPane);
//		tabPane.getTabs().add(tab1);
	}

//	public void setFirstGrid(Presentation[][] table, LinkedList<PresentationOperator> operators, Section section,
//			Map<Integer, String> idWithSection) {
//		tableMod = table;
//		operatorsTab = operators;
//		sectionTab = section;
//		tableMod = new Presentation[table.length][table[0].length];
//		tableDelelott = new AppButton[table.length][table[0].length];
//		tableDelutan = new AppButton[table.length][table[0].length];
//		AppButton[][] buttonTable = new AppButton[table.length][table[0].length];
//		for (int i = 0; i < table.length; i++) {
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					buttonTable[i][j] = new AppButton(i);
//					buttonTable[i][j].setPresentationId(table[i][j].getId());
//					buttonTable[i][j].setText(
//							table[i][j].getTopic() + "\n" + table[i][j].getFrom() + " - " + table[i][j].getTo());
//					buttonTable[i][j].setPrefSize(100, 75);
//					buttonTable[i][j].setTextAlignment(TextAlignment.CENTER);
//					tableMod[i][j] = table[i][j];
//
//				}
//			}
//		}
//		for (int i = 0; i < buttonTable.length; i++) {
//			for (int j = 0; j < buttonTable[i].length; j++) {
//				if (buttonTable[i][j] != null) {
//					final AppButton myButton = buttonTable[i][j];
//					myButton.setOnAction(new EventHandler<ActionEvent>() {
//						public void handle(ActionEvent event) {
//							setPresentation(myButton.getPresentationId(), table);
//						}
//					});
//				}
//			}
//		}
//		LocalTime del = null;
//		del = LocalTime.parse("12:00", formatterDateTime);
//
//		for (int i = 0; i < section.getSectionNumber(); i++) {
//			LocalTime localtime = LocalTime.parse(section.getFrom(), formatterDateTime);
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					if (idWithSection.get(table[i][j].getId()).contains("DE")) {
//						insertDelelott(i, buttonTable[i][j]);
//					} else {
//						insertDelutan(i, buttonTable[i][j]);
//					}
//				}
//			}
//		}
//		for (int i = 0; i < tableDelelott.length; i++) {
//			for (int j = 0; j < tableDelelott[i].length; j++) {
//				if (tableDelelott[i][j] != null) {
//					gridOne.add(tableDelelott[i][j], i, j);
//				} else if (j == 0 && tableDelelott[i][j] == null) {
//					AppButton button = new AppButton(Integer.MAX_VALUE);
//					button.setText("Nincs elõadás");
//					button.setPrefSize(100, 75);
//					gridOne.add(button, i, j);
//				}
//			}
//		}
//		for (int i = 0; i < tableDelutan.length; i++) {
//			for (int j = 0; j < tableDelutan[i].length; j++) {
//				if (tableDelutan[i][j] != null) {
//					gridTwo.add(tableDelutan[i][j], i, j);
//				} else if (j == 0 && tableDelutan[i][j] == null) {
//					AppButton button = new AppButton(Integer.MAX_VALUE);
//					button.setText("Nincs elõadás");
//					button.setPrefSize(100, 75);
//					gridTwo.add(button, i, j);
//				}
//			}
//		}
//		tabPane.prefWidth(buttonTable.length * 100);
//		delelottButton.setPrefWidth(buttonTable.length * 100);
//		delutanButton.setPrefWidth(buttonTable.length * 100);
//	}

	private void insertDelutan(int index, AppButton buttonTable) {
		boolean insert = true;
		for (int i = 0; i < tableDelutan.length; i++) {
			for (int j = 0; j < tableDelutan[i].length; j++) {
				if (tableDelutan[i][j] == null && index == i) {
					tableDelutan[i][j] = buttonTable;
					insert = false;
					break;
				}
			}
			if (!insert) {
				break;
			}
		}
	}

	private void insertDelelott(int index, AppButton buttonTable) {
		boolean insert = true;
		for (int i = 0; i < tableDelelott.length; i++) {
			for (int j = 0; j < tableDelelott[i].length; j++) {
				if (tableDelelott[i][j] == null && index == i) {
					tableDelelott[i][j] = buttonTable;
					insert = false;
					break;
				}
			}
			if (!insert) {
				break;
			}
		}
	}

	public LinkedList<PresentationOperator> updateOperatorList(LinkedList<PresentationOperator> operators) {
		LinkedList<PresentationOperator> newOperatorList = new LinkedList<>();
		for (PresentationOperator presentationOperator : operators) {
			if (presentationOperator.isPiority()) {
				newOperatorList.addFirst(presentationOperator);
			} else {
				newOperatorList.add(presentationOperator);
			}
		}
		return newOperatorList;
	}

	@FXML
	public void reGenerateAction(ActionEvent event) {
		PresentationProblem problem = new PresentationProblem();
		LinkedList<PresentationOperator> newOperatorList = new LinkedList<>();
		newOperatorList = updateOperatorList(operatorsTab);
		PresentationProblem.setOperators(newOperatorList);
		problem.setSection(sectionTab);
		problem.setMapSize(sectionTab.getSections());
		problem.setX(sectionTab.getSectionNumber());
		problem.setY(operatorsTab.size());
		NewGenericAlgorythm algorithm = new NewGenericAlgorythm(problem);
		boolean run = algorithm.run();
		Map<String, LinkedList<Integer>> intMap = algorithm.GetMapFullTabla();
		finalTable = new HashMap<>();
		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
			LinkedList<Presentation> tmpList = new LinkedList<>();
			LinkedList<Integer> temp = entry.getValue();
			if (temp != null) {
				tmpList = new LinkedList<>();
				for (int j = 0; j < temp.size(); j++) {
					for (PresentationOperator ope : operatorsTab) {
						if (temp.get(j) == ope.getId()) {
							tmpList.add(new Presentation(ope.getId(), ope.getPresentationTitle(), ope.getActor(),
									ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight(),
									ope.getInter()));
							break;
						}
					}
				}
				finalTable.put(entry.getKey(), tmpList);
			}
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainForm.class.getResource("/Table.fxml"));
		try {
			AnchorPane fightView = (AnchorPane) loader.load();
			Stage stage = new Stage();
			stage.setTitle("Elõadások");
			stage.initModality(Modality.WINDOW_MODAL);

			Scene scene = new Scene(fightView);
			stage.setScene(scene);

			TableControl controller = loader.getController();
			controller.setFirstGrid(finalTable, operatorsTab, sectionTab);
			stage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.show();
			Stage actualStage = (Stage) reGenerate.getScene().getWindow();
			actualStage.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void changeButtonAction(ActionEvent event) {
		for (PresentationOperator op : operatorsTab) {
			if (op.getId() == presentationId) {
				op.setWeight(Integer.parseInt(weight.getText()));
				op.setPiority(piority.isSelected());
			}
		}
		PresentationOperator newOperator = new PresentationOperator();
		newOperator.setId(presentationId);
		newOperator.setPiority(new Boolean(piority.isSelected()));
		newOperator.setWeight(Integer.parseInt(weight.getText()));

		for (int i = 0; i < tableMod.length; i++) {
			for (int j = 0; j < tableMod[i].length; j++) {
				if (tableMod[i][j] != null) {
					if (presentationId == tableMod[i][j].getId()) {
						tableMod[i][j].setWeight(Integer.parseInt(weight.getText()));
						tableMod[i][j].setPoirity(piority.isSelected());
						break;
					}
				}
			}
		}
	}

//	public void setPresentation(int id, Presentation[][] table) {
//		for (int i = 0; i < table.length; i++) {
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					if (id == table[i][j].getId()) {
//						title.setText(table[i][j].getPresentationTitle());
//						title.setTextAlignment(TextAlignment.CENTER);
//						topic.setText(table[i][j].getTopic());
//						topic.setTextAlignment(TextAlignment.CENTER);
//						presentatior.setText(table[i][j].getActor());
//						presentatior.setTextAlignment(TextAlignment.CENTER);
//						time.setText(table[i][j].getFrom() + " - " + table[i][j].getTo());
//						time.setTextAlignment(TextAlignment.CENTER);
//						presentationId = id;
//						break;
//					}
//				}
//			}
//		}
//	}

//	private void setGrids(Presentation[][] table) throws ParseException {
//		LocalTime del = null;
//		del = LocalTime.parse("12:00", formatterDateTime);
//		AppButton[][] buttonTable = new AppButton[table.length][table[0].length];
//		for (int i = 0; i < table.length; i++) {
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					buttonTable[i][j] = new AppButton(i);
//					buttonTable[i][j].setPresentationId(table[i][j].getId());
//					buttonTable[i][j].setText(
//							table[i][j].getTopic() + "\n" + table[i][j].getFrom() + " - " + table[i][j].getTo());
//					buttonTable[i][j].setPrefSize(100, 75);
//					if (table[i][j].isPoirity()) {
//						buttonTable[i][j].setStyle("-fx-background-color: #96012e");
//					}
//					buttonTable[i][j].setTextAlignment(TextAlignment.CENTER);
//					tableMod[i][j] = table[i][j];
//				}
//			}
//		}
//		for (int i = 0; i < buttonTable.length; i++) {
//			for (int j = 0; j < buttonTable[i].length; j++) {
//				if (buttonTable[i][j] != null) {
//					final AppButton myButton = buttonTable[i][j];
//					myButton.setOnAction(new EventHandler<ActionEvent>() {
//						public void handle(ActionEvent event) {
//							setPresentation(myButton.getPresentationId(), table);
//						}
//					});
//				}
//			}
//		}
//		for (int i = 0; i < table.length; i++) {
//			LocalTime localtime = LocalTime.parse("08:00", formatterDateTime);
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					String tmpInter = table[i][j].getInter();
//					String[] interArray = tmpInter.split("\\.");
//					localtime.plusMinutes(Integer.parseInt(interArray[0]));
//					if (localtime.isBefore(del)) {
//						insertDelelott(i, buttonTable[i][j]);
//					} else {
//						insertDelutan(i, buttonTable[i][j]);
//					}
//				}
//			}
//		}
//		for (int i = 0; i < tableDelelott.length; i++) {
//			for (int j = 0; j < tableDelelott[i].length; j++) {
//				if (tableDelelott[i][j] != null) {
//					gridOneDele.add(tableDelelott[i][j], i, j);
//				}
//			}
//		}
//		for (int i = 0; i < tableDelutan.length; i++) {
//			for (int j = 0; j < tableDelutan[i].length; j++) {
//				if (tableDelutan[i][j] != null) {
//					gridOneDelu.add(tableDelutan[i][j], i, j);
//				}
//			}
//		}
//		GridPane TabgridPane = new GridPane();
//		delelottButton = new Button("Délelõtt");
//		TabgridPane.add(delelottButton, 0, 0);
//		TabgridPane.add(gridOneDele, 0, 1);
//		delutanButton = new Button("Délután");
//		// delelottButton.setPrefWidth(buttonTableList.get(0).length * 100);
//		// delutanButton.setPrefWidth(buttonTableList.get(0).length * 100);
//		TabgridPane.add(delutanButton, 0, 2);
//		TabgridPane.add(gridOneDelu, 0, 3);
//		Tab tab1 = new Tab();
//		tab1.setText("Elsõ nap");
//		tab1.setContent(TabgridPane);
//		tabPane.getTabs().add(tab1);
//	}

	public void setFirstGrid(Map<String, LinkedList<Presentation>> finalTable,
			LinkedList<PresentationOperator> operators, Section section) {
		operatorsTab = operators;
		sectionTab = section;
		int maxLengt = 0;
		Map<String, LinkedList<AppButton>> buttonMap = new HashMap();
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			LinkedList<Presentation> tmpList= map.getValue();
			LocalTime localtime = null;
			LocalTime localtime13 = null;
			localtime = LocalTime.parse(section.getFrom(), formatterDateTime);
			localtime13 = LocalTime.parse("13:00", formatterDateTime);
			if(map.getKey().contains("DE")){
				for (Presentation presentation : tmpList) {
					String tmpInter = presentation.getInter();
					String[] interArray = tmpInter.split("\\.");
					presentation.setFromTime(localtime.toString());
					localtime = localtime.plusMinutes(Integer.parseInt(interArray[0]));
				}
			}else if(map.getKey().contains("DU")){
				for (Presentation presentation : tmpList) {
					String tmpInter = presentation.getInter();
					String[] interArray = tmpInter.split("\\.");
					presentation.setFromTime(localtime13.toString());
					localtime13 = localtime13.plusMinutes(Integer.parseInt(interArray[0]));
				}
			}
			if (map.getValue().size() > maxLengt) {
				maxLengt = map.getValue().size();
			}
		}
		Map<String, Integer> dateIndexPos = new HashMap<>();
		if(finalTable.keySet().size()%2 ==0){
			tableDelelott = new AppButton[finalTable.keySet().size()/2][maxLengt];
			tableDelutan = new AppButton[finalTable.keySet().size()/2][maxLengt];
		}else{
			tableDelelott = new AppButton[(finalTable.keySet().size()/2)+1][maxLengt];
			tableDelutan = new AppButton[(finalTable.keySet().size()/2)+1][maxLengt];
		}
		termek = new Button[maxLengt];
		int k = 0;
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			LinkedList<AppButton> buttonList = new LinkedList<>();
			int j = 0;
			for (Presentation presentation : map.getValue()) {
				AppButton tmpButton = new AppButton(k);
				tmpButton.setPresentationId(presentation.getId());
				tmpButton.setText(presentation.getActor());
				tmpButton.setPrefSize(150, 75);
				tmpButton.setTextAlignment(TextAlignment.CENTER);
				buttonList.add(tmpButton);
			}
			buttonMap.put(map.getKey(), buttonList);
		}
		for (Map.Entry<String, LinkedList<AppButton>> map : buttonMap.entrySet()) {
			LinkedList<AppButton> buttonList = new LinkedList<>();
			int j = 0;
			for (AppButton button : map.getValue()) {
				button.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						setPresentation(button.getPresentationId(), finalTable);
					}
				});
			}
		}
		k = 0;
		int t = 0;
		for (String terem : section.getSections()) {
			termek[t] = new Button();
			termek[t].setText(terem);
			t++;
		}
		for (Map.Entry<String, LinkedList<AppButton>> map : buttonMap.entrySet()) {
			if (!dateIndexPos.containsKey(map.getKey())) {
				if (map.getKey().contains("DE")) {
					for (AppButton button : map.getValue()) {
						insertDelelott(k, button);
					}
					dateIndexPos.put(map.getKey().substring(0, map.getKey().length()-2)+"DU", k);
				} else if (map.getKey().contains("DU")) {
					for (AppButton button : map.getValue()) {
						insertDelutan(k, button);
					}
					dateIndexPos.put(map.getKey().substring(0, map.getKey().length()-2)+"DE", k);
				}
				k++;
			}else if (dateIndexPos.containsKey(map.getKey())) {
				if (map.getKey().contains("DE")) {
					for (AppButton button : map.getValue()) {
						insertDelelott(dateIndexPos.get(map.getKey()), button);
					}
				} else if (map.getKey().contains("DU")) {
					for (AppButton button : map.getValue()) {
						insertDelutan(dateIndexPos.get(map.getKey()), button);
					}
				}
			}
		}
		
		for (int i = 0; i < tableDelelott.length; i++) {
			for (int j = 0; j < tableDelelott[i].length; j++) {
				if (tableDelelott[i][j] != null) {
					gridOneDele.add(tableDelelott[i][j], i, j);
				} else if (j == 0 && tableDelelott[i][j] == null) {
					AppButton button = new AppButton(Integer.MAX_VALUE);
					button.setText("Nincs elõadás");
					button.setPrefSize(150, 75);
					gridOneDele.add(button, i, j);
				}
			}
		}
		for (int i = 0; i < tableDelutan.length; i++) {
			for (int j = 0; j < tableDelutan[i].length; j++) {
				if (tableDelutan[i][j] != null) {
					gridOneDelu.add(tableDelutan[i][j], i, j);
				} else if (j == 0 && tableDelutan[i][j] == null) {
					AppButton button = new AppButton(Integer.MAX_VALUE);
					button.setText("Nincs elõadás");
					button.setPrefSize(150, 75);
					gridOneDelu.add(button, i, j);
				}
			}
		}
		for (int i = 0; i < termek.length; i++) {
			if (termek[i] != null) {
				termek[i].setPrefSize(150, 35);
				termek[i].setStyle("-fx-background-color: #727987");
				termek[i].setStyle("-fx-border-color: white;");
				gridTermek.add(termek[i], i,0);
			}
		}
		GridPane TabgridPane = new GridPane();
		TabgridPane.setStyle("-fx-background-color: #afb6c6");
		TabgridPane.setStyle("-fx-border-color: #657087;");
		TabgridPane.setStyle("-fx-border-width: 2px;");
		delelottButton = new Button("Délelõtt");
		TabgridPane.add(gridTermek, 0, 0);
		TabgridPane.add(delelottButton, 0, 1);
		TabgridPane.add(gridOneDele, 0, 2);
		delutanButton = new Button("Délután");
		delelottButton.setPrefWidth(tableDelelott.length * 150);
		delelottButton.setPrefHeight(40);
		delelottButton.setStyle("-fx-background-color: #657087");
		delutanButton.setPrefWidth(tableDelutan.length * 150);
		delutanButton.setPrefHeight(40);
		delutanButton.setStyle("-fx-background-color: #657087");
		TabgridPane.add(delutanButton, 0, 3);
		TabgridPane.add(gridOneDelu, 0, 4);
		Tab tab1 = new Tab();
		tab1.setText("Elsõ nap");
		tab1.setContent(TabgridPane);
		tabPane.getTabs().add(tab1);
		exportTable = finalTable;
	}

	protected void setPresentation(int id, Map<String, LinkedList<Presentation>> finalTable) {
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			for (Presentation presentation : map.getValue()) {
				if (id == presentation.getId()) {
					title.setText(presentation.getPresentationTitle());
					title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
					title.setWrapText(true);
					topic.setText(presentation.getTopic());
					topic.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
					presentatior.setText(presentation.getActor());
					presentatior.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
					time.setText(presentation.getFromTime());
					time.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
					presentationId = id;
					break;
				}
			}
		}
	}
	
	@FXML
	public void xlsExportAction(ActionEvent event) {
		daoImp.writePresentationsXls(exportTable, sectionTab);
	}
	
	@FXML
	public void xlsxExportAction(ActionEvent event) {
		daoImp.writePresentationsXlsx(exportTable, sectionTab);
	}
}

