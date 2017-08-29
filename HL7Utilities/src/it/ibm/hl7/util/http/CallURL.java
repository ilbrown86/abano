package it.ibm.hl7.util.http;


import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;


public class CallURL
{

	public int call(String urlString, String cookies, String body, String connectMethod, int redirect, Properties setCookies,
			ByteArrayOutputStream baos) throws Throwable
	{

		Properties requestHeaders = new Properties();
		Properties cookieProps = new Properties();

		HostnameVerifier verifier = null;
		SSLSocketFactory factory = null;
		verifier = new HostnameVerifier()
		{

			public boolean verify(String arg0, SSLSession arg1)
			{
				return true;
			}
		};
		/*try
		{
			URL keystoreURL = getClass().getResource("solr.desktop.keystore");
			factory = new CertificateUtils().getSSLSocketFactory(SSLContextProtocol.TLS, keystoreURL, "passw0rd");
		} catch (Exception e)
		{
			System.out.println("error creating SSLSocketFactory");
			throw e;
		}*/

		requestHeaders.setProperty("User-Agent", "IBM Integration Bus");
		requestHeaders.setProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

		if (cookies != null)
		{
			requestHeaders.setProperty("Cookie", cookies);
		}

		if (baos == null)
		{
			baos = new ByteArrayOutputStream();
		}

		try
		{

			return new URLConnector().getResponse(urlString, requestHeaders, cookieProps, body, connectMethod, null, baos, redirect, verifier,
					factory);
		} finally
		{
			if (setCookies != null)
			{
				setCookies.putAll(cookieProps);
			}
		}

	}

}
