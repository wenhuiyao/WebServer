package webServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;


import webServer.constant.HttpdConf;
import webServer.utils.ConfigurationException;

/**
 * <p>
 * MIME parse mime.types file.
 * </p>
 */
public class MIME {

	private static HashMap< String, String > MIMETable = new HashMap< String, String >( 1200, 0.9f );
	private BufferedReader reader;

	public MIME( String path ) throws IOException {
		reader = new BufferedReader( new FileReader( path ) );
	}

	public MIME( File mimeFile ) throws IOException {
		reader = new BufferedReader( new FileReader( mimeFile ) );
	}

	public void readMIMEType() throws ConfigurationException {
		String line;
		String[] tokens;
		int size = 0;
		try {
			while ( ( line = reader.readLine() ) != null ) {
				line = line.trim();
				// skip comment and blink line
				if ( line.isEmpty() || line.charAt( 0 ) == '#' ) {
					continue;
				}
				// replace all the white space and tab with '#'
				line = line.replaceAll( "[ \t]+", "#" );
				tokens = line.split( "#" );
				if ( tokens.length > 1 ) {
					size = tokens.length;
					for ( int i = 1; i < size; i++ ) {
						MIMETable.put( tokens[i], tokens[0] );
					}
				}
			}
		} catch ( IOException ioe ) {
			throw new ConfigurationException( "Fail to read mime.types" );
		} finally {
			try {
				reader.close();
			} catch ( IOException ioe ) {
			}
		}

	}

	public static String getMIMEType( String extension ) {
		String mime = MIMETable.get( extension );
		if ( mime != null )
			return mime;
		else
			return HttpdConf.DEFAULT_TYPE;
	}

	@SuppressWarnings("unused")
	private void print() {
		Set< String > keys = MIMETable.keySet();
		String[] keyArray = keys.toArray( new String[keys.size()] );
		Arrays.sort( keyArray );
		for ( String str : keyArray ) {
			System.out.println( str + "---->" + MIMETable.get( str ) );
		}
		System.out.println( "\n\nEnd of MIME" );
	}

	public int length() {
		return MIMETable.size();
	}

}
