package it.ibm.sample;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import com.intesasanpaolo.mpe.commons.http.URLConnector;
import com.intesasanpaolo.mpe.commons.model.json.java.JSONObject;

public class RestConnector extends HL7Sender
{

	public static void main(String[] args) throws Throwable
	{
		RestConnector wcm = new RestConnector();
		//String baseUrl = "http://it56961b:10080";
		//String baseUrl = "https://10.20.251.2";
		String baseUrl = "http://win7pro:7800";
		//		String baseUrl = "http://it1dbasw1sviln";
		//String baseUrl = "https://9.71.75.115";
		//String baseUrl = "https://192.168.1.17";
		String url;
		int code;
		Properties setCookies;
		ByteArrayOutputStream baos = null;
		String cookies;
		Properties allCookies = new Properties();

		Properties requestHeaders;
		String body;

		requestHeaders = new Properties();

		//body = "{\"logonId\" : \"cristianom@it.ibm.com\",\"logonPassword\" : \"zaq12wsx\"}";
		baos = new ByteArrayOutputStream();
		url = baseUrl + "/ehr/patients/123456789/details";
		code = wcm.call(url, requestHeaders, null, null, "GET", 1,
				setCookies = new Properties(), baos);
		allCookies.putAll(setCookies);
		cookies = wcm.getCookie(allCookies);
		System.out.println(code + " for " + url);
		System.out.println("cookies=" + cookies);
		String response = new String(baos.toByteArray());
		System.out.println("body=" + response);

		JSONObject obj = JSONObject.parse(response);
	}

	public int call(String urlString, Properties requestHeaders,
			String cookies, String body, String connectMethod, int redirect,
			Properties setCookies, ByteArrayOutputStream baos) throws Throwable
	{

		if (requestHeaders == null)
		{
			requestHeaders = new Properties();
		}
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
			factory = new CertificateUtils().getSSLSocketFactory(SSLContextProtocol.TLS, keystoreURL,
					"passw0rd");
		} catch (Exception e)
		{
			System.out.println("error creating SSLSocketFactory");
			throw e;
		}*/

		//requestHeaders.setProperty("User-Agent", "RCP/2.0");
		requestHeaders.setProperty("Content-type",
		//"application/x-www-form-urlencoded; charset=UTF-8");
				"application/json");

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

			int code = new URLConnector().getResponse(urlString,
					requestHeaders, cookieProps, body, connectMethod,
					responseHeaders, baos, redirect, verifier, factory);
			System.out.println("responseHeaders=" + responseHeaders);
			return code;
		} finally
		{
			if (setCookies != null)
			{
				setCookies.putAll(cookieProps);
			}
		}

	}
}
