package app.view;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import app.model.AppButton;
import app.model.Section;
import app.sort.Optimal;
import app.sort.Presentation;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
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
	
	final String FORMAT = "mm:ss";
	DateFormat formatter = new SimpleDateFormat(FORMAT);
	Set<PresentationOperator> operatorsTab = new HashSet<>();
	Section sectionTab;
	int[][] intTable;
	
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
	
	GridPane gridOne = new GridPane();
	GridPane gridTwo = new GridPane();
	
	Presentation[][] tableMod;
	AppButton[][] tableDelelott;
	AppButton[][] tableDelutan;

	@FXML
	TabPane tabPane;
	
	@FXML 
	Button delelottButton;
	
	@FXML 
	Button delutanButton;
	
	public void initialize(){
		Tab tab = new Tab();
		tab.setText("Elsõ nap");
		GridPane TabgridPane = new GridPane();
		delelottButton = new Button("Délelõtt");
		TabgridPane.add(delelottButton,0,0);
		TabgridPane.add(gridOne, 0, 1);
		delutanButton = new Button("Délután");
		TabgridPane.add(delutanButton, 0, 2);
		TabgridPane.add(gridTwo, 0, 3);
		tab.setContent(TabgridPane);
		tabPane.getTabs().add(tab);
	}

	public void setFirstGrid(Presentation[][] table, Set<PresentationOperator> operators, Section section) {
		operatorsTab = operators;
		sectionTab = section;
		tableMod = new Presentation[table.length][table[0].length];
		tableDelelott = new AppButton[table.length][table[0].length];
		tableDelutan = new AppButton[table.length][table[0].length];
		AppButton[][] buttonTable = new AppButton[table.length][table[0].length];
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j] != null) {
					buttonTable[i][j] = new AppButton(i);
					buttonTable[i][j].setPresentationId(table[i][j].getId());
					buttonTable[i][j].setText(table[i][j].getTopic()+ "\n" + table[i][j].getFrom() + " - " + table[i][j].getTo());
					buttonTable[i][j].setPrefSize(100, 75);
					if(table[i][j].isPoirity()){
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
		Date del = null;
		try {
			del = formatter.parse("12:00");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				Date time = null;
				if (table[i][j] != null) {
					try {
						time = formatter.parse(table[i][j].getFrom());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(time.before(del)){
						insertDelelott(i, buttonTable[i][j]);
					}else{
						insertDelutan(i, buttonTable[i][j]);
					}
				}
			}
		}
		for (int i = 0; i < tableDelelott.length; i++) {
			for (int j = 0; j < tableDelelott[i].length; j++) {
				if (tableDelelott[i][j] != null) {
					gridOne.add(tableDelelott[i][j], i, j);
				}
			}
		}
		for (int i = 0; i < tableDelutan.length; i++) {
				for (int j = 0; j < tableDelutan[i].length; j++) {
					if (tableDelutan[i][j] != null) {
						gridTwo.add(tableDelutan[i][j], i, j);
					}
				}
		}
		tabPane.prefWidth(buttonTable.length*100);
		delelottButton.setPrefWidth(buttonTable.length*100);
		delutanButton.setPrefWidth(buttonTable.length*100);
	}
	
	private void insertDelutan(int index,AppButton buttonTable) {
		boolean insert = true;
		for (int i = 0; i < tableDelutan.length; i++) {
			for (int j = 0; j < tableDelutan[i].length; j++) {
				if(tableDelutan[i][j]==null && index == i){
					tableDelutan[i][j]=buttonTable;
					insert = false;
					break;
				}
			}
			if(!insert){
				break;
			}
		}
	}

	private void insertDelelott(int index, AppButton buttonTable) {
		boolean insert = true;
		for (int i = 0; i < tableDelelott.length; i++) {
			for (int j = 0; j < tableDelelott[i].length; j++) {
				if(tableDelelott[i][j]==null && index == i){
					tableDelelott[i][j]=buttonTable;
					insert = false;
					break;
				}
			}
			if(!insert){
				break;
			}
		}
	}

	@FXML
	public void reGenerateAction(ActionEvent event){
		PresentationProblem problem = new PresentationProblem();
		PresentationProblem.setOperators(operatorsTab);
		problem.setX(sectionTab.getSectionNumber());
		problem.setY(operatorsTab.size());
		Optimal algorithm = new Optimal(problem);
		boolean run = algorithm.run();
		if (run == false) {
			System.out.println("Nem lehet beosztani az elõadásokat!");
		}
		System.out.println(algorithm.getGoal());
		intTable = algorithm.GetTabla();
		tableMod= new Presentation[intTable.length][intTable[0].length];
		for (int i = 0; i < intTable.length; i++) {
			for (int j = 0; j < intTable[i].length; j++) {
				for (PresentationOperator ope : operatorsTab) {
					if(intTable[i][j] == ope.getId()){
						tableMod[i][j] = new Presentation(ope.getId(), ope.getPresentationTitle(), 
								ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight());
						break;
					}
				}
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
			controller.setFirstGrid(tableMod, operatorsTab, sectionTab);
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
			if(op.getId() == presentationId){
				op.setWeight(Integer.parseInt(weight.getText()));
			}
		}
		PresentationOperator newOperator = new PresentationOperator();
		newOperator.setId(presentationId);
		newOperator.setPiority(new Boolean(piority.getText()));
		newOperator.setWeight(Integer.parseInt(weight.getText()));	
		
		for (int i = 0; i < tableMod.length; i++) {
			for (int j = 0; j < tableMod[i].length; j++) {
				if (tableMod[i][j] != null) {
					if (presentationId == tableMod[i][j].getId()) {
						tableMod[i][j].setWeight(Integer.parseInt(weight.getText()));
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
						weight.setText(table[i][j].getWeight()+"");
						piority.setSelected(table[i][j].isPoirity());
						presentationId = id;
						break;
					}
				}
			}
		}
	}
	
	private void setGrids(Presentation[][] table){
		Date del = null;
		try {
			del = formatter.parse("12:00");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		AppButton[][] buttonTable = new AppButton[table.length][table[0].length];
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j] != null) {
					buttonTable[i][j] = new AppButton(i);
					buttonTable[i][j].setPresentationId(table[i][j].getId());
					buttonTable[i][j].setText(table[i][j].getTopic()+ "\n" + table[i][j].getFrom() + " - " + table[i][j].getTo());
					buttonTable[i][j].setPrefSize(100, 75);
					if(table[i][j].isPoirity()){
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
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				Date time = null;
				if (table[i][j] != null) {
					try {
						time = formatter.parse(table[i][j].getFrom());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(time.before(del)){
						insertDelelott(i, buttonTable[i][j]);
					}else{
						insertDelutan(i, buttonTable[i][j]);
					}
				}
			}
		}
		for (int i = 0; i < tableDelelott.length; i++) {
			for (int j = 0; j < tableDelelott[i].length; j++) {
				if (tableDelelott[i][j] != null) {
					gridOne.add(tableDelelott[i][j], i, j);
				}
			}
		}
		for (int i = 0; i < tableDelutan.length; i++) {
				for (int j = 0; j < tableDelutan[i].length; j++) {
					if (tableDelutan[i][j] != null) {
						gridTwo.add(tableDelutan[i][j], i, j);
					}
				}
		}
	}
}
