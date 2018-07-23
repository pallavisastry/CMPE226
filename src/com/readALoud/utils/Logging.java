package com.readALoud.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Logging {

	public static PrintStream set() {
		FileOutputStream fos = null;
		try {
			String fullPath = getAbsolutePath();
			String[] dirs = fullPath.split("/");
			String path = "";
			for(String dir : dirs) {
				if(dir.contains(":")){}
				else if(dir.equalsIgnoreCase(".metadata")) {
					break;
				}else {
					path += dir + "/";
				}
			}
			for(int i =0; i<dirs.length; i++) {
				if(dirs[i+1].equalsIgnoreCase("WEB-INF")) {
					path += dirs[i] + "/";
					break;
				}
			}
			path += "logs/";
			System.err.println(path);
			fos = new FileOutputStream(path+"ReadALoadLogging.txt", true);
		} catch (IOException ioe) {
			System.err.println("redirection not possible: " + ioe);
			System.exit(-1);
		}
		PrintStream logger = new PrintStream(fos);
		System.setErr(logger);
		System.setOut(logger);
		return logger;
	}
	
	static String getAbsolutePath(){
		java.security.ProtectionDomain pd =
		Logging.class.getProtectionDomain();
		if ( pd == null ) return null;
		java.security.CodeSource cs = pd.getCodeSource();
		if ( cs == null ) return null;
		java.net.URL url = cs.getLocation();
		if ( url == null ) return null;
		java.io.File f = new File( url.getFile() );
		if (f == null) return null;
		return f.getAbsolutePath();
	}

}
