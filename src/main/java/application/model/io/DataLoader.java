package application.model.io;

import java.io.IOException;
import java.util.List;

public interface DataLoader<T> {

	public List<T> getDataList() throws IOException;

}
