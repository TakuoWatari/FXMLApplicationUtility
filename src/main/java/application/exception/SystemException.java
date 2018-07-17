package application.exception;

public class SystemException extends RuntimeException {

	/** デフォルトシリアルID **/
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 * @param t エラー情報(原因)
	 */
	public SystemException(String message, Throwable t) {
		super(message, t);
	}
}
