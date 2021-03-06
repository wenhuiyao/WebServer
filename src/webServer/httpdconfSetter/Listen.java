package webServer.httpdconfSetter;

import webServer.constant.HttpdConf;
import webServer.utils.ConfigurationException;

public class Listen extends HttpdConfSetter {

	@Override
	public void process(Object portNumber) throws ConfigurationException{
		try{
			if( !(portNumber instanceof String ))
				throw new ConfigurationException("Listen: type String expected");
			
			HttpdConf.LISTEN = Integer.parseInt((String)portNumber);
		}catch(NumberFormatException nfe){
			throw new ConfigurationException("ListenSetter: number format excepton");
		}		
	}

	
	
}
