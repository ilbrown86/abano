package com.intesasanpaolo.mpe.commons.model.json.xml.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JSONSAXHandler extends DefaultHandler
{

	private static String className = "com.intesasanpaolo.mpe.commons.model.json.xml.transform.impl.JSONSAXHandler";
	private static Logger logger = Logger.getLogger(className, null);
	private OutputStreamWriter osWriter = null;
	private JSONObject current = null;
	private Stack previousObjects = new Stack();
	private JSONObject head = null;
	private boolean compact = false;

	public JSONSAXHandler(OutputStream paramOutputStream) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "JSONHander(OutputStream) <constructor>");
		}
		this.osWriter = new OutputStreamWriter(paramOutputStream, "UTF-8");
		this.compact = true;
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "JSONHander(OutputStream) <constructor>");
		}
	}

	public JSONSAXHandler(OutputStream paramOutputStream, boolean paramBoolean) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "JSONHander(OutputStream, boolean) <constructor>");
		}
		this.osWriter = new OutputStreamWriter(paramOutputStream, "UTF-8");
		this.compact = (!paramBoolean);
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "JSONHander(OutputStream, boolean) <constructor>");
		}
	}

	public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "startElement(String,String,String,org.xml.sax.Attributes)");
		}
		Properties localProperties = new Properties();
		int i = paramAttributes.getLength();
		for (int j = 0; j < i; j++)
		{
			localProperties.put(paramAttributes.getQName(j), paramAttributes.getValue(j));
		}
		JSONObject localJSONObject = new JSONObject(paramString2, localProperties);
		if (this.head == null)
		{
			this.head = localJSONObject;
			this.current = this.head;
		} else
		{
			if (this.current != null)
			{
				this.previousObjects.push(this.current);
				this.current.addJSONObject(localJSONObject);
			}
			this.current = localJSONObject;
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "startElement(String,String,String,org.xml.sax.Attributes)");
		}
	}

	public void endElement(String paramString1, String paramString2, String paramString3) throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "endElement(String,String,String)");
		}
		if (!this.previousObjects.isEmpty())
		{
			this.current = ((JSONObject) this.previousObjects.pop());
		} else
		{
			this.current = null;
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "endElement(String,String,String)");
		}
	}

	public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "characters(char[], int, int)");
		}
		String str = new String(paramArrayOfChar, paramInt1, paramInt2);
		str = str.trim();
		this.current.setTagText(str);
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "characters(char[], int, int)");
		}
	}

	public void startDocument() throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "startDocument()");
		}
		startJSON();
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "startDocument()");
		}
	}

	public void endDocument() throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "endDocument()");
		}
		endJSON();
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "endDocument()");
		}
	}

	public void flushBuffer() throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "flushBuffer()");
		}
		if (this.osWriter != null)
		{
			this.osWriter.flush();
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "flushBuffer()");
		}
	}

	private void startJSON() throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "startJSON()");
		}
		this.head = new JSONObject("", null);
		this.current = this.head;
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "startJSON()");
		}
	}

	private void endJSON() throws SAXException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "endJSON()");
		}
		try
		{
			this.head.writeObject(this.osWriter, 0, true, this.compact);
			this.head = null;
			this.current = null;
			this.previousObjects.clear();
		} catch (Exception localException)
		{
			SAXException localSAXException = new SAXException(localException);
			localSAXException.initCause(localException);
			throw localSAXException;
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "endJSON()");
		}
	}
}
