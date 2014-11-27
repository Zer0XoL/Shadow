package Shadow;

import Shadow.Editor.Editor;
import Shadow.System.ShadowEngine;
import Shadow.System.Debug.ErrorLog;
import Shadow.System.Script.ShadowCommand;
import Shadow.System.Script.ShadowParser;
import Shadow.Util.Math.AABB;
import Shadow.Util.Math.Ray;

public class Application {
	public static void main(String[] args) {
		ShadowEngine engine = new ShadowEngine();
		Editor editor       = new Editor("src/res/testlevel.lvl");
		
		engine.run();
		editor.run();
		
		ShadowCommand.run("echo the command string");
		ShadowCommand.run("add 1.337 3.14");
		
		System.out.println("Parser tokenizer test: " + ShadowParser.getToken("echo this is a test", 0));
		System.out.println("Testing the tokenizer: " + ShadowParser.removeKey("echo my child"));
		
		AABB aabb = new AABB(32, 0, 32, 104, 10, 104);
		Ray r = new Ray(0, 0, 0, 320, 25, 320);
		
		System.out.println("Ray intersection test with AABB result: " + AABB.intersection(r, aabb));
		
		ShadowEngine.report("Error test!");
		ShadowEngine.log("Log test!");
		ErrorLog.dumpToFile();
	}
}
