package app.view;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import app.daoimp.PresentationsDaoImp;
import app.model.AppButton;
import app.model.Section;
import app.sort.Optimal;
import app.sort.Presentation;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
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
		PresentationsDaoImp impDao = new PresentationsDaoImp();
		Set<PresentationOperator> operators = new HashSet<>();
		operators = impDao.getPresentations(selectedFile);
		section = impDao.getSection(selectedFile);
		PresentationProblem problem = new PresentationProblem();
		PresentationProblem.setOperators(operators);
		problem.setX(section.getSectionNumber());
		problem.setY(operators.size());
		Optimal algorithm = new Optimal(problem);
		boolean run = algorithm.run();
		if (run == false) {
			System.out.println("Nem lehet beosztani az el�ad�sokat!");
		}
		System.out.println(algorithm.getGoal());
		intTable = algorithm.GetTabla();
		table= new Presentation[intTable.length][intTable[0].length];
		for (int i = 0; i < intTable.length; i++) {
			for (int j = 0; j < intTable[i].length; j++) {
				for (PresentationOperator ope : operators) {
					if(intTable[i][j] == ope.getId()){
						table[i][j] = new Presentation(ope.getId(), ope.getPresentationTitle(), 
								ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(), ope.getWeight());
						break;
					}
				}
			}
		}
		AppButton[][] buttonTable = new AppButton[section.getSectionNumber()][operators.size()];
		stage = new Stage();
		GridPane gridPane = new GridPane();
		int maxWidth=0;
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
					if(j>maxWidth){
					}
				}
				maxWidth=i;
			}
		}
		for (int i = 0; i < buttonTable.length; i++) {
			for (int j = 0; j < buttonTable[i].length; j++) {
				if (buttonTable[i][j] != null) {
					final AppButton myButton = buttonTable[i][j];
					myButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(ChangeForm.class.getResource("/ButtonElement.fxml"));
							AnchorPane fightView;
							try {
								fightView = (AnchorPane)loader.load();
							Stage stage = new Stage();
							stage.setTitle("El�ad�s");
							Scene scene = new Scene(fightView);
							stage.setScene(scene);
							ChangeForm controller = loader.getController();
							controller.setPresentation(myButton.getPresentationId(), table);
							stage.setResizable(false);
							stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		}
		for (int i = 0; i < buttonTable.length; i++) {
			for (int j = 0; j < buttonTable[i].length; j++) {
				if (buttonTable[i][j] != null) {
					gridPane.add(buttonTable[i][j], i, j);
				}
			}
		}
		TilePane tilepane = new TilePane();
		AnchorPane ancher = new AnchorPane();
//		ancher.setStyle("-fx-background-color: #96012e");
//		gridPane.setStyle("-fx-background-color: #60002e");
		ancher.setPrefSize(125, 125);
		ancher.setMaxSize(125, 125);
		gridPane.setPrefSize(section.getSectionNumber()*100, maxWidth*75);
//		GridPane mainGridpane = new GridPane();
//		mainGridpane.add(gridPane, 0, 0);
//		mainGridpane.add(ancher, 1, 0);
//		mainGridpane.getChildren().add(mainGridpane);
//		mainGridpane.getChildren().add(ancher);
//		tilepane.getChildren().add(gridPane);
//		tilepane.getChildren().add(ancher);
//		tilepane.setTileAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(gridPane, 500, 500);
		stage.setScene(scene);
		stage.setTitle("T�bla");
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainForm.class.getResource("/MainForm.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 640, 480);
		// scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("F�oldal");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}