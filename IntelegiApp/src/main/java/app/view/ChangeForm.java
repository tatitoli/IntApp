package app.view;

import app.sort.Presentation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangeForm {

	@FXML
	Button changeButton;

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

	@FXML
	public void changeButtonAction(ActionEvent event) {

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
						break;
					}
				}
			}
		}
	}
}
