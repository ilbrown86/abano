package it.ibm.sample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import com.intesasanpaolo.mpe.commons.http.CertificateUtils;
import com.intesasanpaolo.mpe.commons.http.CertificateUtils.SSLContextProtocol;
import com.intesasanpaolo.mpe.commons.http.URLConnector;

public class LoanRestConnectorSample
{

	public static void main(String[] args) throws Throwable
	{
		LoanRestConnectorSample wcm = new LoanRestConnectorSample();
		//String baseUrl = "http://192.168.120.128:9080";
		//String baseUrl = "https://192.168.120.128:9443";
		//String baseUrl = "http://serviziBE-tnbpl0-WSRRR.syssede.systest.sanpaoloimi.com:9080";
		String baseUrl = "http://win7pro:7800";
		String url;
		int code;
		Properties setCookies;
		ByteArrayOutputStream baos = null;

		baos = new ByteArrayOutputStream();
		url = baseUrl
				//+ "/LoanServicesREST/v1/loans/3";
				+ "/callREST";
		System.out.println("calling " + url);
		code = wcm.call(url, null, null, "GET", 5, setCookies = new Properties(), baos);
		System.out.println(code + " for " + url);
		byte[] response = baos.toByteArray();
		System.out.println(new String(response));

	}

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
		try
		{
			factory = new CertificateUtils().getSSLSocketFactory(SSLContextProtocol.TLS);
		} catch (Exception e)
		{
			System.out.println("error creating SSLSocketFactory");
			throw e;
		}

		if (cookies != null)
		{
			requestHeaders.setProperty("Cookie", cookies);
			requestHeaders.setProperty("Content-Type", "application/json; charset=UTF-8");
		}

		if (baos == null)
		{
			baos = new ByteArrayOutputStream();
		}
		Map responseHeaders = new HashMap();

		try
		{

			return new URLConnector().getResponse(urlString, requestHeaders, cookieProps, body, connectMethod, responseHeaders, baos, redirect,
					verifier, factory, "Chary", "Chary");
		} finally
		{
			if (setCookies != null)
			{
				setCookies.putAll(cookieProps);
			}
		}

	}

	public String getCookie(Properties cookieProps)
	{
		String cookieValue = new String();
		Iterator iter = cookieProps.keySet().iterator();
		while (iter.hasNext())
		{
			if (cookieValue.length() > 0)
				cookieValue += "; ";
			String key = iter.next().toString();
			cookieValue += key + "=" + cookieProps.getProperty(key);

		}
		return cookieValue;
	}

	public String extractLongDescription(ByteArrayOutputStream baos)
	{
		String html = new String(baos.toByteArray());
		String longDesc = "<textarea>";
		String longDescEnd = "</textarea>";

		int indof = html.indexOf(longDesc);

		int indofEnd = html.indexOf(longDescEnd);
		if (indof > -1 && indofEnd > -1)
		{
			return html.substring(indof, indofEnd + longDescEnd.length());

		} else
		{
			return longDesc + " not found: check it!";
		}
	}

	public String extractFromBody(ByteArrayOutputStream baos, String startMatch, String endMatch) throws IOException
	{
		String responseString = new String(baos.toByteArray());

		int indof, indofEnd, startMatchLength;
		if (startMatch == null)
		{
			indof = 0;
			startMatchLength = 0;
		} else
		{
			indof = responseString.indexOf(startMatch);
			startMatchLength = startMatch.length();
		}

		if (endMatch == null)
		{
			indofEnd = responseString.length();
		} else
		{
			indofEnd = responseString.indexOf(endMatch, indof + startMatchLength);
		}

		if (indof > -1 && indofEnd > -1)
		{

			return responseString.substring(indof + startMatchLength, indofEnd).trim();
		} else
		{
			throw new IOException("matches [" + startMatch + ":" + indof + "," + endMatch + ":" + indofEnd + "] not found in " + responseString);
		}
	}

}
