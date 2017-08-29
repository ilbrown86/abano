package com.intesasanpaolo.mpe.commons.model.json.xml.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONObject
{

	private static String className = "com.intesasanpaolo.mpe.commons.model.json.xml.transform.impl.JSONObject";
	private static Logger logger = Logger.getLogger(className, null);
	private static final String indent = "   ";
	private String objectName = null;
	private Properties attrs = null;
	private Hashtable jsonObjects = null;
	private String tagText = null;

	public JSONObject(String paramString, Properties paramProperties)
	{
		this.objectName = paramString;
		this.attrs = paramProperties;
		this.jsonObjects = new Hashtable();
	}

	public void addJSONObject(JSONObject paramJSONObject)
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "addJSONObject(JSONObject)");
		}
		Vector localVector = (Vector) this.jsonObjects.get(paramJSONObject.objectName);
		if (localVector != null)
		{
			localVector.add(paramJSONObject);
		} else
		{
			localVector = new Vector();
			localVector.add(paramJSONObject);
			this.jsonObjects.put(paramJSONObject.objectName, localVector);
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "addJSONObject(JSONObject)");
		}
	}

	public void setTagText(String paramString)
	{
		this.tagText = paramString;
	}

	public void writeObject(Writer paramWriter, int paramInt, boolean paramBoolean) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeObject(Writer, int, boolean)");
		}
		writeObject(paramWriter, paramInt, paramBoolean, false);
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeObject(Writer, int, boolean)");
		}
	}

	public void writeObject(Writer paramWriter, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeObject(Writer, int, boolean, boolean)");
		}
		if (paramWriter != null)
		{
			try
			{
				if (isEmptyObject())
				{
					writeEmptyObject(paramWriter, paramInt, paramBoolean1, paramBoolean2);
				} else if (isTextOnlyObject())
				{
					writeTextOnlyObject(paramWriter, paramInt, paramBoolean1, paramBoolean2);
				} else
				{
					writeComplexObject(paramWriter, paramInt, paramBoolean1, paramBoolean2);
				}
			} catch (Exception localException)
			{
				IOException localIOException = new IOException("Error occurred on serialization of JSON text.");
				localIOException.initCause(localException);
				throw localIOException;
			}
		} else
		{
			throw new IOException("The writer cannot be null.");
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeObject(Writer, int, boolean, boolean)");
		}
	}

	private void writeAttribute(Writer paramWriter, String paramString1, String paramString2, int paramInt, boolean paramBoolean) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeAttribute(Writer, String, String, int)");
		}
		if (!paramBoolean)
		{
			writeIndention(paramWriter, paramInt);
		}
		try
		{
			if (!paramBoolean)
			{
				paramWriter.write("\"" + paramString1 + "\"" + " : " + "\"" + escapeStringSpecialCharacters(paramString2) + "\"");
			} else
			{
				paramWriter.write("\"" + paramString1 + "\"" + ":" + "\"" + escapeStringSpecialCharacters(paramString2) + "\"");
			}
		} catch (Exception localException)
		{
			IOException localIOException = new IOException("Error occurred on serialization of JSON text.");
			localIOException.initCause(localException);
			throw localIOException;
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeAttribute(Writer, String, String, int)");
		}
	}

	private void writeIndention(Writer paramWriter, int paramInt) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeIndention(Writer, int)");
		}
		try
		{
			for (int i = 0; i < paramInt; i++)
			{
				paramWriter.write("   ");
			}
		} catch (Exception localException)
		{
			IOException localIOException = new IOException("Error occurred on serialization of JSON text.");
			localIOException.initCause(localException);
			throw localIOException;
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeIndention(Writer, int)");
		}
	}

	private void writeAttributes(Writer paramWriter, Properties paramProperties, int paramInt, boolean paramBoolean) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeAttributes(Writer, Properties, int, boolean)");
		}
		if (paramProperties != null)
		{
			Enumeration localEnumeration = paramProperties.propertyNames();
			if ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
			{
				while (localEnumeration.hasMoreElements())
				{
					String str = (String) localEnumeration.nextElement();
					writeAttribute(paramWriter, escapeAttributeNameSpecialCharacters(str), (String) paramProperties.get(str), paramInt + 1,
							paramBoolean);
					if (localEnumeration.hasMoreElements())
					{
						try
						{
							if (!paramBoolean)
							{
								paramWriter.write(",\n");
							} else
							{
								paramWriter.write(",");
							}
						} catch (Exception localException)
						{
							IOException localIOException = new IOException("Error occurred on serialization of JSON text.");
							localIOException.initCause(localException);
							throw localIOException;
						}
					}
				}
			}
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeAttributes(Writer, Properties, int, boolean)");
		}
	}

	private String escapeAttributeNameSpecialCharacters(String paramString)
	{
		if (paramString != null)
		{
			StringBuffer localStringBuffer = new StringBuffer("");
			for (int i = 0; i < paramString.length(); i++)
			{
				char c = paramString.charAt(i);
				switch (c)
				{
				case ':':
					localStringBuffer.append("_ns-sep_");
					break;
				default:
					localStringBuffer.append(c);
				}
			}
			paramString = localStringBuffer.toString();
		}
		return paramString;
	}

	private String escapeStringSpecialCharacters(String paramString)
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "escapeStringSpecialCharacters(String)");
		}
		if (paramString != null)
		{
			StringBuffer localStringBuffer1 = new StringBuffer("");
			for (int i = 0; i < paramString.length(); i++)
			{
				char c = paramString.charAt(i);
				switch (c)
				{
				case '"':
					localStringBuffer1.append("\\\"");
					break;
				case '\t':
					localStringBuffer1.append("\\t");
					break;
				case '\b':
					localStringBuffer1.append("\\b");
					break;
				case '\\':
					localStringBuffer1.append("\\\\");
					break;
				case '\f':
					localStringBuffer1.append("\\f");
					break;
				case '\r':
					localStringBuffer1.append("\\r");
					break;
				case '/':
					localStringBuffer1.append("\\/");
					break;
				default:
					if ((c >= ' ') && (c <= '~'))
					{
						localStringBuffer1.append(c);
					} else
					{
						localStringBuffer1.append("\\u");
						String str = Integer.toHexString(c);
						StringBuffer localStringBuffer2 = new StringBuffer(str);
						while (str.length() < 4)
						{
							localStringBuffer2.insert(0, '0');
						}
						localStringBuffer1.append(str.toString());
					}
					break;
				}
			}
			paramString = localStringBuffer1.toString();
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "escapeStringSpecialCharacters(String)");
		}
		return paramString;
	}

	private void writeChildren(Writer paramWriter, int paramInt, boolean paramBoolean) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeChildren(Writer, int, boolean)");
		}
		if (!this.jsonObjects.isEmpty())
		{
			Enumeration localEnumeration = this.jsonObjects.keys();
			while (localEnumeration.hasMoreElements())
			{
				String str = (String) localEnumeration.nextElement();
				Vector localVector = (Vector) this.jsonObjects.get(str);
				if ((localVector != null) && (!localVector.isEmpty()))
				{
					if (localVector.size() == 1)
					{
						if (logger.isLoggable(Level.FINEST))
						{
							logger.logp(Level.FINEST, className, "writeChildren(Writer, int, boolean)", "Writing child object: [" + str + "]");
						}
						JSONObject localJSONObject = (JSONObject) localVector.elementAt(0);
						localJSONObject.writeObject(paramWriter, paramInt + 1, false, paramBoolean);
						if (localEnumeration.hasMoreElements())
						{
							try
							{
								if (!paramBoolean)
								{
									if ((!localJSONObject.isTextOnlyObject()) && (!localJSONObject.isEmptyObject()))
									{
										writeIndention(paramWriter, paramInt + 1);
									}
									paramWriter.write(",\n");
								} else
								{
									paramWriter.write(",");
								}
							} catch (Exception localException2)
							{
								IOException localIOException = new IOException("Error occurred on serialization of JSON text.");
								localIOException.initCause(localException2);
								throw localIOException;
							}
						} else if ((localJSONObject.isTextOnlyObject()) && (!paramBoolean))
						{
							paramWriter.write("\n");
						}
					} else
					{
						if (logger.isLoggable(Level.FINEST))
						{
							logger.logp(Level.FINEST, className, "writeChildren(Writer, int, boolean)",
									"Writing array of JSON objects with attribute name: [" + str + "]");
						}
						try
						{
							if (!paramBoolean)
							{
								writeIndention(paramWriter, paramInt + 1);
								paramWriter.write("\"" + str + "\"");
								paramWriter.write(" : [\n");
							} else
							{
								paramWriter.write("\"" + str + "\"");
								paramWriter.write(":[");
							}
							for (int i = 0; i < localVector.size(); i++)
							{
								JSONObject localObject = (JSONObject) localVector.elementAt(i);//localObject cannot be resolved to a variable	JSONObject.java	/URLConnectorSamples/src/com/ibm/json/xml/internal	line 335	Java Problem
								((JSONObject) localObject).writeObject(paramWriter, paramInt + 2, true, paramBoolean);
								if (i != localVector.size() - 1)
								{
									if (!paramBoolean)
									{
										if ((!((JSONObject) localObject).isTextOnlyObject()) && (!((JSONObject) localObject).isEmptyObject()))
										{
											writeIndention(paramWriter, paramInt + 2);
										}
										paramWriter.write(",\n");
									} else
									{
										paramWriter.write(",");
									}
								}
							}
							if (!paramBoolean)
							{
								paramWriter.write("\n");
								writeIndention(paramWriter, paramInt + 1);
							}
							paramWriter.write("]");
							if (localEnumeration.hasMoreElements())
							{
								paramWriter.write(",");
							}
							if (!paramBoolean)
							{
								paramWriter.write("\n");
							}
						} catch (Exception localException1)
						{
							Object localObject = new IOException("Error occurred on serialization of JSON text.");
							((IOException) localObject).initCause(localException1);
							throw ((/*Throwable*/IOException) localObject);//Unhandled exception type Throwable	JSONObject.java	/URLConnectorSamples/src/com/ibm/json/xml/internal	line 368	Java Problem
						}
					}
				}
			}
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeChildren(Writer, int, boolean)");
		}
	}

	private void writeEmptyObject(Writer paramWriter, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeEmptyObject(Writer, int, boolean, boolean)");
		}
		if (!paramBoolean1)
		{
			if (!paramBoolean2)
			{
				writeIndention(paramWriter, paramInt);
				paramWriter.write("\"" + this.objectName + "\"");
				paramWriter.write(" : true");
			} else
			{
				paramWriter.write("\"" + this.objectName + "\"");
				paramWriter.write(":true");
			}
		} else if (!paramBoolean2)
		{
			writeIndention(paramWriter, paramInt);
			paramWriter.write("true");
		} else
		{
			paramWriter.write("true");
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeEmptyObject(Writer, int, boolean, boolean)");
		}
	}

	private void writeTextOnlyObject(Writer paramWriter, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeTextOnlyObject(Writer, int, boolean, boolean)");
		}
		if (!paramBoolean1)
		{
			writeAttribute(paramWriter, this.objectName, this.tagText, paramInt, paramBoolean2);
		} else if (!paramBoolean2)
		{
			writeIndention(paramWriter, paramInt);
			paramWriter.write("\"" + this.tagText + "\"");
		} else
		{
			paramWriter.write("\"" + this.tagText + "\"");
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeTextOnlyObject(Writer, int, boolean, boolean)");
		}
	}

	private void writeComplexObject(Writer paramWriter, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "writeComplexObject(Writer, int, boolean, boolean)");
		}
		int i = 0;
		if (!paramBoolean1)
		{
			if (logger.isLoggable(Level.FINEST))
			{
				logger.logp(Level.FINEST, className, "writeComplexObject(Writer, int, boolean, boolean)", "Writing object: [" + this.objectName + "]");
			}
			if (!paramBoolean2)
			{
				writeIndention(paramWriter, paramInt);
			}
			paramWriter.write("\"" + this.objectName + "\"");
			if (!paramBoolean2)
			{
				paramWriter.write(" : {\n");
			} else
			{
				paramWriter.write(":{");
			}
		} else
		{
			if (logger.isLoggable(Level.FINEST))
			{
				logger.logp(Level.FINEST, className, "writeObject(Writer, int, boolean, boolean)",
						"Writing object contents as an anonymous object (usually an array entry)");
			}
			if (!paramBoolean2)
			{
				writeIndention(paramWriter, paramInt);
				paramWriter.write("{\n");
			} else
			{
				paramWriter.write("{");
			}
		}
		if ((this.tagText != null) && (!this.tagText.equals("")))
		{
			writeAttribute(paramWriter, "content", this.tagText, paramInt + 1, paramBoolean2);
			i = 1;
		}
		if ((this.attrs != null) && (!this.attrs.isEmpty()) && (i != 0))
		{
			if (!paramBoolean2)
			{
				paramWriter.write(",\n");
			} else
			{
				paramWriter.write(",");
			}
		}
		writeAttributes(paramWriter, this.attrs, paramInt, paramBoolean2);
		if (!this.jsonObjects.isEmpty())
		{
			if ((this.attrs != null) && ((!this.attrs.isEmpty()) || (i != 0)))
			{
				if (!paramBoolean2)
				{
					paramWriter.write(",\n");
				} else
				{
					paramWriter.write(",");
				}
			} else if (!paramBoolean2)
			{
				paramWriter.write("\n");
			}
			writeChildren(paramWriter, paramInt, paramBoolean2);
		} else if (!paramBoolean2)
		{
			paramWriter.write("\n");
		}
		if (!paramBoolean2)
		{
			writeIndention(paramWriter, paramInt);
			paramWriter.write("}\n");
		} else
		{
			paramWriter.write("}");
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "writeComplexObject(Writer, int, boolean, boolean)");
		}
	}

	private boolean isEmptyObject()
	{
		boolean bool = false;
		if ((this.attrs == null) || ((this.attrs != null) && (this.attrs.isEmpty())))
		{
			if (this.jsonObjects.isEmpty())
			{
				if ((this.tagText == null) || ((this.tagText != null) && (this.tagText.equals(""))))
				{
					bool = true;
				}
			}
		}
		return bool;
	}

	private boolean isTextOnlyObject()
	{
		boolean bool = false;
		if ((this.attrs == null) || ((this.attrs != null) && (this.attrs.isEmpty())))
		{
			if (this.jsonObjects.isEmpty())
			{
				if ((this.tagText != null) && (!this.tagText.equals("")))
				{
					bool = true;
				}
			}
		}
		return bool;
	}
}
