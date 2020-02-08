package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class HelpController {
	
	private Main main;
	
	@FXML
	private WebView helpContent;
	
	@FXML
	private void initialize() {
		String helpHTML;
		try {
			helpHTML = new String ( Files.readAllBytes( Paths.get("C:\\programs\\eclipse-workspace\\PixelGalleryPiotrKolodziejski\\src\\res\\help.html") ) );
			System.out.println(helpHTML);
			helpContent.getEngine().loadContent(helpHTML);
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
	private void goBackToMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MenuView.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			main.getPrimaryStage().setScene(scene);

			MenuController controller = loader.getController();
			controller.setMain(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}

}
