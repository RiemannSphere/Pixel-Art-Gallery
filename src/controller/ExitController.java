package controller;


import javafx.fxml.FXML;

public class ExitController {

	private Main main;
	
	@FXML
	private void exitApp() {
		main.getPrimaryStage().close();
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

}
