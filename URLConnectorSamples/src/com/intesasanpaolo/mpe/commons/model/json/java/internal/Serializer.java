package com.intesasanpaolo.mpe.commons.model.json.java.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.intesasanpaolo.mpe.commons.model.json.java.JSONArray;
import com.intesasanpaolo.mpe.commons.model.json.java.JSONObject;
import com.intesasanpaolo.mpe.commons.model.json.java.OrderedJSONObject;

public class Serializer
{

	private Writer writer;

	public Serializer(Writer paramWriter)
	{
		this.writer = paramWriter;
	}

	public void flush() throws IOException
	{
		this.writer.flush();
	}

	public void close() throws IOException
	{
		this.writer.close();
	}

	public Serializer writeRawString(String paramString) throws IOException
	{
		this.writer.write(paramString);

		return this;
	}

	public Serializer writeNull() throws IOException
	{
		writeRawString("null");

		return this;
	}

	public Serializer writeNumber(Number paramNumber) throws IOException
	{
		if (null == paramNumber)
		{
			return writeNull();
		}
		if ((paramNumber instanceof Float))
		{
			if (((Float) paramNumber).isNaN())
			{
				return writeNull();
			}
			if ((1.0F / -1.0F) == paramNumber.floatValue())
			{
				return writeNull();
			}
			if ((1.0F / 1.0F) == paramNumber.floatValue())
			{
				return writeNull();
			}
		}
		if ((paramNumber instanceof Double))
		{
			if (((Double) paramNumber).isNaN())
			{
				return writeNull();
			}
			if ((-1.0D / 0.0D) == paramNumber.doubleValue())
			{
				return writeNull();
			}
			if ((1.0D / 0.0D) == paramNumber.doubleValue())
			{
				return writeNull();
			}
		}
		writeRawString(paramNumber.toString());

		return this;
	}

	public Serializer writeBoolean(Boolean paramBoolean) throws IOException
	{
		if (null == paramBoolean)
		{
			return writeNull();
		}
		writeRawString(paramBoolean.toString());

		return this;
	}

	private String rightAlignedZero(String paramString, int paramInt)
	{
		if (paramInt == paramString.length())
		{
			return paramString;
		}
		StringBuffer localStringBuffer = new StringBuffer(paramString);
		while (localStringBuffer.length() < paramInt)
		{
			localStringBuffer.insert(0, '0');
		}
		return localStringBuffer.toString();
	}

	public Serializer writeString(String paramString) throws IOException
	{
		if (null == paramString)
		{
			return writeNull();
		}
		this.writer.write(34);

		char[] arrayOfChar = paramString.toCharArray();
		for (int i = 0; i < arrayOfChar.length; i++)
		{
			int j = arrayOfChar[i];
			switch (j)
			{
			case 34:
				this.writer.write("\\\"");
				break;
			case 92:
				this.writer.write("\\\\");
				break;
			case 0:
				this.writer.write("\\0");
				break;
			case 8:
				this.writer.write("\\b");
				break;
			case 9:
				this.writer.write("\\t");
				break;
			case 10:
				this.writer.write("\\n");
				break;
			case 12:
				this.writer.write("\\f");
				break;
			case 13:
				this.writer.write("\\r");
				break;
			case 47:
				this.writer.write("\\/");
				break;
			default:
				if ((j >= 32) && (j <= 126))
				{
					this.writer.write(j);
				} else
				{
					this.writer.write("\\u");
					this.writer.write(rightAlignedZero(Integer.toHexString(j), 4));
				}
				break;
			}
		}
		this.writer.write(34);

		return this;
	}

	private Serializer write(Object paramObject) throws IOException
	{
		if (null == paramObject)
		{
			return writeNull();
		}
		if ((paramObject instanceof Number))
		{
			return writeNumber((Number) paramObject);
		}
		if ((paramObject instanceof String))
		{
			return writeString((String) paramObject);
		}
		if ((paramObject instanceof Boolean))
		{
			return writeBoolean((Boolean) paramObject);
		}
		if ((paramObject instanceof JSONObject))
		{
			return writeObject((JSONObject) paramObject);
		}
		if ((paramObject instanceof JSONArray))
		{
			return writeArray((JSONArray) paramObject);
		}
		throw new IOException("Attempting to serialize unserializable object: '" + paramObject + "'");
	}

	public Serializer writeObject(JSONObject paramJSONObject) throws IOException
	{
		if (null == paramJSONObject)
		{
			return writeNull();
		}
		writeRawString("{");
		indentPush();

		Iterator localIterator = null;
		Object localObject1;
		if ((paramJSONObject instanceof OrderedJSONObject))
		{
			localIterator = ((OrderedJSONObject) paramJSONObject).getOrder();
		} else
		{
			localObject1 = getPropertyNames(paramJSONObject);
			localIterator = ((List) localObject1).iterator();
		}
		while (localIterator.hasNext())
		{
			localObject1 = localIterator.next();
			if (!(localObject1 instanceof String))
			{
				throw new IOException("attempting to serialize object with an invalid property name: '" + localObject1 + "'");
			}
			Object localObject2 = paramJSONObject.get(localObject1);
			if (!JSONObject.isValidObject(localObject2))
			{
				throw new IOException("attempting to serialize object with an invalid property value: '" + localObject2 + "'");
			}
			newLine();
			indent();
			writeString((String) localObject1);
			writeRawString(":");
			space();
			write(localObject2);
			if (localIterator.hasNext())
			{
				writeRawString(",");
			}
		}
		indentPop();
		newLine();
		indent();
		writeRawString("}");

		return this;
	}

	public Serializer writeArray(JSONArray paramJSONArray) throws IOException
	{
		if (null == paramJSONArray)
		{
			return writeNull();
		}
		writeRawString("[");
		indentPush();
		for (Iterator localIterator = paramJSONArray.iterator(); localIterator.hasNext();)
		{
			Object localObject = localIterator.next();
			if (!JSONObject.isValidObject(localObject))
			{
				throw new IOException("attempting to serialize array with an invalid element: '" + paramJSONArray + "'");
			}
			newLine();
			indent();
			write(localObject);
			if (localIterator.hasNext())
			{
				writeRawString(",");
			}
		}
		indentPop();
		newLine();
		indent();
		writeRawString("]");

		return this;
	}

	public void space() throws IOException
	{
	}

	public void newLine() throws IOException
	{
	}

	public void indent() throws IOException
	{
	}

	public void indentPush()
	{
	}

	public void indentPop()
	{
	}

	public List getPropertyNames(Map paramMap)
	{
		return new ArrayList(paramMap.keySet());
	}
}
