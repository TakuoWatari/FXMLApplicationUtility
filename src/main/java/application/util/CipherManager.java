package application.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import application.util.CommonUtil;

public class CipherManager {

	/** 暗号化用オブジェクト **/
	private Cipher encryptChipher;
	/** 復号化用オブジェクト **/
	private Cipher decryptChipher;

	/**
	 * コンストラクタ
	 * @param key 暗号化・復号化キー
	 */
	public CipherManager(String key) {
		initialize(key);
	}

	/**
	 * 初期処理
	 * @param key 暗号化・復号化キー
	 */
	private void initialize(String key) {
		try {
			SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "Blowfish");
			Cipher encipher = Cipher.getInstance("Blowfish");
			encipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sksSpec);
			Cipher decipher = Cipher.getInstance("Blowfish");
			decipher.init(javax.crypto.Cipher.DECRYPT_MODE, sksSpec);

			this.encryptChipher = encipher;
			this.decryptChipher = decipher;
		} catch (NoSuchPaddingException e) {
			throw new IllegalStateException("初期化に失敗しました。", e);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException("初期化に失敗しました。", e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("初期化に失敗しました。", e);
		}
	}

	/**
	 * 引数で指定された文字列を暗号化します。
	 * @param value 暗号化する値
	 * @return 暗号化文字列
	 */
	public final String encrypt(String value) {
		if (CommonUtil.isEmpty(value)) {
			return value;
		}
		try {
			//暗号化
			byte[] encrypted = this.encryptChipher.doFinal(value.getBytes());
			//BASE64変換
			return CommonUtil.encodeBase64(encrypted);
		} catch (BadPaddingException e) {
			throw new IllegalStateException("暗号化に失敗しました。", e);
		} catch (IllegalBlockSizeException e) {
			throw new IllegalStateException("暗号化に失敗しました。", e);
		}
	}

	/**
	 * 引数で指定された文字列を復号化します。
	 * @param value 復号化する値
	 * @return 復号化文字列
	 */
	public final String decrypt(String value) {
		if (CommonUtil.isEmpty(value)) {
			return value;
		}
		try {
			//BASE64逆変換
			byte[] decodeValue = CommonUtil.decodeBase64(value);
			//復号化
			byte[] decrypted = this.decryptChipher.doFinal(decodeValue);
			return new String(decrypted);
		} catch (BadPaddingException e) {
			throw new IllegalStateException("復号化に失敗しました。", e);
		} catch (IllegalBlockSizeException e) {
			throw new IllegalStateException("復号化に失敗しました。", e);
		}
	}
}
