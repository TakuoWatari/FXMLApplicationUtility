package application;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

interface ApplicationBase {

	default StageStyle getStageStyle() {
		return StageStyle.DECORATED;
	}

	FXMLLoader getLoader();

	default URL getStyleSheetUrl() {
		return null;
	}

	default Image getIcon() {
		return null;
	}

	default boolean isResizable() {
		return false;
	}

	default double getMinHeight() {
		return 0.0;
	}

	default double getMinWeight() {
		return 0.0;
	}
}
