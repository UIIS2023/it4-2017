package strategy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveLog implements Save {

	@Override
	public void saveData(Object objectsToSave, String path) {

		//FileOutputStream - zapis u fajl
		try {
			String objects = (String) objectsToSave;
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			fileOutputStream.write(objects.getBytes()); //stringove konvertuje u bajtove
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
