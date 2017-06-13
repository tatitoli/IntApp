package app.view;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.Map;

import app.model.AppButton;
import app.model.Section;
import app.sort.Presentation;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
import geneticalgorythm.GeneticAlgorithm;
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
	LinkedList<Presentation[][]> tableList;
	
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

	GridPane gridOneDele = new GridPane();
	GridPane gridOneDelu = new GridPane();
	GridPane gridTwoDele = new GridPane();
	GridPane gridTwoDelu = new GridPane();
	GridPane gridThreeDele = new GridPane();
	GridPane gridThreeDelu = new GridPane();

	Presentation[][] tableMod;
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
		Tab tab1 = new Tab();
		tab1.setText("Elsõ nap");
		Tab tab2 = new Tab();
		tab2.setText("Második nap");
		Tab tab3 = new Tab();
		tab3.setText("Haramadik nap");
		Tab tab4 = new Tab();
		tab4.setText("Negyedik nap");
		Tab tab5 = new Tab();
		tab5.setText("Ötödik nap");
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

//	public void setFirstGrid(Presentation[][] table, LinkedList<PresentationOperator> operators, Section section) {
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
//					if (table[i][j].isPoirity()) {
//						buttonTable[i][j].setStyle("-fx-background-color: #96012e");
//					}
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
//		// del = formatter.parse("12:00");
//		del = LocalTime.parse("12:00", formatterDateTime);
//
//		for (int i = 0; i < table.length; i++) {
//			LocalTime localtime = LocalTime.parse(section.getFrom(), formatterDateTime);
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					String tmpInter = table[i][j].getInter();
//					String[] interArray = tmpInter.split("\\.");
//					localtime.plusMinutes(Integer.parseInt(interArray[0]));
//					String[] from = table[i][j].getFrom().split(" ");
//					LocalTime fromLT = LocalTime.parse(from[3], formatterDateTime);
//					// String[] tmp = table[i][j].getFrom().split(" ");
//					// time = formatter.parse(tmp[3]);
//					if (localtime.isBefore(del) && fromLT.isBefore(del)) {
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
		GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
		boolean run = algorithm.run();
		Map<String, LinkedList<Integer>> intMap = algorithm.GetMapFullTabla();
		int maxLenght = Integer.MIN_VALUE;
		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
			if (maxLenght < entry.getValue().size()) {
				maxLenght = entry.getValue().size();
			}
		}
		tableList = new LinkedList<>();
		for(int i =0;i<sectionTab.getDays().size();i++){
			tableMod= new Presentation[sectionTab.getSectionNumber()][maxLenght];
			int k = 0;
			for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
				if(entry.getKey().contains(sectionTab.getDays().get(i))){
					LinkedList<Integer> temp = entry.getValue();
					for (int j = 0; j < temp.size(); j++) {
						for (PresentationOperator ope : operatorsTab) {
							if(temp.get(j) == ope.getId()){
								tableMod[k][j] = new Presentation(ope.getId(), ope.getPresentationTitle(), 
										ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight(), ope.getInter());
								break;
							}
						}
					}
					k++;
				}
			}
			tableList.add(tableMod);
		}
//		tableMod= new Presentation[sectionTab.getSectionNumber()*sectionTab.getDays().size()][maxLenght];
		// Optimal algorithm = new Optimal(problem,min);
		// boolean run = algorithm.run();
		// if (run == false) {
		// System.out.println("Nem lehet beosztani az elõadásokat!");
		// }
		// intTable = algorithm.GetTabla();

		// tableMod= new Presentation[intTable.length][intTable[0].length];
