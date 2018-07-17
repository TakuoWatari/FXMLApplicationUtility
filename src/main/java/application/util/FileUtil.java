package application.util;

import java.io.File;

public class FileUtil {

	/**
	 * インスタンス生成不可
	 */
	private FileUtil() {}

	public static void deleteFile(File target) {
		if (target.exists()) {
			if (target.isFile()) {
				target.delete();
			} else {
				File[] subFiles = target.listFiles();
				for (File subFile : subFiles) {
					deleteFile(subFile);
				}
				target.delete();
			}
		}
	}
}
