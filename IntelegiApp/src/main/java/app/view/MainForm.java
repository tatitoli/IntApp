package app.view;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import app.daoimp.PresentationsDaoImp;
import app.model.Section;
import app.sort.Presentation;
import app.sort.PresentationOperator;
import app.sort.PresentationProblem;
import geneticalgorythm.GeneticAlgorithm;
import geneticalgorythm.NewGenericAlgorythm;
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

	LinkedList<Presentation[][]> tableList;

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
		operators = impDao.getPresentationsNew(selectedFile);
		section = impDao.getSection(selectedFile);
		PresentationProblem problem = new PresentationProblem();
		PresentationProblem.setOperators(operators);
		problem.setSection(section);
		problem.setMapSize(section.getSections());
		problem.setX(section.getSectionNumber());
		problem.setY(operators.size());
		NewGenericAlgorythm algorithm = new NewGenericAlgorythm(problem);
		boolean run = algorithm.run();
		Map<String, LinkedList<Integer>> intMap = algorithm.GetMapFullTabla();
		Map<String, LinkedList<Presentation>> finalTable = new HashMap<>();
		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
			LinkedList<Presentation> tmpList = new LinkedList<>();
			LinkedList<Integer> temp = entry.getValue();
			if (temp != null) {
				tmpList = new LinkedList<>();
				for (int j = 0; j < temp.size(); j++) {
					for (PresentationOperator ope : operators) {
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
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			TableControl controller = loader.getController();
			controller.setFirstGrid(finalTable, operators, section);
			stage.setResizable(false);
			stage.show();
			Stage actualStage = (Stage) generateButton.getScene().getWindow();
			actualStage.close();
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private LinkedList<String> getDays(LinkedList<PresentationOperator> operators) {
		LinkedList<String> days = new LinkedList<>();
		for (PresentationOperator op : operators) {
			String[] from = op.getFrom().split(" ");
			if (!days.contains(from[0] + " " + from[1] + " " + from[2].replace(".", ""))) {
				days.add(from[0] + " " + from[1] + " " + from[2].replace(".", ""));
			}
		}
		return days;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainForm.class.getResource("/MainForm.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 389.0, 249);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fõoldal");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}