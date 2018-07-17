package application.content;

import java.io.IOException;

import application.contoller.Controller;
import application.util.ApplicationUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class PopupWindow implements SubWindow {

	private Stage window;

	private Controller controller;

	public PopupWindow(Window owner, StageStyle stageStyle, String title,
			String fxmlFileName, Modality modality, boolean resizable, boolean alwaysOnTop,
			String... cssForms)
	throws IOException {

		FXMLLoader loader = ApplicationUtil.getFXMLLoader(fxmlFileName);

		loader.load();

		Controller controller = loader.getController();
		Parent root = loader.getRoot();
		Scene scene = new Scene(root);

		if (cssForms != null) {
			for (String cssForm : cssForms) {
				scene.getStylesheets().add(cssForm);
			}
		}

		Stage popup;
		if (stageStyle != null) {
			popup = new Stage(stageStyle);
		} else {
			popup = new Stage();
		}
		popup.setTitle(title);
		popup.setScene(scene);
		if (owner != null) {
			popup.initOwner(owner);
		}
		if (modality != null) {
			popup.initModality(modality);
		}
		popup.setResizable(resizable);
		popup.setAlwaysOnTop(alwaysOnTop);
		popup.sizeToScene();

		controller.setStage(popup);

		this.window = popup;
		this.controller = controller;
	}

	public void setOwner(Window owner) {
		this.window.initOwner(owner);
	}

	public void setTitle(String title) {
		this.window.setTitle(title);
	}

	public Controller getController() {
		return this.controller;
	}

	public void close() {
		this.window.close();
	}

	public void show() {
		this.window.showAndWait();
	}
}
