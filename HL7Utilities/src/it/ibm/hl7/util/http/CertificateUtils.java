package it.ibm.hl7.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class CertificateUtils
{

	/**
	 * 
	 * @author it056961
	 * 
	 * @see https://docs.oracle.com/javase/6/docs/technotes/guides/security/StandardNames.html#SSLContext
	 * @see https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#SSLContext
	 */
	public static enum SSLContextProtocol
	{
		/*from java 6->*/SSL("SSL"), SSLv2("SSLv2"), SSLv3("SSLv3"), TLS("SSL"), TLSv1("TLSv1"), TLSv1_1("TLSv1.1"), /*from Java 7->*/TLSv1_2(
				"TLSv1.2");

		private String name;

		private SSLContextProtocol(String name)
		{
			this.name = name;
		}


		public String getName()
		{
			return name;
		}
	}

	protected KeyStore getKeystore(URL ksURL, String ksPasswd) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException
	{
		InputStream is = ksURL.openStream();
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(is, ksPasswd != null ? ksPasswd.toCharArray() : new char[0]);
		return ks;
	}

	protected TrustManagerFactory getTrustManagerFactory(KeyStore ks) throws NoSuchAlgorithmException, KeyStoreException
	{
		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		if (ks != null)
		{
			factory.init(ks);
		}

		return factory;
	}

	protected SSLContext getSSLContext(TrustManager[] trustManagers, SSLContextProtocol sslContextProtocol) throws NoSuchAlgorithmException,
			KeyManagementException
	{
		SSLContext sslc = SSLContext.getInstance(sslContextProtocol.getName());

		sslc.init(null, trustManagers, new SecureRandom());

		return sslc;

	}

	/**
	 * 
	 * @param sslContextProtocol: "SSL", "TLS"
	 * @return {@link SSLSocketFactory}
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public SSLSocketFactory getSSLSocketFactory(SSLContextProtocol sslContextProtocol) throws KeyManagementException, NoSuchAlgorithmException
	{
		return getSSLContext(new TrustManager[]
		{ new X509TrustManager()
		{

			public X509Certificate[] getAcceptedIssuers()
			{
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
			}
		} }, sslContextProtocol).getSocketFactory();

	}

	/**
	 * 
	 * @param sslContextProtocol: "SSL", "TLS"
	 * @param ksURL
	 * @param ksPasswd
	 * @return {@link SSLSocketFactory}
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public SSLSocketFactory getSSLSocketFactory(SSLContextProtocol sslContextProtocol, URL ksURL, String ksPasswd) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException
	{
		return getSSLContext(getTrustManagerFactory(getKeystore(ksURL, ksPasswd)).getTrustManagers(), sslContextProtocol).getSocketFactory();
	}



}
