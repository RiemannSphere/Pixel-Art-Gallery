package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController {

	private Main main;

	@FXML
	private void enterGalleryButtonClicked() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/DisplayView.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			main.getPrimaryStage().setScene(scene);

			DisplayController controller = loader.getController();
			controller.setMain(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void exitButtonClicked() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/ExitView.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			main.getPrimaryStage().setScene(scene);

			ExitController controller = loader.getController();
			controller.setMain(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void helpButtonClicked() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/HelpView.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			main.getPrimaryStage().setScene(scene);

			HelpController controller = loader.getController();
			controller.setMain(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}

}
