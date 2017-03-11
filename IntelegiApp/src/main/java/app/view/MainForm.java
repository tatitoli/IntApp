package app.view;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import app.daoimp.PresentationsDaoImp;
import app.model.Section;
import app.sort.Optimal;
import app.sort.Presentation;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
import geneticalgorythm.GeneticAlgorithm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainForm extends Application {

	Presentation[][] table;
	int[][] intTable;
	
	Section section;
	
	Stage stage;

	Scene scene;

	@FXML
	Button fileButton;

	@FXML
	Button generateButton;

	private File selectedFile;

	@FXML
	public void addFileAction(ActionEvent event) {
		selectedFile = null;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		selectedFile = fileChooser.showOpenDialog(stage);
		System.out.println(selectedFile);
	}

	@FXML
	public void generateAction(ActionEvent event) throws IOException {
		long startTime = System.currentTimeMillis();
		PresentationsDaoImp impDao = new PresentationsDaoImp();
		LinkedList<PresentationOperator> operators = new LinkedList<>();
//		operators = impDao.getPresentations(selectedFile);
		operators = impDao.getPresentationsInter(selectedFile);
		section = impDao.getSection(selectedFile);
		PresentationProblem problem = new PresentationProblem();
		PresentationProblem.setOperators(operators);
		problem.setSection(section);
		problem.setMapSize(section.getSections());
		problem.setX(section.getSectionNumber());
		problem.setY(operators.size());
		int db = 0, min=Integer.MAX_VALUE;
//		while(db<11){
//			TryError tryError = new TryError(problem);
//			if(tryError.run()){
//				if(min >= tryError.GetLastCost()){
//					min = tryError.GetLastCost();
//				}
//				db++;
//			}
//		}
//		Optimal algorithm = new Optimal(problem,min);
		GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
		boolean run = algorithm.run();
		if (run == false) {
			System.out.println("Nem lehet beosztani az elõadásokat!");
		}
		Map<String, LinkedList<Integer>> intMap = algorithm.GetMapTabla();
		int maxLenght = Integer.MIN_VALUE;
		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
			if(maxLenght < entry.getValue().size()){
				maxLenght = entry.getValue().size();
			}
		}
		table= new Presentation[section.getSectionNumber()][maxLenght];
		int i =0;
		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
			LinkedList<Integer> temp = entry.getValue();
			for (int j = 0; j < temp.size(); j++) {
				for (PresentationOperator ope : operators) {
					if(temp.get(j) == ope.getId()){
						table[i][j] = new Presentation(ope.getId(), ope.getPresentationTitle(), 
								ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight());
						break;
					}
				}
			}
			i++;
		}
//		for (int i = 0; i < intTable.length; i++) {
//			for (int j = 0; j < intTable[i].length; j++) {
//				for (PresentationOperator ope : operators) {
//					if(intTable[i][j] == ope.getId()){
//						table[i][j] = new Presentation(ope.getId(), ope.getPresentationTitle(), 
//								ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight());
//						break;
//					}
//				}
//			}
//		}
//		AppButton[][] buttonTable = new AppButton[section.getSectionNumber()][operators.size()];
//		stage = new Stage();
//		GridPane gridPane = new GridPane();
//		int maxWidth=0;
//		for (int i = 0; i < table.length; i++) {
//			for (int j = 0; j < table[i].length; j++) {
//				if (table[i][j] != null) {
//					buttonTable[i][j] = new AppButton(i);
//					buttonTable[i][j].setPresentationId(table[i][j].getId());
//					buttonTable[i][j].setText(table[i][j].getTopic()+ "\n" + table[i][j].getFrom() + " - " + table[i][j].getTo());
//					buttonTable[i][j].setPrefSize(100, 75);
//					if(table[i][j].isPoirity()){
//						buttonTable[i][j].setStyle("-fx-background-color: #96012e");
//					}
//					buttonTable[i][j].setTextAlignment(TextAlignment.CENTER);
//					if(j>maxWidth){
//					}
//				}
//				maxWidth=i;
//			}
//		}
//		for (int i = 0; i < buttonTable.length; i++) {
//			for (int j = 0; j < buttonTable[i].length; j++) {
//				if (buttonTable[i][j] != null) {
//					final AppButton myButton = buttonTable[i][j];
//					myButton.setOnAction(new EventHandler<ActionEvent>() {
//						public void handle(ActionEvent event) {
//							FXMLLoader loader = new FXMLLoader();
//							loader.setLocation(ChangeForm.class.getResource("/ButtonElement.fxml"));
//							AnchorPane fightView;
//							try {
//								fightView = (AnchorPane)loader.load();
//							Stage stage = new Stage();
//							stage.setTitle("Elõadás");
//							Scene scene = new Scene(fightView);
//							stage.setScene(scene);
//							ChangeForm controller = loader.getController();
//							controller.setPresentation(myButton.getPresentationId(), table);
//							stage.setResizable(false);
//							stage.show();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					});
//				}
//			}
//		}
//		for (int i = 0; i < buttonTable.length; i++) {
//			for (int j = 0; j < buttonTable[i].length; j++) {
//				if (buttonTable[i][j] != null) {
//					gridPane.add(buttonTable[i][j], i, j);
//				}
//			}
//		}
		
		
		//Porba
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
			controller.setFirstGrid(table, operators, section);
			stage.setResizable(false);
			stage.show();
			Stage actualStage = (Stage) generateButton.getScene().getWindow();
			actualStage.close();
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainForm.class.getResource("/MainForm.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 389.0,249);
		// scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fõoldal");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}