package it.ibm.hl7.util.main;

import java.io.File;

import it.ibm.hl7.util.relay.SocketWaiter;


public class HTTPRelay
{
	public static String folder;

	public static int sourcePort = 9999;
	
	//public static String targetHost = "10.4.2.39";
	public static String targetHost = "10.4.2.221";
	public static int targetPort = 9999;
	//public static String targetHost = "localhost";
	//public static int targetPort = 8888;

	public static void main(String[] args)
	{
		try
		{
			folder = args[0] + File.separator + System.currentTimeMillis();
			new SocketWaiter(sourcePort);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
