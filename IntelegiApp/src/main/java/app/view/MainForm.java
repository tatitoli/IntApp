package app.view;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import app.daoimp.PresentationsDaoImp;
import app.sort.Algorythm;
import app.sort.Operator;
import app.sort.Optimal;
import app.sort.PresentationProblem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainForm extends Application {

	Stage stage;

	@FXML
	Button fileButton;

	@FXML
	public void addFileAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(stage);
		System.out.println(file);
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
		primaryStage.setTitle("Fõoldal");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
//		launch(args);
		PresentationsDaoImp impDao = new PresentationsDaoImp();
		Set<Operator> operators = new HashSet<>();
		operators = impDao.getPresentations();
		PresentationProblem problem = new PresentationProblem();
		PresentationProblem.setOperators(operators);
//		Algorythm algorithm = new Algorythm(problem);
		Optimal algorithm = new Optimal(problem);
		boolean run = algorithm.run();
		if(run == false){
			System.out.println("Nem lehet beosztani az elõadásokat!");
		}
		System.out.println(algorithm.getGoal());
	}
}