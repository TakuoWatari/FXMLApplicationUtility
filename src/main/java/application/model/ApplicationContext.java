package application.model;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application.Parameters;
import javafx.stage.Stage;

public final class ApplicationContext {

	public static String KEY_PRIMARY_STAGE = "primaryStage";
	public static String KEY_APPLICATION_PARAMS = "applicationParams";

	private static Map<String, Object> DATA_MAP = new HashMap<>();

	private ApplicationContext() {
		// インスタンス生成不可
	}

	public static void initialize(Stage primaryStage, Parameters params) {
		DATA_MAP.put(KEY_PRIMARY_STAGE, primaryStage);
		DATA_MAP.put(KEY_APPLICATION_PARAMS, params);
	}

	public static Stage getPrimaryStage() {
		Stage stage = null;
		if (DATA_MAP.containsKey(KEY_PRIMARY_STAGE)) {
			stage = (Stage) DATA_MAP.get(KEY_PRIMARY_STAGE);
		}
		return stage;
	}

	public static Parameters getParameters() {
		Parameters params = null;
		if (DATA_MAP.containsKey(KEY_APPLICATION_PARAMS)) {
			params = (Parameters) DATA_MAP.get(KEY_APPLICATION_PARAMS);
		}
		return params;
	}

	public static void set(String key, Object value) {
		if (value != null) {
			DATA_MAP.put(key, value);
		} else {
			DATA_MAP.remove(key);
		}
	}

	public static Object get(String key) {
		return DATA_MAP.get(key);
	}

	public static boolean contains(String key) {
		return DATA_MAP.containsKey(key);
	}

	public static void remove(String key) {
		DATA_MAP.remove(key);
	}

	public static void clear() {
		DATA_MAP.clear();
	}
}
