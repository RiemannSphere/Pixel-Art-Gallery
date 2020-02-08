package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import model.Persistance;
import model.PixelArt;
import model.Shape;

public class DisplayController {

	@FXML
	private Button menuBtn;
	@FXML
	private Button loadBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button displayBtn;
	@FXML
	private Button helpBtn;
	@FXML
	private Button exitBtn;

	@FXML
	private TableView<PixelArt> artTable;
	@FXML
	private TableColumn<PixelArt, String> nameCol;
	@FXML
	private TableColumn<PixelArt, String> authorCol;
	@FXML
	private TableColumn<PixelArt, String> priceCol;
	@FXML
	private TableColumn<PixelArt, String> shapeCol;
	@FXML
	private TableColumn<PixelArt, String> size1Col;
	@FXML
	private TableColumn<PixelArt, String> size2Col;
	@FXML
	private TableColumn<PixelArt, String> areaCol;

	@FXML
	private Polygon arrow;
	@FXML
	private Rectangle sidebar;
	@FXML
	private HBox hideAndShowBtn;
	@FXML
	private Tooltip tableTooltip;

	private ObservableList<PixelArt> data = FXCollections.observableArrayList();

	private Main main;

	private boolean sidebarHidden;

	@FXML
	private void initialize() {
		sidebarHidden = false;

		menuBtn.setTextFill(Color.rgb(9, 4, 44));
		loadBtn.setTextFill(Color.rgb(9, 4, 44));
		editBtn.setTextFill(Color.rgb(9, 4, 44));
		displayBtn.setTextFill(Color.rgb(248, 207, 93));

		initTableData();
		artTable.setItems(data);

		nameCol.setCellValueFactory(new PropertyValueFactory<PixelArt, String>("name"));
		authorCol.setCellValueFactory(new PropertyValueFactory<PixelArt, String>("author"));
		priceCol.setCellValueFactory(new PropertyValueFactory<PixelArt, String>("price"));
		shapeCol.setCellValueFactory(new PropertyValueFactory<PixelArt, String>("shape"));
		size1Col.setCellValueFactory(param -> {
			StringProperty sp = new SimpleStringProperty();
			String shape = param.getValue().getShape().getShape();

			switch (shape) {
			case Shape.REC:
				String width = String.valueOf(param.getValue().getShape().getWidth());
				sp.setValue(width);
				return sp;
			case Shape.SQR:
				String size = String.valueOf(param.getValue().getShape().getWidth());
				sp.setValue(size);
				return sp;
			case Shape.CIR:
				String rad = String.valueOf(param.getValue().getShape().getRadius());
				sp.setValue(rad);
				return sp;
			}
			return sp;
		});
		size2Col.setCellValueFactory(param -> {
			StringProperty sp = new SimpleStringProperty();
			String shape = param.getValue().getShape().getShape();

			switch (shape) {
			case Shape.REC:
				String height = String.valueOf(param.getValue().getShape().getHeight());
				sp.setValue(height);
				return sp;
			case Shape.SQR:
				sp.setValue("");
				return sp;
			case Shape.CIR:
				sp.setValue("");
				return sp;
			}
			return sp;
		});
		areaCol.setCellValueFactory(param -> {
			StringProperty sp = new SimpleStringProperty();
			String area = String.format("%.0f", param.getValue().getShape().getArea() / 1_000);
			sp.setValue(area);
			return sp;
		});

		tableTooltip.setText("price in $\nsize in px\narea in kpx");
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

	@FXML
	private void onEdit() {
		PixelArt selected = null;
	    if (artTable.getSelectionModel().getSelectedItem() != null) {
	        selected = artTable.getSelectionModel().getSelectedItem();
	    }else {
	    	System.err.println("[onEdit] : no artwork selected");
	    	return;
	    }
		
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/EditView.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			main.getPrimaryStage().setScene(scene);

			EditController controller = loader.getController();
			controller.setMain(main, selected);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onView() {
//		AnchorPane root = new AnchorPane();
//		
//		ImageView iv = new ImageView(image);
//		
//		root.getChildren().add(iv);
//		Scene modal = new Scene(root);
//		main.getPrimaryStage().setScene(modal);
	}
	
	@FXML
	private void onComment() {
		
	}
	
	private void initTableData() {
		List<PixelArt> artData = Persistance.list();
		data.addAll(artData);
	}

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	private void hideAndShowSidebar() {
		hideAndShowBtn.setRotate(hideAndShowBtn.getRotate() + 180);
		if (sidebarHidden) {
			hideAndShowBtn.setTranslateX(0);
			menuBtn.setVisible(true);
			loadBtn.setVisible(true);
			displayBtn.setVisible(true);
			helpBtn.setVisible(true);
			exitBtn.setVisible(true);

			sidebar.setWidth(190);
			sidebar.setStyle("-fx-fill: url(\"/res/bar.jpg\");");
			arrow.setVisible(true);

			AnchorPane.setLeftAnchor(artTable, 195d);

			sidebarHidden = false;
		} else {
			hideAndShowBtn.setTranslateX(-140);
			menuBtn.setVisible(false);
			loadBtn.setVisible(false);
			displayBtn.setVisible(false);
			helpBtn.setVisible(false);
			exitBtn.setVisible(false);

			sidebar.setWidth(50);
			sidebar.setStyle("-fx-fill: url(\"/res/hidden.jpg\");");
			arrow.setVisible(false);

			AnchorPane.setLeftAnchor(artTable, 55d);

			sidebarHidden = true;
		}

	}

}
