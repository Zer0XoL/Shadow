package Shadow.System.Script;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Shadow.ShadowEngine;

public class ShadowFile {
	
	public static final String SHADOWFILE_EXTENSION = "shd";
	private String contents, path;
	
	public ShadowFile(String contents, String path) {
		this.contents = contents;
		this.path = path;
	}
	
	public static ShadowFile load(String path) {
		
		int indexExtension = path.indexOf(".") + 1;
		if(path.substring(indexExtension, path.length() - 1) != SHADOWFILE_EXTENSION) {
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
	
	public void load() {
		load(path);
	}
}
