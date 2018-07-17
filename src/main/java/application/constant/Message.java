package application.constant;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import application.exception.SystemException;

public class Message {

	private static final Properties MESSAGE_PROP = new Properties();

	static {
		try (InputStream inStream = new BufferedInputStream(
				new FileInputStream(CommonConst.PROPS_MESSAGE))) {
			MESSAGE_PROP.load(inStream);
		} catch (Exception e) {
			throw new SystemException("Failed in reading of message information.\r\nMessage File:" + CommonConst.PROPS_MESSAGE.getName());
		}
	}

	public static final String getMessage(String messageId, String... parseValues) {
		String message;
		if (MESSAGE_PROP.containsKey(messageId)) {
			message = MESSAGE_PROP.getProperty(messageId);
			if (parseValues != null) {
				int count = 0;
				for (String parseValue : parseValues) {
					count++;
					message = message.replaceAll("%" + count +"%", parseValue);
				}
			}
		} else {
			message = "There is not message.[" + messageId + "]";
		}
		return message;
	}
}
