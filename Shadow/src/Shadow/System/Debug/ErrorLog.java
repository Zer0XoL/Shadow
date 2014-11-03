package Shadow.System.Debug;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLog {
	public static final String ERROR_FILE = "errorlog.see";
	private static String log = "";
	
	public static void addErrorMessage(String msg) {
		log += msg + "\n";
	}
	
	public static void dumpToFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(ERROR_FILE));
			bw.write(log);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
