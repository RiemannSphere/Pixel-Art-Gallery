package controller;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.Persistance;
import model.PixelArt;
import model.Shape;

public class LoadController {

	private Main main;

	@FXML
	private TextField nameField;
	@FXML
	private TextField authorField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField size1Field;
	@FXML
	private TextField size2Field;
	@FXML
	private ComboBox<String> shapeCombo;
	@FXML
	private Label warningLabel;
	@FXML
	private Label infoLabel;

	@FXML
	private void initialize() {
		warningLabel.setText("");
		infoLabel.setText("");
		shapeCombo.getItems().addAll("Rectangle", "Square", "Circle");
	}

	@FXML
	private void chooseShape() {
		String shape = shapeCombo.getValue();
		switch (shape) {
		case Shape.REC:
			size2Field.setDisable(false);
			break;
		case Shape.SQR:
			size2Field.setDisable(true);
			size2Field.setText("");
			break;
		case Shape.CIR:
			size2Field.setDisable(true);
			size2Field.setText("");
			break;
		}
	}

	@FXML
	private void loadArtwork() {
		warningLabel.setText("");
		Pattern patternNumber = Pattern.compile("\\d+(\\.\\d+)?");
		Pattern patternInteger = Pattern.compile("\\d+");

		String name = nameField.getText();
		String author = authorField.getText();

		String priceStr = priceField.getText();
		if (priceStr == null || !patternNumber.matcher(priceStr).matches()) {
			priceField.setText("");
			warningLabel.setText("Error: Price must be a number.");
			return;
		}
		Double price = Double.valueOf(priceField.getText());

		String size1Str = size1Field.getText();
		if (size1Str == null || size1Str == "" || !patternInteger.matcher(size1Str).matches()) {
			size1Field.setText("");
			warningLabel.setText("Error: Size must be an integer.");
			return;
		}
		Integer size1 = Integer.valueOf(size1Field.getText());
		
		String size2Str = size2Field.getText();

		if( !size2Field.isDisabled() ) {
			if (size2Str == null || size2Str == "" || !patternInteger.matcher(size2Str).matches()) {
				size2Field.setText("");
				warningLabel.setText("Error: Size must be an integer.");
				return;
			}	
		}
		Integer size2 = Integer.valueOf( size2Str.length() == 0 ? "0" : size2Str);

		String shapeStr = shapeCombo.getValue();

		// choose file
		File file = new FileChooser().showOpenDialog(main.getPrimaryStage());
		
		String fileName = file.getName();
		System.out.println("File Name: " + fileName);
		
		Image img = new Image(file.toURI().toString());
		ImageView imgv = new ImageView(img);
		// save file to img
		File save = new File(Persistance.DATA_DIR + fileName);
		try {
			if (save.createNewFile()) {
				infoLabel.setText("File created: " + save.getName());
			} else {
				warningLabel.setText("Error: File already exists.");
			}
			ImageIO.write(SwingFXUtils.fromFXImage(imgv.getImage(), null), "png", save);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save artwork info to data.dat

		PixelArt artwork = new PixelArt();
		artwork.setName(name);
		artwork.setFileName(fileName);
		artwork.setAuthor(author);
		artwork.setPrice(price);
		Shape shape = null;
		switch (shapeStr) {
		case Shape.REC:
			shape = new Shape(size1, size2);
			break;
		case Shape.SQR:
			shape = new Shape(shapeStr, size1);
			break;
		case Shape.CIR:
			shape = new Shape(shapeStr, size1);
			break;
		}
		artwork.setShape(shape);

		Persistance.save(artwork);

	}

	@FXML
	private void navigateMenu() {
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

	@FXML
	private void navigateLoad() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoadView.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			main.getPrimaryStage().setScene(scene);

			LoadController controller = loader.getController();
			controller.setMain(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void navigateDisplay() {
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
	private void navigateHelp() {
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

	@FXML
	private void navigateExit() {
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

	public void setMain(Main main) {
		this.main = main;
	}

}
