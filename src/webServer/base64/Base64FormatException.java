// Base64FormatException.java
// $Id: Base64FormatException.java,v 1.4 2000/08/16 21:37:48 ylafon Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

package webServer.base64 ;

/**
 * Exception for invalid BASE64 streams.
 */

@SuppressWarnings("serial")
public class Base64FormatException extends Exception {

  /**
   * Create that kind of exception
   * @param msg The associated error message 
   */

  public Base64FormatException(String msg) {
	super(msg) ;
  }

}
