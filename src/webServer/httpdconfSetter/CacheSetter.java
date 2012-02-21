package webServer.httpdconfSetter;

import webServer.HttpdConf;
import webServer.ulti.ConfigurationException;

public class CacheSetter extends HttpdConfSetter{

	@Override
	public void process(Object line) throws ConfigurationException{
		
		if( !(line instanceof String ))
			throw new ConfigurationException("CacheSetter: String");
		
		if(((String)line).equals("ON"))
			HttpdConf.CACHE_ENABLE = true;
	}

	
}