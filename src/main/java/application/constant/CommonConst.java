package application.constant;

import java.io.File;

public class CommonConst {

	/** コンストラクタ(インスタンス生成不可 **/
	private CommonConst() {}

	public static final File DIRECTORY_FXML = new File("fxml");

	public static final File DIRECTORY_CSS = new File("css");

	public static final File DIRECTORY_PROPS = new File("props");

	public static final File DIRECTORY_IMAGE = new File("image");

	public static final File DIRECTORY_SOUND = new File("sound");

	public static final File PROPS_APPLICATION = new File(DIRECTORY_PROPS, "application.properties");

	public static final File PROPS_MESSAGE = new File(DIRECTORY_PROPS, "message.properties");

	public static final String COMMA = ",";

	public static final String TAB = "\t";

	public static final String LINE_SEPARATOR = "\r\n";

	public static final String FLAG_ON = "1";

	public static final String FLAG_OFF = "0";
}
