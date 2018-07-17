package application.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import application.constant.CommonConst;
import application.exception.SystemException;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public class ApplicationUtil {

	private static final Properties APPLICATION_PROP = new Properties();

	private static final String KEY_APPLICATION_NAME = "application_name";
	private static final String KEY_FXML_DIR = "fxml_dir";
	private static final String KEY_CSS_DIR = "css_dir";
	private static final String KEY_IMAGE_DIR = "image_dir";
	private static final String KEY_DATA_DIR = "data_dir";
	private static final String KEY_DATA_CHAR_CODE = "data_char_code";
	private static final String KEY_SOUND_DIR = "sound_dir";

	static {
		try (InputStream inStream = new BufferedInputStream(
				new FileInputStream(CommonConst.PROPS_APPLICATION))) {
			APPLICATION_PROP.load(inStream);
		} catch (Exception e) {
			throw new SystemException("メッセージ情報の読み込みに失敗しました。\r\nファイル:" + CommonConst.PROPS_MESSAGE.getName());
		}
	}

	public static String getApplicationName() {
		return APPLICATION_PROP.getProperty(KEY_APPLICATION_NAME);
	}

	public static FXMLLoader getFXMLLoader(String fileName) {
		FXMLLoader loader = null;
		if (APPLICATION_PROP.containsKey(KEY_FXML_DIR)) {
			String dirName = APPLICATION_PROP.getProperty(KEY_FXML_DIR);
			if (!CommonUtil.isEmpty(dirName)) {
				URL url = getUrl(dirName, fileName);
				if (url != null) {
					loader = new FXMLLoader(url);
				}
			}
		}
		return loader;
	}

	public static URL getStyleSheetURL(String fileName) {
		URL url = null;
		if (APPLICATION_PROP.containsKey(KEY_CSS_DIR)) {
			String dirName = APPLICATION_PROP.getProperty(KEY_CSS_DIR);
			if (!CommonUtil.isEmpty(dirName)) {
				url = getUrl(dirName, fileName);
			}
		}
		return url;
	}

	public static Image getImage(String fileName) {
		Image image = null;
		if (APPLICATION_PROP.containsKey(KEY_IMAGE_DIR)) {
			String dirName = APPLICATION_PROP.getProperty(KEY_IMAGE_DIR);
			if (!CommonUtil.isEmpty(dirName)) {
				URL url = getUrl(dirName, fileName);
				if (url != null) {
					image = new Image(url.toString());
				}
			}
		}
		return image;
	}

	public static File getSoundFile(String fileName) {
		File soundFile = null;
		if (APPLICATION_PROP.containsKey(KEY_SOUND_DIR)) {
			String dirName = APPLICATION_PROP.getProperty(KEY_SOUND_DIR);
			if (!CommonUtil.isEmpty(dirName)) {
				File file = new File(dirName, fileName);
				if (file.exists() && file.isFile()) {
					soundFile = file;
				}
			}
		}
		return soundFile;
	}

	public static File getDataDir() {
		File dataDir = null;
		if (APPLICATION_PROP.containsKey(KEY_DATA_DIR)) {
			String dirName = APPLICATION_PROP.getProperty(KEY_DATA_DIR);
			if (!CommonUtil.isEmpty(dirName)) {
				dataDir = new File(dirName);
			}
		}
		return dataDir;
	}

	public static String getDataCharCode() {
		return APPLICATION_PROP.getProperty(KEY_DATA_CHAR_CODE);
	}

	private static URL getUrl(String dirName, String fileName) {
		URL url = null;
		File file = new File(dirName, fileName);
		if (file.exists() && file.isFile()) {
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException e) {
				// URL変換に失敗した場合は、スタイルシートの取得ができないとしてnullを返却
				url = null;
			}
		}
		return url;
	}

	public static boolean contains(String key) {
		return APPLICATION_PROP.containsKey(key);
	}

	public static String getProperty(String key) {
		return APPLICATION_PROP.getProperty(key);
	}
}
