package webServer.httpdconfSetter;

import webServer.ulti.ConfigurationException;

public abstract class HttpdConfSetter {

	private static String packageName = null;

	/**
	 * Take Object as parameter, so it is more felixiable when handle httpd.conf
	 * configuration
	 * 
	 * @param line
	 * @throws ConfigurationException
	 *             if the Object is not
	 */
	public abstract void process(Object line) throws ConfigurationException;

	/**
	 * Instaniate subclass object of HttpdConfSetter, the tag name must match class name.
	 * 
	 * @param className The class to instaniate
	 * @return 
	 */
	public static HttpdConfSetter instaniate(String className) {
		try {
			if (packageName == null) {
				String name = HttpdConfSetter.class.getName();
				int indexLastDot = name.lastIndexOf('.');
				packageName = name.substring(0, indexLastDot+1);
			}
			return (HttpdConfSetter) Class.forName(
					packageName + className).newInstance();
		} catch (Exception e) {
			// Ignore the class that not been implemented and print the error 
			// message out for warning.
			System.out.println("Error: Could not find class: " + className
					+ " for HttpdConfSetter");
		}
		return null;
	}
}
