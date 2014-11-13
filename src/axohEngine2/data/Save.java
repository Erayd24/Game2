package axohEngine2.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class Save implements Serializable {
	private static final long serialVersionUID = 5719848248859586331L;
	
	private transient FileOutputStream file_out;
	private transient ObjectOutputStream obj_out;
	private PrintWriter writer;
	private File newfile; 
	
	public void saveState(String fileName, Data data) {
		try {
			file_out = new FileOutputStream("C:/ProgramFiles(x86)/gamedata/saves/" + fileName);
			obj_out = new ObjectOutputStream(file_out);
			obj_out.writeObject(data);
			obj_out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newFile(String file) {
		newfile = new File("C:/Program Files (x86)/gamedata/saves/" + file);
		newfile.getParentFile().mkdirs();
		
		try {
			writer = new PrintWriter(newfile, "UTF-8");
		} catch (Exception e) {
			System.err.println("Unable to make new file...");
		}
		writer.close();
	}
}