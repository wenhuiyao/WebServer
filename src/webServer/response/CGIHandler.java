package webServer.response;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import webServer.WebServer;
import webServer.constant.EnvVarTable;
import webServer.constant.HttpdConf;
import webServer.constant.ResponseTable;
import webServer.request.Request;
import webServer.utils.ServerException;
import webServer.utils.Utils;

public class CGIHandler {

	/**
	 * Send script to external intepretere for execution, and read in execution result.
	 * 
	 * @param request
	 * @return
	 * @throws ServerException
	 */
	public CGIOutputStreamReader sendScript( Request request ) throws ServerException {
		try {
			String scriptPath = getScriptPath( request.getURI() );
			ProcessBuilder pb = new ProcessBuilder( scriptPath, request.getURI() );
			addEnvironmentVariables( pb.environment(), request );
			Process process;
			if ( request.getMethod().equals( Request.GET ) || request.equals( Request.HEAD ) ) {
				pb.environment().put( EnvVarTable.QUERY_STRING, request.getParameterString() );
				process = pb.start();
			} else {
				process = pb.start();
				servePostParameters( process, request );
			}

			return new CGIOutputStreamReader( process.getInputStream() );

		} catch ( IOException e ) {
			e.printStackTrace();
			throw new ServerException( ResponseTable.INTERNAL_SERVER_ERROR, "Fail to execute script" );
		}

	}

	private String getScriptPath( String URI ) throws IOException, ServerException {
		String ext = Utils.getFileExtension( new File( URI ) );
		String scriptPath = HttpdConf.CGI_HANDLER.get( ext );
		if ( scriptPath == null )
			return extractScriptPath( URI );
		return scriptPath;
	}

	private String extractScriptPath( String script ) throws ServerException {
		BufferedReader reader=null;
		try {
			reader = new BufferedReader( new FileReader( script ) );
			String scriptPath = reader.readLine();
			if ( !scriptPath.isEmpty() )
				return scriptPath.replace( "#!", "" );
		} catch ( IOException ioe ) {
		} finally {
			try {
				if ( reader != null )
					reader.close();
			} catch ( IOException ioe ) {

			}
		}

		throw new ServerException( ResponseTable.INTERNAL_SERVER_ERROR );

	}

	private void addEnvironmentVariables( Map< String, String > env, Request request ) {
		addNonHeaderFieldEnvVar( env, request );
		addHeaderFieldsEnvVar( env, request.getHeaderFields() );
	}

	private void addNonHeaderFieldEnvVar( Map< String, String > env, Request request ) {
		env.put( EnvVarTable.SERVER_NAME, WebServer.SERVER_NAME );
		env.put( EnvVarTable.SERVER_SOFTWARE, WebServer.SERVER_SOFTWARE );
		env.put( EnvVarTable.GATEWAY_INTERFACE, WebServer.GATEWAY_INTERFACE );
		env.put( EnvVarTable.SERVER_PORT, Integer.toString( HttpdConf.LISTEN ) );
		env.put( EnvVarTable.REMOTE_ADDR, request.getIPAddr() );
		env.put( EnvVarTable.REMOTE_USER, request.getRemoteUser() );
		env.put( EnvVarTable.SERVER_PROTOCOL, request.getHttpVersion() );
		env.put( EnvVarTable.REQUEST_METHOD, request.getMethod() );
		env.put( EnvVarTable.PATH_INFO, request.getPathInfo() );
		env.put( EnvVarTable.SCRIPT_NAME, request.getScriptName() );
		env.put( EnvVarTable.PATH_TRANSLATED, ( request.getPathInfo().isEmpty() ) ? "" : request.getURI() );
	}

	private void addHeaderFieldsEnvVar( Map< String, String > env, Map< String, String > headers ) {
		Set< String > keySet = headers.keySet();
		for ( String key : keySet ) {
			if ( EnvVarTable.containKey( key ) )
				env.put( EnvVarTable.get( key ), headers.get( key ) );
		}
	}

	private void servePostParameters( Process p, Request request ) throws IOException {

		BufferedOutputStream args = new BufferedOutputStream( p.getOutputStream() );
		args.write( request.getParameterByteArray() );
		args.close();
	}

}
