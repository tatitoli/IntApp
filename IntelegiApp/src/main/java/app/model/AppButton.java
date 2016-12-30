package app.model;

import javafx.scene.control.Button;

public class AppButton extends Button{
	
	private int presentationId;

	public AppButton(int id) {
		super();
		this.presentationId = id;
	}

	public int getPresentationId() {
		return presentationId;
	}

	public void setPresentationId(int presentationId) {
		this.presentationId = presentationId;
	}
}
