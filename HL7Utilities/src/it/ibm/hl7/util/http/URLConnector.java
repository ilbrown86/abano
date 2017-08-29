package it.ibm.hl7.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class URLConnector
{

	protected HttpURLConnection connection;

	protected final Logger logger = Logger.getLogger(getClass().getName());

	public int getResponse(String urlString, Properties requestHeaders, Properties cookies, String body, String connectMethod,
			Map<String, List<String>> responseHeaders, OutputStream os, int maxredirect) throws IOException, InterruptedException
	{
		return getResponse(urlString, requestHeaders, cookies, body, connectMethod, responseHeaders, os, maxredirect, null, null);
	}

	public int getResponse(String urlString, Properties requestHeaders, Properties cookies, String body, String connectMethod,
			Map<String, List<String>> responseHeaders, OutputStream os, int maxredirect, HostnameVerifier verifier, SSLSocketFactory factory)
			throws IOException, InterruptedException
	{
		return getResponse(urlString, requestHeaders, cookies, body, connectMethod, responseHeaders, os, 0, maxredirect, verifier, factory, null,
				null);
	}

	/**
	 * 
	 * @param urlString
	 * @param requestHeaders
	 * @param cookies
	 * @param body
	 * @param connectMethod
	 * @param responseHeaders
	 * @param os
	 * @param maxredirect
	 * @param verifier
	 * @param factory
	 * @param username: used for basic authentication, only if protocol is https
	 * @param password: used for basic authentication, only if protocol is https
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public int getResponse(String urlString, Properties requestHeaders, Properties cookies, String body, String connectMethod,
			Map<String, List<String>> responseHeaders, OutputStream os, int maxredirect, HostnameVerifier verifier, SSLSocketFactory factory,
			String username, String password) throws IOException, InterruptedException
	{
		return getResponse(urlString, requestHeaders, cookies, body, connectMethod, responseHeaders, os, 0, maxredirect, verifier, factory, username,
				password);
	}

	protected int getResponse(String urlString, Properties requestHeaders, Properties cookies, String body, String connectMethod,
			Map<String, List<String>> responseHeaders, OutputStream os, int count, int maxredirect) throws IOException, InterruptedException
	{
		return getResponse(urlString, requestHeaders, cookies, body, connectMethod, responseHeaders, os, count, maxredirect, null, null, null, null);
	}

	protected int getResponse(String urlString, Properties requestHeaders, Properties cookies, String body, String connectMethod,
			Map<String, List<String>> responseHeaders, OutputStream os, int count, int maxredirect, HostnameVerifier verifier,
			SSLSocketFactory factory, String username, String password) throws IOException, InterruptedException
	{

		if (++count > maxredirect)
		{
			if (logger.isLoggable(Level.WARNING))
			{
				logger.warning("Exceeded limit of redirects: last url is " + urlString);
			}
			return -1;
		}

		URL url = new URL(urlString);

		URLConnection urlconnection = url.openConnection();
		connection = (HttpURLConnection) urlconnection;

		connection.setDoOutput(true);
		connection.setDoInput(true);

		if (urlString.startsWith("https"))
		{
			HttpsURLConnection sconn = (HttpsURLConnection) connection;

			if (verifier != null)
			{
				sconn.setHostnameVerifier(verifier);

			}
			if (factory != null)
			{
				sconn.setSSLSocketFactory(factory);
			}

		}

		if (username != null && username.length() > 0 && password != null)
		{
			String userpass = username + ":" + password;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			connection.setRequestProperty("Authorization", basicAuth);
		}

		if (connectMethod != null)
		{
			connection.setRequestMethod(connectMethod);
		}

		connection.setInstanceFollowRedirects(false);
		/*
		 * if (!cookies.isEmpty()) { connection.setRequestProperty("Cookie",
		 * encodeCookieMap(cookies)); }
		 */

		String charset = null;
		if (requestHeaders != null)
		{
			Set<String> keys = requestHeaders.stringPropertyNames();
			for (String key : keys)
			{
				if (charset == null && "content-type".equals(key.toLowerCase()))
				{
					charset = parseCharset(requestHeaders.getProperty(key));
				}
				connection.setRequestProperty(key, requestHeaders.getProperty(key));
			}
		}

		if (cookies != null)
		{
			String cookieString = encodeCookieMap(cookies);
			connection.addRequestProperty("Cookie", cookieString);
		}

		if (body != null && body.length() > 0)
		{
			checkInterrupt();

			OutputStream out = connection.getOutputStream();

			try
			{
				checkInterrupt();
				if (charset == null)
				{
					charset = "UTF-8";
				}
				out.write(body.getBytes(charset));
			} catch (Throwable t)
			{
				t.printStackTrace();
			} finally
			{
				out.close();
			}
		}

		checkInterrupt();
		connection.connect();

		Map<String, List<String>> headerFields = null;

		checkInterrupt();
		headerFields = connection.getHeaderFields();

		if (responseHeaders != null)
		{
			Iterator<String> iterator = headerFields.keySet().iterator();
			while (iterator.hasNext())
			{
				String key = iterator.next();
				List<String> value = headerFields.get(key);
				responseHeaders.put(key, value);
			}
		}

		List<String> newCookies = headerFields.get("Set-Cookie");

		if (newCookies != null && !newCookies.isEmpty())
		{
			for (Iterator<String> i = newCookies.iterator(); i.hasNext();)
			{
				String cookieString = i.next();
				int semiColon = cookieString.indexOf(";");
				if (semiColon != -1)
				{
					cookieString = cookieString.substring(0, semiColon);
				}
				int aIndex = cookieString.indexOf("=");
				if (aIndex != -1)
				{
					String left = cookieString.substring(0, aIndex);
					String right = cookieString.substring(aIndex + 1);
					if (cookies == null)
					{
						cookies = new Properties();
					}
					cookies.put(left, right);
				}
			}
		}

		checkInterrupt();
		int code = connection.getResponseCode();
		if (code == 302)
		{
			String newLocation = connection.getHeaderField("location");
			if (logger.isLoggable(Level.FINE))
			{
				logger.finest("Redirecting to " + newLocation);
			}
			connection.disconnect();

			checkInterrupt();
			int incode = getResponse(newLocation, requestHeaders, cookies, body, connectMethod, new HashMap<String, List<String>>(), os, count,
					maxredirect, verifier, factory, username, password);
			if (incode > -1)
			{
				code = incode;
			}
			// } else if (code == 500)
			// {
			// return;
		} else if (code < 300)
		{
			checkInterrupt();
			InputStream inputStream = connection.getInputStream();
			if (inputStream != null)
			{

				byte buf[] = new byte[4096];
				int read;
				while ((read = inputStream.read(buf, 0, 4096)) != -1)
				{
					if (os != null)
					{
						checkInterrupt();
						os.write(buf, 0, read);
					}
				}
			}
		} else
		{
			checkInterrupt();
			InputStream inputStream = connection.getErrorStream();
			if (inputStream != null)
			{
				byte buf[] = new byte[4096];
				int read;
				while ((read = inputStream.read(buf, 0, 4096)) != -1)
				{
					if (os != null)
					{
						checkInterrupt();
						os.write(buf, 0, read);
					}
				}
			}
		}
		return code;

	}

	private String parseCharset(String contentType)
	{
		int indof = contentType.lastIndexOf(";");
		if (indof > -1 && indof < (contentType.length() - 1))
		{
			contentType = contentType.substring(indof + 1).trim();
			if (contentType.toLowerCase().startsWith("charset"))
			{
				indof = contentType.lastIndexOf('=');
				if (indof > -1 && indof < (contentType.length() - 1))
				{
					contentType = contentType.substring(indof + 1).trim();
					if (Charset.availableCharsets().containsKey(contentType))
					{
						return contentType.trim();
					}
				}
			}
		}
		return null;
	}

	private void checkInterrupt() throws InterruptedException
	{
		if (shouldInterrupt)
		{
			throw new InterruptedException("SHOULD_INTERRUPT");
		}
	}

	public static String encodeCookieMap(Properties cookies)
	{
		StringBuffer cookieString = new StringBuffer();
		Iterator<String> i = cookies.stringPropertyNames().iterator();
		while (i.hasNext())
		{
			String cookie = i.next();
			if (cookieString.length() != 0)
			{
				cookieString.append(";");
			}
			cookieString.append(cookie).append("=").append(cookies.getProperty(cookie));
		}
		return cookieString.toString();
	}

	private boolean shouldInterrupt = false;

	public final void interrupt()
	{
		shouldInterrupt = true;
		if (connection != null)
		{
			synchronized (connection)
			{
				if (connection != null)
				{
					try
					{
						connection.disconnect();
					} catch (Exception ex)
					{
						System.out.println("INTERRUPT ERROR ON DISCONNECT: " + ex);
					}
				}

			}
		}
	}
}
