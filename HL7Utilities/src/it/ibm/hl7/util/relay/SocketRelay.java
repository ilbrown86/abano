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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * this class handles the pumping of data from the incoming socket to the
 * outgoing socket
 */
public class SocketRelay extends Thread
{

	/**
	 * Field inSocket
	 */
	Socket inSocket = null;

	/**
	 * Field outSocket
	 */
	Socket outSocket = null;

	/**
	 * Field in
	 */
	InputStream in = null;

	/**
	 * Field out
	 */
	OutputStream out = null;

	/**
	 * Field done
	 */
	volatile boolean done = false;

	/**
	 * Field tmodel
	 */
	volatile long elapsed = 0;

	long bytes = 0;
	StringBuffer sbuffer = new StringBuffer();
	FileOutputStream fos = null;
	final Object lock = new Object();

	private boolean closed = false;

	/**
	 * Constructor SocketRR
	 *
	 * @param c
	 * @param inputSocket
	 * @param inputStream
	 * @param outputSocket
	 * @param outputStream
	 * @param _textArea
	 * @param format
	 * @param tModel
	 * @param index
	 * @param type
	 * @param slowLink
	 * @throws FileNotFoundException 
	 */
	public SocketRelay(Socket inputSocket, InputStream inputStream, Socket outputSocket, OutputStream outputStream, File f)
			throws FileNotFoundException
	{
		inSocket = inputSocket;
		in = inputStream;
		outSocket = outputSocket;
		out = outputStream;
		fos = new FileOutputStream(f);
		start();
	}

	/**
	 * Method isDone
	 *
	 * @return boolean
	 */
	public boolean isDone()
	{
		return done;
	}

	public String getElapsed()
	{
		return String.valueOf(elapsed);
	}

	/**
	 * Method run
	 */
	public void run()
	{
		try
		{
			byte[] buffer = new byte[4096];
			int saved = 0;
			int len;
			long start = System.currentTimeMillis();
			a: for (;;)
			{

				elapsed = System.currentTimeMillis() - start;

				if (done)
				{
					break;
				}

				// try{
				// len = in.available();
				// }catch(Exception e){len=0;}
				len = buffer.length;

				// Used to be 1, but if we block it doesn't matter
				// however 1 will break with some servers, including apache
				if (len == 0)
				{
					len = buffer.length;
				}
				if (saved + len > buffer.length)
				{
					len = buffer.length - saved;
				}
				int len1 = 0;
				while (len1 == 0)
				{
					try
					{
						len1 = in.read(buffer, saved, len);
						synchronized (lock)
						{
							bytes += len1;
							sbuffer.append(new String(buffer, saved, len1));
						}
						//getLogger().info(uri + (isInput ? " - input  - delta " : " - output - delta ") + len1);
					} catch (Exception ex)
					{
						if (done && (saved == 0))
						{
							break a;
						}
						len1 = -1;
						break;
					}
				}
				len = len1;
				if ((len == -1) && (saved == 0))
				{
					break;
				}
				if (len == -1)
				{
					done = true;
				}

				// No matter how we may (or may not) format it, send it
				// on unformatted - we don't want to mess with how its
				// sent to the other side, just how its displayed
				if ((out != null) && (len > 0))
				{
					out.write(buffer, saved, len);
					fos.write(buffer, saved, len);
					fos.flush();

				}

				{
					String str = new String(buffer, 0, len);
					//textArea.append(str);
					getLogger().info(str);
					// getLogger().info(this + "P     " + str.length());

				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			done = true;
			try
			{
				if (out != null)
				{
					out.flush();
					if (null != outSocket)
					{
						outSocket.shutdownOutput();
					} else
					{
						out.close();
					}
					out = null;
				}
			} catch (Exception e)
			{
			}
			try
			{
				if (fos != null)
					fos.close();

			} catch (Exception e2)
			{
			}
			try
			{
				if (in != null)
				{
					if (inSocket != null)
					{
						inSocket.shutdownInput();
					} else
					{
						in.close();
					}
					in = null;
				}
			} catch (Exception e)
			{
			}
			closed = true;
		}
	}

	/**
	 * Method halt
	 */
	public void halt()
	{
		try
		{
			if (inSocket != null)
			{
				inSocket.close();
			}
			if (outSocket != null)
			{
				outSocket.close();
			}
			inSocket = null;
			outSocket = null;
			if (in != null)
			{
				in.close();
			}
			if (out != null)
			{
				out.close();
			}
			in = null;
			out = null;
			done = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
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


	public boolean isClosed()
	{
		return closed;
	}


}
