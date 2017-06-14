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
		// operators = impDao.getPresentations(selectedFile);
		operators = impDao.getPresentationsNew(selectedFile);
		section = impDao.getSection(selectedFile);
//		section.setDays(getDays(operators));
		PresentationProblem problem = new PresentationProblem();
		PresentationProblem.setOperators(operators);
		problem.setSection(section);
		problem.setMapSize(section.getSections());
		problem.setX(section.getSectionNumber());
		problem.setY(operators.size());
		int db = 0, min = Integer.MAX_VALUE;
		// Optimal algorithm = new Optimal(problem,min);
		NewGenericAlgorythm algorithm = new NewGenericAlgorythm(problem);
		// GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
		boolean run = algorithm.run();
		// if (run == false) {
		// System.out.println("Nem lehet beosztani az elõadásokat!");
		// }
		Map<String, LinkedList<Integer>> intMap = algorithm.GetMapFullTabla();
		int maxLenght = Integer.MIN_VALUE;
		for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
			if (entry.getValue() != null && maxLenght < entry.getValue().size()) {
				maxLenght = entry.getValue().size();
			}
		}
		Map<String, LinkedList<Presentation>> finalTable = new HashMap<>();
		table = new Presentation[section.getSectionNumber()][maxLenght * 2];
		for (int i = 0; i < section.getSections().size(); i++) {
			for (Map.Entry<String, LinkedList<Integer>> entry : intMap.entrySet()) {
				LinkedList<Presentation> tmpList = new LinkedList<>();
				if (entry.getKey().contains(section.getSections().get(i)) && entry.getKey().contains("DE")) {
					LinkedList<Integer> temp = entry.getValue();
					if (temp != null) {
						tmpList = new LinkedList<>();
						for (int j = 0; j < temp.size(); j++) {
							for (PresentationOperator ope : operators) {
								if (temp.get(j) == ope.getId()) {
									tmpList.add(new Presentation(ope.getId(), ope.getPresentationTitle(),
											ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(),
											ope.getWeight(), ope.getInter()));
									break;
								}
							}
						}
					}
					finalTable.put(section.getSections().get(i), tmpList);
				} else if (entry.getKey().contains(section.getSections().get(i)) && entry.getKey().contains("DU")) {
					LinkedList<Integer> temp = entry.getValue();
					if (temp != null) {
						if (finalTable.get(section.getSections().get(i)) != null) {
							tmpList = finalTable.get(section.getSections().get(i));
						} else {
							tmpList = new LinkedList<>();
						}
						for (int j = 0; j < temp.size(); j++) {
							for (PresentationOperator ope : operators) {
								if (temp.get(j) == ope.getId()) {
									tmpList.add(new Presentation(ope.getId(), ope.getPresentationTitle(),
											ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(), ope.isPiority(),
											ope.getWeight(), ope.getInter()));
									break;
								}
							}
						}
					}
					finalTable.put(section.getSections().get(i), new LinkedList<>(tmpList));
				}
			}
		}
		for (int i = 0; i < section.getSections().size(); i++) {
			for (Map.Entry<String, LinkedList<Presentation>> entry : finalTable.entrySet()) {
				if (entry.getKey().contains(section.getSections().get(i))) {
					LinkedList<Presentation> temp = entry.getValue();
					if (temp != null) {
						for (int j = 0; j < temp.size(); j++) {
							table[i][j] = new Presentation(temp.get(j).getId(), temp.get(j).getPresentationTitle(), temp.get(j).getActor(),
									temp.get(j).getTopic(), temp.get(j).getFrom(), temp.get(j).getTo(), temp.get(j).isPoirity(), temp.get(j).getWeight(),
									temp.get(j).getInter());
						}
					}
				}
			}
		}
		// tableList = new LinkedList<>();
		// for(int i =0;i<section.getDays().size();i++){
		// table= new Presentation[section.getSectionNumber()][maxLenght];
		// int k = 0;
		// for (Map.Entry<String, LinkedList<Integer>> entry :
		// intMap.entrySet()) {
		// if(entry.getKey().contains(section.getDays().get(i))){
		// LinkedList<Integer> temp = entry.getValue();
		// for (int j = 0; j < temp.size(); j++) {
		// for (PresentationOperator ope : operators) {
		// if(temp.get(j) == ope.getId()){
		// table[k][j] = new Presentation(ope.getId(),
		// ope.getPresentationTitle(),
		// ope.getActor(), ope.getTopic(), ope.getFrom(), ope.getTo(),
		// ope.isPiority(), ope.getWeight(), ope.getInter());
		// break;
		// }
		// }
		// }
		// k++;
		// }
		// }
		// tableList.add(table);

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
//			controller.setFirstGrid(tableList, operators, section);
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