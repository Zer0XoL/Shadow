package Shadow.System.Debug;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLog {
	public static final String SHADOW_ERROR_EXTENSION = ".see";
	public static final String SHADOW_LOG_EXTENSION = ".sle";
	
	public static final String ERROR_FILE = "errorlog" + SHADOW_ERROR_EXTENSION;
	public static final String LOG_FILE = "genlog" + SHADOW_LOG_EXTENSION;
	private static String errorlog = "";
	private static String entrylog = ""; //general log string for debugging purposes besides obvious error reports
	
	public static void addErrorMessage(String msg) {
		errorlog += msg + "\n";
	}
	
	public static void addLogEntry(String msg) {
		entrylog += msg + "\n";
	}
	
	public static void dumpToFile() {
		try {
			BufferedWriter bw0 = new BufferedWriter(new FileWriter(ERROR_FILE));
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(LOG_FILE));
			bw0.write(errorlog);
			bw1.write(entrylog);
			bw0.close();
			bw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
