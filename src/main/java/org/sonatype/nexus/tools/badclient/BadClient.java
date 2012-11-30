package org.sonatype.nexus.tools.badclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class BadClient
{

    public static void main( String[] args )
        throws IOException
    {
        if ( args.length < 1 )
        {
            System.err.println( "Usage: badclient <URL>" );
            System.exit( 1 );
        }

        final String urlArgument = args[0];
        final URL url = new URL( urlArgument );

        final Socket socket = new Socket();
        socket.setReceiveBufferSize( 1 );
        socket.connect( new InetSocketAddress( url.getHost(), url.getPort() ) );

        // MacOS seems does not apply setReceiveBufferSize()
        System.out.printf( "Buffer size: %s\n", socket.getReceiveBufferSize() );

        final OutputStream outputStream = socket.getOutputStream();
        final PrintStream output = new PrintStream( outputStream, false, "US-ASCII" );

        output.printf( "GET %s HTTP/1.1\r\n", url.getPath() );
        output.printf( "Host: %s:%s\r\n", url.getHost(), url.getPort() );
        output.printf( "User-Agent: Nexus-BadClient/1.0\r\n" );
        output.printf( "\r\n" );
        output.printf( "\r\n" );
        output.flush();

        final InputStream inputStream = socket.getInputStream();
        inputStream.read(); // just read a byte, no more
        socket.close();
    }
}
