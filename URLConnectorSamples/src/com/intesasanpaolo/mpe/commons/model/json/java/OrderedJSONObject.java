package com.intesasanpaolo.mpe.commons.model.json.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.intesasanpaolo.mpe.commons.model.json.java.internal.Parser;

public class OrderedJSONObject extends JSONObject
{

	private static final long serialVersionUID = -3269263069889337299L;
	private ArrayList order = null;

	public OrderedJSONObject()
	{
		this.order = new ArrayList();
	}

	public static JSONObject parse(Reader paramReader) throws IOException
	{
		paramReader = new BufferedReader(paramReader);
		return new Parser(paramReader).parse(true);
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
		if (!containsKey(paramObject1))
		{
			this.order.add(paramObject1);
		}
		return super.put(paramObject1, paramObject2);
	}

	public Object remove(Object paramObject)
	{
		Object localObject1 = null;
		if (null == paramObject)
		{
			throw new IllegalArgumentException("key must not be null");
		}
		if (containsKey(paramObject))
		{
			localObject1 = super.remove(paramObject);
			for (int i = 0; i < this.order.size(); i++)
			{
				Object localObject2 = this.order.get(i);
				if (localObject2.equals(paramObject))
				{
					this.order.remove(i);
					break;
				}
			}
		}
		return localObject1;
	}

	public void clear()
	{
		super.clear();
		this.order.clear();
	}

	public Object clone()
	{
		OrderedJSONObject localOrderedJSONObject = (OrderedJSONObject) super.clone();
		Iterator localIterator = localOrderedJSONObject.getOrder();
		ArrayList localArrayList = new ArrayList();
		while (localIterator.hasNext())
		{
			localArrayList.add(localIterator.next());
			localOrderedJSONObject.order = localArrayList;
		}
		return localOrderedJSONObject;
	}

	public Iterator getOrder()
	{
		return this.order.iterator();
	}
}
