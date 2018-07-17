package application.content;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

	@Override
	public void replaceText(int start, int end, String text) {
		super.replaceText(start, end, this.parse(text));
	}

	@Override
	public void replaceSelection(String text) {
		super.replaceSelection(this.parse(text));
	}

	@Override
	public void appendText(String text) {
		super.appendText(this.parse(text));
	}

	private String parse(String text) {
		StringBuilder sb = new StringBuilder();
		int length = text.length();
		for (int i = 0; i < length; i++) {
			char c = text.charAt(i);
			if (c >= '０' && c <= '９') {
				c = (char)(c - '０' + '0');
			}
			switch (c) {
				case '0' :
				case '1' :
				case '2' :
				case '3' :
				case '4' :
				case '5' :
				case '6' :
				case '7' :
				case '8' :
				case '9' :
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
}
