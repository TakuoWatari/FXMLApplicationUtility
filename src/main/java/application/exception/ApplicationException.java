package application.exception;

public class ApplicationException extends Exception {

	/** デフォルトシリアルID **/
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 * @param t エラー情報(原因)
	 */
	public ApplicationException(String message, Throwable t) {
		super(message, t);
	}
}