//		tableMod = new Presentation[sectionTab.getSectionNumber() * sectionTab.getDays().size()][maxLenght];
//		int i = 0;
//		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
//			LinkedList<Integer> temp = entry.getValue();
//			for (int j = 0; j < temp.size(); j++) {
//				for (PresentationOperator ope : newOperatorList) {
//					if (temp.get(j) == ope.getId()) {
//						tableMod[i][j] = new Presentation(ope.getId(), ope.getPresentationTitle(), ope.getActor(),
//								ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight(),
//								ope.getInter());
//						break;
//					}
//				}
//			}
//			i++;
//		}
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
//			controller.setFirstGrid(tableMod, operatorsTab, sectionTab);
			controller.setFirstGrid(tableList, operatorsTab, sectionTab);
			stage.setResizable(false);
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

	public void setPresentation(int id, Presentation[][] table) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j] != null) {
					if (id == table[i][j].getId()) {
						title.setText(table[i][j].getPresentationTitle());
						topic.setText(table[i][j].getTopic());
						presentatior.setText(table[i][j].getActor());
						time.setText(table[i][j].getFrom() + " - " + table[i][j].getTo());
						weight.setText(table[i][j].getWeight() + "");
						piority.setSelected(table[i][j].isPoirity());
						presentationId = id;
						break;
					}
				}
			}
		}
	}

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
//					gridOne.add(tableDelelott[i][j], i, j);
//				}
//			}
//		}
//		for (int i = 0; i < tableDelutan.length; i++) {
//			for (int j = 0; j < tableDelutan[i].length; j++) {
//				if (tableDelutan[i][j] != null) {
//					gridTwo.add(tableDelutan[i][j], i, j);
//				}
//			}
//		}
//	}

	public void setFirstGrid(LinkedList<Presentation[][]> tableList, LinkedList<PresentationOperator> operators,
			Section section) {
		operatorsTab = operators;
		sectionTab = section;
		LinkedList<AppButton[][]> buttonTableList = new LinkedList<>();
		for (int k = 0; k < tableList.size(); k++) {
			Presentation[][] table = tableList.get(k);
			tableMod = new Presentation[table.length][table[0].length];
			tableDelelott = new AppButton[table.length][table[0].length];
			tableDelutan = new AppButton[table.length][table[0].length];
			AppButton[][] buttonTable = new AppButton[table.length][table[0].length];
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[i].length; j++) {
					if (table[i][j] != null) {
						buttonTable[i][j] = new AppButton(i);
						buttonTable[i][j].setPresentationId(table[i][j].getId());
						buttonTable[i][j].setText(table[i][j].getActor());
						buttonTable[i][j].setPrefSize(100, 75);
						if (table[i][j].isPoirity()) {
							buttonTable[i][j].setStyle("-fx-background-color: #96012e");
						}
						buttonTable[i][j].setTextAlignment(TextAlignment.CENTER);
						tableMod[i][j] = table[i][j];

					}
				}
			}
			for (int i = 0; i < buttonTable.length; i++) {
				for (int j = 0; j < buttonTable[i].length; j++) {
					if (buttonTable[i][j] != null) {
						final AppButton myButton = buttonTable[i][j];
						myButton.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								setPresentation(myButton.getPresentationId(), table);
							}
						});
					}
				}
			}
			LocalTime del = null;
			// del = formatter.parse("12:00");
			del = LocalTime.parse("12:00", formatterDateTime);

			for (int i = 0; i < table.length; i++) {
				LocalTime localtime = LocalTime.parse(section.getFrom(), formatterDateTime);
				for (int j = 0; j < table[i].length; j++) {
					if (table[i][j] != null) {
						String tmpInter = table[i][j].getInter();
						String[] interArray = tmpInter.split("\\.");
						localtime = localtime.plusMinutes(Integer.parseInt(interArray[0]));
						String[] from = table[i][j].getFrom().split(" ");
						LocalTime fromLT = LocalTime.parse(from[3], formatterDateTime);
						// String[] tmp = table[i][j].getFrom().split(" ");
						// time = formatter.parse(tmp[3]);
						if (localtime.isBefore(del) && fromLT.isBefore(del)) {
							insertDelelott(i, buttonTable[i][j]);
						} else {
							insertDelutan(i, buttonTable[i][j]);
						}
					}
				}
			}
			if (k == 0) {
				for (int i = 0; i < tableDelelott.length; i++) {
					for (int j = 0; j < tableDelelott[i].length; j++) {
						if (tableDelelott[i][j] != null) {
							gridOneDele.add(tableDelelott[i][j], i, j);
						} else if (j == 0 && tableDelelott[i][j] == null) {
							AppButton button = new AppButton(Integer.MAX_VALUE);
							button.setText("Nincs elõadás");
							button.setPrefSize(100, 75);
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
							button.setPrefSize(100, 75);
							gridOneDelu.add(button, i, j);
						}
					}
				}
				buttonTableList.add(buttonTable);
				GridPane TabgridPane = new GridPane();
				delelottButton = new Button("Délelõtt");
				TabgridPane.add(delelottButton, 0, 0);
				TabgridPane.add(gridOneDele, 0, 1);
				delutanButton = new Button("Délután");
				delelottButton.setPrefWidth(buttonTableList.get(0).length * 100);
				delutanButton.setPrefWidth(buttonTableList.get(0).length * 100);
				TabgridPane.add(delutanButton, 0, 2);
				TabgridPane.add(gridOneDelu, 0, 3);
				Tab tab1 = new Tab();
				tab1.setText("Elsõ nap");
				tab1.setContent(TabgridPane);
				tabPane.getTabs().add(tab1);
			}
			if (k == 1) {
				for (int i = 0; i < tableDelelott.length; i++) {
					for (int j = 0; j < tableDelelott[i].length; j++) {
						if (tableDelelott[i][j] != null) {
							gridTwoDele.add(tableDelelott[i][j], i, j);
						} else if (j == 0 && tableDelelott[i][j] == null) {
							AppButton button = new AppButton(Integer.MAX_VALUE);
							button.setText("Nincs elõadás");
							button.setPrefSize(100, 75);
							gridTwoDele.add(button, i, j);
						}
					}
				}
				for (int i = 0; i < tableDelutan.length; i++) {
					for (int j = 0; j < tableDelutan[i].length; j++) {
						if (tableDelutan[i][j] != null) {
							gridTwoDelu.add(tableDelutan[i][j], i, j);
						} else if (j == 0 && tableDelutan[i][j] == null) {
							AppButton button = new AppButton(Integer.MAX_VALUE);
							button.setText("Nincs elõadás");
							button.setPrefSize(100, 75);
							gridTwoDelu.add(button, i, j);
						}
					}
				}
				buttonTableList.add(buttonTable);
				GridPane TabgridPane = new GridPane();
				delelottButton = new Button("Délelõtt");
				TabgridPane.add(delelottButton, 0, 0);
				TabgridPane.add(gridTwoDele, 0, 1);
				delutanButton = new Button("Délután");
				delelottButton.setPrefWidth(buttonTableList.get(0).length * 100);
				delutanButton.setPrefWidth(buttonTableList.get(0).length * 100);
				TabgridPane.add(delutanButton, 0, 2);
				TabgridPane.add(gridTwoDelu, 0, 3);
				Tab tab2 = new Tab();
				tab2.setText("Második nap");
				tab2.setContent(TabgridPane);
				tabPane.getTabs().add(tab2);
			}
		}
		tabPane.prefWidth(buttonTableList.get(0).length * 100);
		delelottButton.setPrefWidth(buttonTableList.get(0).length * 100);
		delutanButton.setPrefWidth(buttonTableList.get(0).length * 100);
	}
}
