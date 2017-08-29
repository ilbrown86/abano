/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.ibm.hl7.util.relay;

import it.ibm.hl7.util.main.HTTPRelay;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * a connection listens to a single current connection
 */
class Connection extends Thread
{

	static int count = 0;

	String name;
	int id;

	static
	{

		PropertyConfigurator.configure(Connection.class.getResource("/conf/log4j.properties"));
	}

	Socket inSocket = null;

	/**
	 * Field rr1
	 */
	InputSocketRelay rr1 = null;

	/**
	 * Field rr2
	 */
	OutputSocketRelay rr2 = null;

	/**
	 * Field slowLink
	 */

	/**
	 * Constructor Connection
	 * 
	 * @param l
	 * @param s
	 */
	public Connection(Socket s)
	{
		id = ++count;
		name = "connection_" + id;
		inSocket = s;
		start();
	}

	/**
	 * Method run
	 */
	public void run()
	{

		System.out.println(name + " START");
		try
		{
			String socketURI = null;

			InputStream tmpIn1 = null;
			OutputStream tmpOut1 = null;
			InputStream tmpIn2 = null;
			OutputStream tmpOut2 = null;
			if (tmpIn1 == null)
			{
				tmpIn1 = inSocket.getInputStream();
			}
			if (inSocket != null)
			{
				tmpOut1 = inSocket.getOutputStream();
			}
			Socket outSocket = new Socket(HTTPRelay.targetHost, HTTPRelay.targetPort);
			tmpIn2 = outSocket.getInputStream();
			tmpOut2 = outSocket.getOutputStream();


			File f = new File(HTTPRelay.folder);
			f.mkdirs();
			String paddedId = String.format("%03d", id);

			// this is the channel to the endpoint
			// inSocket.toString()
			rr1 = new InputSocketRelay(inSocket, tmpIn1, outSocket, tmpOut2, f, paddedId);

			// this is the channel from the endpoint
			rr2 = new OutputSocketRelay(outSocket, tmpIn2, inSocket, tmpOut1, f, paddedId);

			//int count = 0;
			while (/*(rr1 != null) || (rr2 != null) || */(!rr1.isClosed()) || (!rr2.isClosed()))
			{

				dumpBytes(socketURI);

				// Only loop as long as the connection to the target
				// machine is available - once that's gone we can stop.
				// The old way, loop until both are closed, left us
				// looping forever since no one closed the 1st one.

				if ((null != rr1) && rr1.isDone())
				{
					getLogger().info(hashCode() + "," + socketURI + ",bytesToServer," + rr1.bytes + ",");
					//rr1 = null;
				}

				if ((null != rr2) && rr2.isDone())
				{
					getLogger().info(hashCode() + "," + socketURI + ",bytesFromServer," + rr2.bytes + ",");
					//rr2 = null;
				}

				/*count++;
				if (count > 100)
				{
					rr1.halt();
					rr2.halt();
				}*/

				synchronized (this)
				{
					this.wait(100); // Safety just incase we're not told to wake
					// up.
				}
			}

			/*
			 * getLogger().info( "END " + bufferedData.substring(0, bufferedData
			 * .indexOf('\n') - 1));
			 */

		} catch (Exception e)
		{
			System.out.println("ERROR: " + e.getMessage());
		}

		System.out.println(name + " END");
	}

	private void dumpBytes(String socketURI)
	{
		if (rr1 != null)
			synchronized (rr1.lock)
			{
				if (rr1.bytes > 0)
				{
					getLogger().info(hashCode() + "," + socketURI + ",bytesToServer," + rr1.bytes + ",");
					rr1.sbuffer = new StringBuffer();
					rr1.bytes = 0;
				}
			}
		if (rr2 != null)
			synchronized (rr2.lock)
			{
				if (rr2.bytes > 0)
				{
					getLogger().info(hashCode() + "," + socketURI + ",bytesFromServer," + rr2.bytes + ",");
					rr2.sbuffer = new StringBuffer();
					rr2.bytes = 0;
				}
			}
	}

	/**
	 * Method wakeUp
	 */
	synchronized void wakeUp()
	{
		this.notifyAll();
	}

	protected Logger logger = null;

	public Logger getLogger()
	{
		if (logger == null)
		{
			logger = Logger.getLogger(this.getClass());
		}
		return logger;
	}

}
