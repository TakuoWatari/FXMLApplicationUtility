package application;

import java.net.URL;

import application.contoller.Controller;
import application.model.ApplicationContext;
import application.util.ApplicationUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class ApplicationEx extends Application implements ApplicationBase {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationContext.initialize(primaryStage, this.getParameters());

		FXMLLoader loader = this.getLoader();
		loader.load();

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		URL stylesheetURL = this.getStyleSheetUrl();
		if (stylesheetURL != null) {
			scene.getStylesheets().add(stylesheetURL.toExternalForm());
		}

		Controller controller = loader.getController();
		controller.setStage(primaryStage);

		primaryStage.setTitle(ApplicationUtil.getApplicationName());
		primaryStage.setScene(scene);
		primaryStage.setResizable(this.isResizable());
		primaryStage.centerOnScreen();

		primaryStage.setMinHeight(this.getMinHeight());
		primaryStage.setMinWidth(this.getMinWeight());

		StageStyle stageStyle = this.getStageStyle();
		if (stageStyle != null) {
			primaryStage.initStyle(stageStyle);
		}

		Image icon = this.getIcon();
		if (icon != null) {
			primaryStage.getIcons().add(icon);
		}

		primaryStage.show();
	}
}
