package it.ibm.sample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import com.intesasanpaolo.mpe.commons.http.URLConnector;

public class HL7Sender
{

	public static void main(String[] args) throws Throwable
	{
		String LS = "\r";
		HL7Sender wcm = new HL7Sender();
		String baseUrl = args[0];
		String url;
		int code;
		Properties setCookies;
		ByteArrayOutputStream baos = null;
		String cookies;
		Properties allCookies = new Properties();

		//@formatter:off
		String writeParameters = 
				 "MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170308153035||ORU^R01|25233631|P|2.5.1"+LS
				//+"EVN||20170308153035||||20170308153035"+LS
				+"PID|||131175600||TEST^CORSO MEDARCHIVER||19710101|F|||via maggini 200^^^^30100^^^027042|||||||TSTCSM71A41H360M"+LS
				+"PV1||I|||||||||||||||||17/000004|||||||||||||||||||||||||20170308153035"+LS
				+"OBR|1||12345|IBMAPPLE|||20170308153035|||||||||||||||||||||||||||CPORTELLI"+LS
				+"OBX|1|NM|SC001^pharmacologicallySedated^SRT||1||||||F"+LS
				+"OBX|2|NM|SC002^gcsEye^SRT||1^Nessuna||||||F"+LS
				+"OBX|3|NM|SC004^gcsVerbal^SRT||1^Nessun suono emesso||||||F"+LS
				+"OBX|4|NM|SC006^gcsMotor^SRT||1^Nessuna risposta||||||F"+LS
				;

		String readParameters = 
				 "MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LS
				+"QRD|20170309130000|R|I|12345|||||131175600|RES|17/000004" + LS
				//+"QRF|CdC||||ALL" + LS
				;
		//@formatter:on

		String body = writeParameters;
		//String body = readParameters;
		baos = new ByteArrayOutputStream();
		//url = baseUrl + "/sampleBuffer";
		//url = baseUrl + "/test";
		//url = baseUrl + "/prelens";
		url = baseUrl + "/sampleCall";
		code = wcm.call(url, null, body, "POST", 5, setCookies = new Properties(), baos);
		allCookies.putAll(setCookies);
		cookies = wcm.getCookie(allCookies);
		System.out.println(code + " for " + url);
		System.out.println(cookies);
		System.out.println(new String(baos.toByteArray()));

	}

	public int call(String urlString, String cookies, String body, String connectMethod, int redirect, Properties setCookies,
			ByteArrayOutputStream baos) throws Throwable
	{

		Properties requestHeaders = new Properties();
		Properties cookieProps = new Properties();

		HostnameVerifier verifier = null;
		SSLSocketFactory factory = null;
		URL keystoreURL = getClass().getResource("solr.desktop.keystore");
		verifier = new HostnameVerifier()
		{

			public boolean verify(String arg0, SSLSession arg1)
			{
				return true;
			}
		};
		/*try
		{
			factory = new CertificateUtils().getSSLSocketFactory(SSLContextProtocol.TLS, keystoreURL, "passw0rd");
		} catch (Exception e)
		{
			System.out.println("error creating SSLSocketFactory");
			throw e;
		}*/

		requestHeaders.setProperty("User-Agent", "RCP/2.0");
		requestHeaders.setProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

		// requestHeaders.setProperty("Pragma", "no-cache");
		// requestHeaders.setProperty("Cache-Control",
		// "no-cache");
		// requestHeaders.setProperty("Host","cosfapb103.hbl.local");
		// requestHeaders.setProperty("Host","cosfapb104.hbl.local");
		if (cookies != null)
		{
			requestHeaders.setProperty("Cookie", cookies);
		}

		if (baos == null)
		{
			baos = new ByteArrayOutputStream();
		}
		Map responseHeaders = new HashMap();

		try
		{

			return new URLConnector().getResponse(urlString, requestHeaders, cookieProps, body, connectMethod, responseHeaders, baos, redirect,
					verifier, factory);
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
