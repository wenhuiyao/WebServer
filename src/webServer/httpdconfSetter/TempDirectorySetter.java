package webServer.httpdconfSetter;

import webServer.HttpdConf;
import webServer.ulti.ConfigurationException;

public class TempDirectorySetter extends HttpdConfSetter{

	@Override
	public void process(Object line) throws ConfigurationException{
		
		if(!(line instanceof String ))
			throw new ConfigurationException("TempDirectorySetter: String");
		HttpdConf.TEMP_DIRECTORY = ((String)line).substring(1,((String)line).length()-1);
	}

	
}