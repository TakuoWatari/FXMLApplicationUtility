package application.contoller;

import java.util.Optional;

import application.constant.CommonConst;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public abstract class ControllerBase implements Controller {

	private Stage stage;
	private static Alert messageBox;
	private static TextArea errorDetail = new TextArea();

	private static final String TITLE_CONFIRM = "確認";
	private static final String TITLE_INFO = "情報";
	private static final String TITLE_WARN = "警告";
	private static final String TITLE_ERROR = "エラー";
	private static final String TITLE_SYSTEM_ERROR = "システムエラー";

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	protected Stage getStage() {
		return this.stage;
	}

	protected void close() {
		this.stage.close();
	}

	protected void hide() {
		this.stage.hide();
	}

	protected void showInfoMessage(String message) {
		this.show(AlertType.INFORMATION, TITLE_INFO, message, null, null);
	}

	protected void showInfoMessage(String message, Throwable t) {
		this.show(AlertType.INFORMATION, TITLE_INFO, message, null, t);
	}

	protected void showInfoMessage(String message, String content) {
		this.show(AlertType.INFORMATION, TITLE_INFO, message, content, null);
	}

	protected void showInfoMessage(String message, String content, Throwable t) {
		this.show(AlertType.INFORMATION, TITLE_INFO, message, content, t);
	}

	protected void showWarnMessage(String message) {
		this.show(AlertType.WARNING, TITLE_WARN, message, null, null);
	}

	protected void showWarnMessage(String message, Throwable t) {
		this.show(AlertType.WARNING, TITLE_WARN, message, null, t);
	}

	protected void showWarnMessage(String message, String content) {
		this.show(AlertType.WARNING, TITLE_WARN, message, content, null);
	}

	protected void showWarnMessage(String message, String content, Throwable t) {
		this.show(AlertType.WARNING, TITLE_WARN, message, content, t);
	}

	protected void showErrorMessage(String message) {
		this.show(AlertType.ERROR, TITLE_ERROR, message, null, null);
	}

	protected void showErrorMessage(String message, Throwable t) {
		this.show(AlertType.ERROR, TITLE_ERROR, message, null, t);
	}

	protected void showErrorMessage(String message, String content) {
		this.show(AlertType.ERROR, TITLE_ERROR, message, content, null);
	}

	protected void showErrorMessage(String message, String content, Throwable t) {
		this.show(AlertType.ERROR, TITLE_ERROR, message, content, t);
	}

	protected void showSystemErrorMessage(String message) {
		this.show(AlertType.ERROR, TITLE_SYSTEM_ERROR, message, null, null);
	}

	protected void showSystemErrorMessage(String message, Throwable t) {
		this.show(AlertType.ERROR, TITLE_SYSTEM_ERROR, message, null, t);
	}

	protected void showSystemErrorMessage(String message, String content) {
		this.show(AlertType.ERROR, TITLE_SYSTEM_ERROR, message, content, null);
	}

	protected void showSystemErrorMessage(String message, String content, Throwable t) {
		this.show(AlertType.ERROR, TITLE_SYSTEM_ERROR, message, content, t);
	}


	protected Optional<ButtonType> confirm(String message) {
		return this.show(AlertType.CONFIRMATION, TITLE_CONFIRM, message, null, null);
	}

	protected Optional<ButtonType> confirm(String message, String content) {
		return this.show(AlertType.CONFIRMATION, TITLE_CONFIRM, message, content, null);
	}

	private Optional<ButtonType> show(AlertType type, String title, String message, String content, Throwable t) {

		if (messageBox == null) {
			messageBox = new Alert(type);
		} else {
			messageBox.setAlertType(type);
		}

		messageBox.setTitle(title);
		messageBox.setHeaderText(message);
		messageBox.setContentText(content);

		if (t != null) {
			StringBuilder buf = new StringBuilder();
			buf.append("Exception：");
			buf.append(t.getMessage());
			buf.append(CommonConst.LINE_SEPARATOR);
			buf.append(CommonConst.LINE_SEPARATOR);
			buf.append(t.getClass().getName());
			buf.append(CommonConst.LINE_SEPARATOR);
			StackTraceElement[] statckTraces = t.getStackTrace();
			for (StackTraceElement statckTrace : statckTraces) {
				buf.append("\tat ");
				buf.append(statckTrace);
				buf.append(CommonConst.LINE_SEPARATOR);
			}
			Throwable cause = t.getCause();
			while (cause != null) {
				buf.append("cause by ");
				buf.append(cause.getClass().getName());
				buf.append(":");
				buf.append(cause.getMessage());
				buf.append(CommonConst.LINE_SEPARATOR);
				statckTraces = cause.getStackTrace();
				for (StackTraceElement statckTrace : statckTraces) {
					buf.append("\tat ");
					buf.append(statckTrace);
					buf.append(CommonConst.LINE_SEPARATOR);
				}
				cause = cause.getCause();
			}

			errorDetail.setText(buf.toString());
			messageBox.getDialogPane().setExpandableContent(errorDetail);
		}

		Optional<ButtonType> returnVal = messageBox.showAndWait();

		messageBox.setTitle(null);
		messageBox.setHeaderText(null);
		messageBox.setContentText(null);
		messageBox.getDialogPane().setExpandableContent(null);
		errorDetail.setText(null);

		return returnVal;
	}
}
