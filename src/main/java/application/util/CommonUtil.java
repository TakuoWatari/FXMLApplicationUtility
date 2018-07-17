package application.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CommonUtil {

	/** Static Common Member **/
	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
	private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd");

	private static final SecureRandom RANDOM;

	static {
		SecureRandom random = new SecureRandom();
		random.setSeed(System.nanoTime());
		RANDOM = random;
	}

	/**
	 * コンストラクタ
	 * ※インスタンスの生成ができないようにprivate修飾子を付与
	 **/
	private CommonUtil() {}

	public static final boolean isEmpty(String value) {
		boolean result = false;
		if (value == null || value.equals("")) {
			result = true;
		}
		return result;
	}

	public static final String parseNullValue(String value, String nullSameValue) {
		String returnValue = value;
		if (value != null && value.equals(nullSameValue)) {
			returnValue = null;
		}
		return returnValue;
	}

	public static final String parseMulitiLineData(String value, String nullSameValue) {
		String parseValue = value;
		if (!CommonUtil.isEmpty(parseValue)) {
			int escapeIndex = parseValue.indexOf('\\');
			int commentLength = parseValue.length();
			if (escapeIndex != -1) {
				StringBuilder newValue = new StringBuilder(
						parseValue.substring(0, escapeIndex));

				while (escapeIndex != -1) {
					if (escapeIndex == commentLength) {
						throw new IllegalStateException("データを読み込めません。データの中身が破損している可能性があります。");
					}
					int escapeNextIndex = escapeIndex + 1;
					char nextChar = parseValue.charAt(escapeNextIndex);
					switch (nextChar) {
						case '\\' :
							newValue.append('\\');
							break;
						case 'r' :
							newValue.append('\r');
							break;
						case 'n' :
							newValue.append('\n');
							break;
						case 't' :
							newValue.append('\t');
							break;
						default :
							throw new IllegalStateException("データを読み込めません。データの中身が破損している可能性があります。");
					}
					int startIndex = escapeNextIndex + 1;
					escapeIndex = parseValue.indexOf('\\', startIndex);
					if (escapeIndex == -1) {
						newValue.append(parseValue.substring(startIndex));
					} else if (escapeIndex != escapeNextIndex + 1){
						newValue.append(parseValue.substring(startIndex, escapeIndex));
					}
				}

				parseValue = newValue.toString();
			}
			parseValue = parseNullValue(
					parseValue, nullSameValue);
		}
		return parseValue;
	}

	public static final String formatNullValue(String value, String nullSameValue) {
		String formatValue = value;
		if (value == null) {
			formatValue = nullSameValue;
		}
		return formatValue;
	}

	public static final String formatMulitiLineData(String value, String nullSameValue) {

		String formatValue = formatNullValue(
				value, nullSameValue);
		formatValue = formatValue.replaceAll("\\\\", "\\\\\\\\");
		formatValue = formatValue.replaceAll("\\r", "\\\\r");
		formatValue = formatValue.replaceAll("\\n", "\\\\n");
		formatValue = formatValue.replaceAll("\\t", "\\\\t");

		return formatValue;
	}

//	public static final String encodeBase64(byte[] data) {
//		try {
//			ByteArrayOutputStream forEncode = new ByteArrayOutputStream();
//			OutputStream toBase64 = MimeUtility.encode(forEncode, "base64");
//			toBase64.write(data);
//			toBase64.close();
//			return forEncode.toString("iso-8859-1");
//		} catch (MessagingException e) {
//			throw new IllegalStateException("Base64変換に失敗しました。", e);
//		} catch (IOException e) {
//			throw new IllegalStateException("Base64変換に失敗しました。", e);
//		}
//	}

	public static final String encodeBase64(byte[] data) {
		Encoder encoder = Base64.getMimeEncoder();
		return encoder.encodeToString(data);
	}

//	public static final byte[] decodeBase64(String base64) {
//		try {
//			InputStream fromBase64 = MimeUtility.decode(
//					new ByteArrayInputStream(base64.getBytes()), "base64");
//
//			byte[] buf = new byte[1024];
//			ByteArrayOutputStream toByteArray = new ByteArrayOutputStream();
//			for (int len = -1;(len = fromBase64.read(buf)) != -1;) {
//				toByteArray.write(buf, 0, len);
//			}
//			return toByteArray.toByteArray();
//		} catch (MessagingException e) {
//			throw new IllegalStateException("Base64変換に失敗しました。", e);
//		} catch (IOException e) {
//			throw new IllegalStateException("Base64変換に失敗しました。", e);
//		}
//	}

	public static final byte[] decodeBase64(String base64) {
		Decoder decoder = Base64.getMimeDecoder();
		return decoder.decode(base64);
	}

	@SuppressWarnings({ "rawtypes" })
	public static void saveClipboardSelectedTableViewCellData(TableView<? extends Object> table) {

		ObservableList<TablePosition> posList = table.getSelectionModel().getSelectedCells();

		int old_r = -1;
		StringBuilder clipboardString = new StringBuilder();

		for (TablePosition p : posList) {
			int r = p.getRow();
			int c = p.getColumn();
			Object cell = table.getColumns().get(c).getCellData(r);
			if (cell == null)
				cell = "";
			if (old_r == r)
				clipboardString.append('\t');
			else if (old_r != -1)
				clipboardString.append('\n');
			clipboardString.append(cell);
			old_r = r;
		}
		final ClipboardContent content = new ClipboardContent();
		content.putString(clipboardString.toString());
		Clipboard.getSystemClipboard().setContent(content);
	}

	public static final String createRandomStr(String userValues, int length) {

		int dataLength = userValues.length();
		StringBuilder randomValue = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = RANDOM.nextInt(dataLength);
			randomValue.append(userValues.charAt(index));
		}

		return randomValue.toString();
	}

	public static final int getRandomInt(int maxValue) {
		return RANDOM.nextInt(maxValue);
	}

	public static final double getRandomDouble() {
		return RANDOM.nextDouble();
	}

	public static final boolean getRandomBoolean() {
		return RANDOM.nextBoolean();
	}

	public static int getSystemYear() {
		return Integer.valueOf(YEAR_FORMAT.format(new Date()));
	}

	public static int getSystemMonth() {
		return Integer.valueOf(MONTH_FORMAT.format(new Date()));
	}

	public static int getSystemDay() {
		return Integer.valueOf(DAY_FORMAT.format(new Date()));
	}

	public static int getWeekDay(int year, int month, int date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, (month - 1), date, 0, 0, 0);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getMonthMaxDay(int year, int month) {
		int maxDay;
		switch (month) {
			case 1 :
			case 3 :
			case 5 :
			case 7 :
			case 8 :
			case 10 :
			case 12 :
				maxDay = 31;
				break;
			case 4 :
			case 6 :
			case 9 :
			case 11 :
				maxDay = 30;
				break;
			case 2 :
				if ( (year % 4 != 0)
						|| ((year % 100 == 0) && (year % 400 != 0))) {
					maxDay = 28;
				} else {
					maxDay = 29;
				}
				break;
			default:
				throw new IllegalArgumentException("想定外の値が指定されています。");
		}

		return maxDay;
	}

	public static void setMonthMaxDay(int year, int month, List<Integer> dayList) {
		int maxDay = getMonthMaxDay(year, month);
		setMonthMaxDay(dayList, maxDay);
	}

	public static void setMonthMaxDay(List<Integer> dayList, int maxDay) {
		int daySize = dayList.size();
		if (daySize > maxDay) {
			for (int i = daySize; i > maxDay; i--) {
				dayList.remove(i-1);
			}
		} else if (daySize < maxDay) {
			for (int i = daySize; i < maxDay; i++) {
				dayList.add(Integer.valueOf(i+1));
			}
		}
	}
}
