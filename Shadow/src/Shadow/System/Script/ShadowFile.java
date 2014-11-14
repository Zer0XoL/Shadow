package Shadow.System.Script;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Shadow.System.ShadowEngine;

public class ShadowFile {
	
	public static final String SHADOWFILE_EXTENSION = "shd";
	private String contents, path;
	
	private int lineptr;
	private String remainder;
	
	public ShadowFile(String contents, String path) {
		lineptr = 0;
		remainder = this.contents = contents;
		this.path = path;
	}
	
	public static ShadowFile load(String path) {
		
		int indexExtension = path.indexOf(".");
		if(path.substring(indexExtension, path.length()) != SHADOWFILE_EXTENSION) {
			ShadowEngine.report("Please attempt to load a compatible file. (Extension ." + SHADOWFILE_EXTENSION + ")!");
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String contents = "";
			String line = "";
			while((line = br.readLine()) != null) {
				contents += line;
			}
			return new ShadowFile(contents, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String nextLine() {
		String result = "";
		int index = remainder.indexOf('\n');
		result = contents.substring(0, index);
		remainder = remainder.replace(result, "").trim();
		
		return result;
	}
	
	public void load() {
		load(path);
	}
}
