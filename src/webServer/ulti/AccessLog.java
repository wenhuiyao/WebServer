package webServer.ulti;

import java.io.File;
import java.io.IOException;

import webServer.constant.HttpdConf;
import webServer.request.Request;

public class AccessLog {


	public static void initialize() throws IOException {
		if(!verifyFile(HttpdConf.LOG_FILE)){
			throw new IOException("Log file not found: "+HttpdConf.LOG_FILE);
		}
	}

	private static boolean verifyFile(String path) throws IOException {
		File logFile = new File(path);

		if (!logFile.exists()) {
			return logFile.createNewFile();
		}
		return true;
	}
	
	public static void log(Request request, int statusCode){
		String IP, requestLine, userId, rfc1413, time;
		IP = request.getIPAddr();
		requestLine = request.getScriptName();
		userId = "-";
		rfc1413 = "-";
		time = Ulti.timeInLogFormat();
		String content=String.format("%s %s %s [%s] \"%s\" %d", IP, rfc1413, userId, time,
				requestLine, statusCode);
		Ulti.writeFile(content, HttpdConf.LOG_FILE);
		
	}
	
}
