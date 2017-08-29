package com.intesasanpaolo.mpe.commons.model.json.java;

import java.io.BufferedReader;
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
import java.util.HashMap;

import com.intesasanpaolo.mpe.commons.model.json.java.internal.Parser;
import com.intesasanpaolo.mpe.commons.model.json.java.internal.Serializer;
import com.intesasanpaolo.mpe.commons.model.json.java.internal.SerializerVerbose;

public class JSONObject extends HashMap implements JSONArtifact
{

	private static final long serialVersionUID = -3269263069889337298L;

	public static boolean isValidObject(Object paramObject)
	{
		if (null == paramObject)
		{
			return true;
		}
		return isValidType(paramObject.getClass());
	}

	public static boolean isValidType(Class paramClass)
	{
		if (null == paramClass)
		{
			throw new IllegalArgumentException();
		}
		if (String.class == paramClass)
		{
			return true;
		}
		if (Boolean.class == paramClass)
		{
			return true;
		}
		if (JSONObject.class.isAssignableFrom(paramClass))
		{
			return true;
		}
		if (JSONArray.class == paramClass)
		{
			return true;
		}
		if (Number.class.isAssignableFrom(paramClass))
		{
			return true;
		}
		return false;
	}

	public static JSONObject parse(Reader paramReader) throws IOException
	{
		paramReader = new BufferedReader(paramReader);
		return new Parser(paramReader).parse();
	}

	public static JSONObject parse(String paramString) throws IOException
	{
		StringReader localStringReader = new StringReader(paramString);
		return parse(localStringReader);
	}

	public static JSONObject parse(InputStream paramInputStream) throws IOException
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
		((Serializer) localObject).writeObject(this).flush();
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
		((Serializer) localObject).writeObject(this).flush();

		return localStringWriter.toString();
	}

	public String serialize() throws IOException
	{
		return serialize(false);
	}

	public Object put(Object paramObject1, Object paramObject2)
	{
		if (null == paramObject1)
		{
			throw new IllegalArgumentException("key must not be null");
		}
		if (!(paramObject1 instanceof String))
		{
			throw new IllegalArgumentException("key must be a String");
		}
		if (!isValidObject(paramObject2))
		{
			if (paramObject2 != null)
			{
				throw new IllegalArgumentException("Invalid type of value.  Type: [" + paramObject2.getClass().getName() + "] with value: ["
						+ paramObject2.toString() + "]");
			}
			throw new IllegalArgumentException("Invalid type of value.");
		}
		return super.put(paramObject1, paramObject2);
	}
}
