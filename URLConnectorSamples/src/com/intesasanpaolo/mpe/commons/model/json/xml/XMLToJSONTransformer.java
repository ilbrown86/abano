package com.intesasanpaolo.mpe.commons.model.json.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.intesasanpaolo.mpe.commons.model.json.xml.internal.JSONSAXHandler;

public class XMLToJSONTransformer
{

	private static String className = "com.intesasanpaolo.mpe.commons.model.json.xml.transform.XMLToJSONTransformer";
	private static Logger logger = Logger.getLogger(className, null);

	public static void transform(InputStream paramInputStream, OutputStream paramOutputStream) throws SAXException, IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "transform(InputStream, OutputStream)");
		}
		transform(paramInputStream, paramOutputStream, false);
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "transform(InputStream, OutputStream)");
		}
	}

	public static void transform(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean) throws SAXException, IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "transform(InputStream, OutputStream)");
		}
		if (paramInputStream == null)
		{
			throw new NullPointerException("XMLStream cannot be null");
		}
		if (paramOutputStream == null)
		{
			throw new NullPointerException("JSONStream cannot be null");
		}
		if (logger.isLoggable(Level.FINEST))
		{
			logger.logp(Level.FINEST, className, "transform", "Fetching a SAX parser for use with JSONSAXHandler");
		}
		try
		{
			SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
			localSAXParserFactory.setNamespaceAware(true);
			SAXParser localSAXParser = localSAXParserFactory.newSAXParser();
			XMLReader localXMLReader = localSAXParser.getXMLReader();
			JSONSAXHandler localJSONSAXHandler = new JSONSAXHandler(paramOutputStream, paramBoolean);
			localXMLReader.setContentHandler(localJSONSAXHandler);
			localXMLReader.setErrorHandler(localJSONSAXHandler);
			InputSource localInputSource = new InputSource(new BufferedInputStream(paramInputStream));
			if (logger.isLoggable(Level.FINEST))
			{
				logger.logp(Level.FINEST, className, "transform", "Parsing the XML content to JSON");
			}
			localInputSource.setEncoding("UTF-8");
			localXMLReader.parse(localInputSource);
			localJSONSAXHandler.flushBuffer();
		} catch (ParserConfigurationException localParserConfigurationException)
		{
			throw new SAXException("Could not get a parser: " + localParserConfigurationException.toString());
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "transform(InputStream, OutputStream)");
		}
	}

	public static String transform(InputStream paramInputStream) throws SAXException, IOException
	{
		return transform(paramInputStream, false);
	}

	public static String transform(InputStream paramInputStream, boolean paramBoolean) throws SAXException, IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "transform(InputStream, boolean)");
		}
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		String str = null;
		try
		{
			transform(paramInputStream, localByteArrayOutputStream, paramBoolean);
			str = localByteArrayOutputStream.toString("UTF-8");
			localByteArrayOutputStream.close();
		} catch (UnsupportedEncodingException localUnsupportedEncodingException)
		{
			IOException localIOException = new IOException(localUnsupportedEncodingException.toString());
			localIOException.initCause(localUnsupportedEncodingException);
			throw localIOException;
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "transform(InputStream, boolean)");
		}
		return str;
	}

	public static String transform(File paramFile, boolean paramBoolean) throws SAXException, IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "transform(InputStream, boolean)");
		}
		FileInputStream localFileInputStream = new FileInputStream(paramFile);
		String str = null;

		str = transform(localFileInputStream, paramBoolean);
		localFileInputStream.close();
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "transform(InputStream, boolean)");
		}
		return str;
	}

	public static String transform(File paramFile) throws SAXException, IOException
	{
		return transform(paramFile, false);
	}
}
