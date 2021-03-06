package Shadow.System;

import Shadow.ShadowClient;
import Shadow.System.Debug.ErrorLog;

public class ShadowEngine {
	public static final ShadowClient client = new ShadowClient();
	
	public void run() {
		client.start();
	}
	
	public static void report(String str) {
		ErrorLog.addErrorMessage(str);
	}
	
	public static void log(String str) {
		ErrorLog.addLogEntry(str);
	}
}