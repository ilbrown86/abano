package it.ibm.hl7.util.json;

import it.ibm.hl7.util.json.internal.Serializer;
import it.ibm.hl7.util.json.internal.SerializerVerbose;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class JSONArray extends ArrayList implements JSONArtifact
{

	private static final long serialVersionUID = 9076798781015779954L;

	public JSONArray()
	{
	}

	public JSONArray(int paramInt)
	{
		super(paramInt);
	}

	public void add(int paramInt, Object paramObject)
	{
		checkElement(paramObject);
		super.add(paramInt, paramObject);
	}

	public boolean add(Object paramObject)
	{
		checkElement(paramObject);
		return super.add(paramObject);
	}

	public boolean addAll(Collection paramCollection)
	{
		checkElements(paramCollection);
		return super.addAll(paramCollection);
	}

	public boolean addAll(int paramInt, Collection paramCollection)
	{
		checkElements(paramCollection);
		return super.addAll(paramInt, paramCollection);
	}

	public Object set(int paramInt, Object paramObject)
	{
		checkElement(paramObject);
		return super.set(paramInt, paramObject);
	}

	public static JSONArray parse(InputStream paramInputStream) throws IOException
	{
		InputStreamReader localInputStreamReader = null;
		try
		{
			localInputStreamReader = new InputStreamReader(paramInputStream, "UTF-8");
		} catch (Exception localException)
		{
			localInputStreamReader = new InputStreamReader(paramInputStream);
		}
		return parse(localInputStreamReader);
	}

	public static JSONArray parse(Reader paramReader) throws IOException
	{
		StringBuffer localStringBuffer = new StringBuffer("");
		localStringBuffer.append("{\"jsonArray\":");

		char[] arrayOfChar = new char[8196];
		int i = 0;
		i = paramReader.read(arrayOfChar, 0, 8196);
		while (i != -1)
		{
			localStringBuffer.append(arrayOfChar, 0, i);
			i = paramReader.read(arrayOfChar, 0, 8196);
		}
		localStringBuffer.append("}");




		JSONObject localJSONObject = JSONObject.parse(localStringBuffer.toString());
		return (JSONArray) localJSONObject.get("jsonArray");
	}

	public static JSONArray parse(String paramString) throws IOException
	{
		StringReader localStringReader = new StringReader(paramString);
		return parse(localStringReader);
	}

	public void serialize(OutputStream paramOutputStream) throws IOException
	{
		serialize(paramOutputStream, false);
	}

	public void serialize(OutputStream paramOutputStream, boolean paramBoolean) throws IOException
	{
		BufferedWriter localBufferedWriter = null;
		try
		{
			localBufferedWriter = new BufferedWriter(new OutputStreamWriter(paramOutputStream, "UTF-8"));
		} catch (UnsupportedEncodingException localUnsupportedEncodingException)
		{
			IOException localIOException = new IOException(localUnsupportedEncodingException.toString());
			localIOException.initCause(localUnsupportedEncodingException);
			throw localIOException;
		}
		serialize(localBufferedWriter, paramBoolean);
	}

	public void serialize(Writer paramWriter) throws IOException
	{
		paramWriter = new BufferedWriter(paramWriter);
		serialize(paramWriter, false);
	}

	public void serialize(Writer paramWriter, boolean paramBoolean) throws IOException
	{
		Object localObject;
		if (paramBoolean)
		{
			localObject = new SerializerVerbose(paramWriter);
		} else
		{
			localObject = new Serializer(paramWriter);
		}
		((Serializer) localObject).writeArray(this).flush();
	}

	public String serialize(boolean paramBoolean) throws IOException
	{
		StringWriter localStringWriter = new StringWriter();
		Object localObject;
		if (paramBoolean)
		{
			localObject = new SerializerVerbose(localStringWriter);
		} else
		{
			localObject = new Serializer(localStringWriter);
		}
		((Serializer) localObject).writeArray(this).flush();

		return localStringWriter.toString();
	}

	public String serialize() throws IOException
	{
		return serialize(false);
	}

	private void checkElement(Object paramObject)
	{
		if (!JSONObject.isValidObject(paramObject))
		{
			throw new IllegalArgumentException("invalid type of element");
		}
	}

	private void checkElements(Collection paramCollection)
	{
		for (Iterator localIterator = paramCollection.iterator(); localIterator.hasNext();)
		{
			if (!JSONObject.isValidObject(localIterator.next()))
			{
				throw new IllegalArgumentException("invalid type of element");
			}
		}
	}
}
