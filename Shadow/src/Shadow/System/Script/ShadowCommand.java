package Shadow.System.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Shadow.ShadowClient;
import Shadow.ShadowEngine;

public class ShadowCommand {

	public static final ShadowClient client = ShadowEngine.client;
	public final String cmdKey;
	private String cmdString;
	private ShadowCommandAction cmd;

	public static final Map<String, ShadowCommandAction> cmdlist = new HashMap<>();

	public static final ShadowCommand echo = new ShadowCommand("echo", (s) -> System.out.println(s));
	public static final ShadowCommand add = new ShadowCommand("add",
			(s) -> {
				double arg1 = Double.parseDouble(ShadowParser.getToken(s, 0));
				double arg2 = Double.parseDouble(ShadowParser.getToken(s, 1));
				System.out.println(arg1 + " + " + arg2 + " = " + (arg1 + arg2));
			});
	public static final ShadowCommand initViewport = new ShadowCommand("initviewport",
			(s) -> {
				short w = Short.parseShort(ShadowParser.getToken(s, 0));
				short h = Short.parseShort(ShadowParser.getToken(s, 1));
				int cc = Integer.parseInt(ShadowParser.getToken(s, 2));
				client.initViewport(w, h, cc);
			});

	public ShadowCommand(String cmdKey, ShadowCommandAction cmd) {
		cmdlist.put(cmdKey, cmd);
		this.cmdKey = cmdKey;
		this.cmd = cmd;
	}

	public static void run(String cmdString) {
		int keyIndex = 0;
		String arguments = ShadowParser.removeKey(cmdString);
		cmdlist.get(ShadowParser.getToken(cmdString, keyIndex)).command(arguments);
	}
}
