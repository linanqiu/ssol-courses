import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SaveLoad {

	public SaveLoad() {

	}

	public void serialize(ArrayList<Section> sections) throws IOException {
		FileOutputStream fileOut = new FileOutputStream("sections.ssol");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(sections);
		out.close();
		fileOut.close();

	}

	public ArrayList<Section> deserialize() throws IOException,
			ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream("sections.ssol");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Section> sectionsRead = (ArrayList<Section>) in.readObject();
		in.close();
		fileIn.close();

		return sectionsRead;
	}
}
