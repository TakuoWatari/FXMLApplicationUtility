package application.model.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

abstract class DataLoaderBase<T> implements DataLoader<T> {

	private File dataFile;

	public DataLoaderBase(File dataFile) {
		this.dataFile = dataFile;
	}

	public List<T> getDataList() throws IOException {
		List<T> dataList;

		try (BufferedReader reader = new BufferedReader(
				new FileReader(this.dataFile))) {
			dataList = load(reader);
		}

		return dataList;
	}

	abstract protected List<T> load(BufferedReader reader) throws IOException;
}
