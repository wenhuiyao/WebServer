package webServer.ulti;

public class ServerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String message="";
	
	public ServerException(int statusCode){
		this.statusCode = statusCode;
	}
	
	public ServerException(int statusCode, String message){
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public int getStatusCode(){
		return statusCode;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void printMessage(){
		System.out.println(message);
	}
}
