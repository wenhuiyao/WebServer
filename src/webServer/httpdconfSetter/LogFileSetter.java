package webServer.httpdconfSetter;

import webServer.HttpdConf;
import webServer.ulti.ConfigurationException;

public class LogFileSetter extends HttpdConfSetter {

	@Override
	public void process(Object path) throws ConfigurationException {
		
		if( !(path instanceof String ))
			throw new ConfigurationException("LogFileSetter: String");
		
		HttpdConf.LOG_FILE = ((String)path).substring(1, ((String)path).length()-1);
	}

}