package Shadow;

import Shadow.System.Debug.ErrorLog;

public class ShadowEngine {
	public static final ShadowClient client = new ShadowClient();
	
	public void run() {
		client.start();
	}
	
	public static void report(String str) {
		ErrorLog.addErrorMessage(str);
	}
}
