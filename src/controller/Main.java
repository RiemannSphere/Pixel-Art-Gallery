package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loadMainWindow();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void loadMainWindow() {
		AnchorPane root;
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MenuView.fxml")); 
			root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			
			MenuController controller = loader.getController();
			controller.setMain(this);
			
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
}
