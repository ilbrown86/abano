package com.intesasanpaolo.mpe.commons.model.json.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.intesasanpaolo.mpe.commons.model.json.java.JSONArray;
import com.intesasanpaolo.mpe.commons.model.json.java.JSONObject;

public class JSONToXMLTransformer
{

	private static String className = "com.intesasanpaolo.mpe.commons.model.json.xml.transform.JSONToXMLTransformer";
	private static Logger logger = Logger.getLogger(className, null);
	private static final String styleSheet = " <xsl:stylesheet version=\"1.0\"                                   \n     xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">           \n   <xsl:output method=\"xml\"/>                                    \n   <xsl:param name=\"indent-increment\" select=\"'   '\" />        \n   <xsl:template match=\"*\">                                      \n      <xsl:param name=\"indent\" select=\"'&#xA;'\"/>              \n      <xsl:value-of select=\"$indent\"/>                           \n      <xsl:copy>                                                   \n        <xsl:copy-of select=\"@*\" />                              \n        <xsl:apply-templates>                                      \n          <xsl:with-param name=\"indent\"                          \n               select=\"concat($indent, $indent-increment)\"/>     \n        </xsl:apply-templates>                                     \n        <xsl:if test=\"*\">                                        \n          <xsl:value-of select=\"$indent\"/>                       \n        </xsl:if>                                                  \n      </xsl:copy>                                                  \n   </xsl:template>                                                 \n   <xsl:template match=\"comment()|processing-instruction()\">     \n      <xsl:param name=\"indent\" select=\"'&#xA;'\"/>              \n      <xsl:value-of select=\"$indent\"/>                           \n      <xsl:copy>                                                   \n        <xsl:copy-of select=\"@*\" />                              \n        <xsl:apply-templates>                                      \n          <xsl:with-param name=\"indent\"                          \n               select=\"concat($indent, $indent-increment)\"/>     \n        </xsl:apply-templates>                                     \n        <xsl:if test=\"*\">                                        \n          <xsl:value-of select=\"$indent\"/>                       \n        </xsl:if>                                                  \n      </xsl:copy>                                                  \n   </xsl:template>                                                 \n   <xsl:template match=\"text()[normalize-space(.)='']\"/>         \n </xsl:stylesheet>                                                 \n";

	public static void transform(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
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

	public static void transform(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean) throws IOException
	{
		if (logger.isLoggable(Level.FINER))
		{
			logger.entering(className, "transform(InputStream, OutputStream)");
		}
		if (paramOutputStream == null)
		{
			throw new NullPointerException("XMLStream cannot be null");
		}
		if (paramInputStream == null)
		{
			throw new NullPointerException("JSONStream cannot be null");
		}
		if (logger.isLoggable(Level.FINEST))
		{
			logger.logp(Level.FINEST, className, "transform", "Parsing the JSON and a DOM builder.");
		}
		try
		{
			JSONObject localJSONObject = JSONObject.parse(paramInputStream);



			DocumentBuilderFactory localObject = DocumentBuilderFactory.newInstance();//localObject cannot be resolved to a variable	JSONToXMLTransformer.java	/URLConnectorSamples/src/com/ibm/json/xml	line 69	Java Problem
			DocumentBuilder localDocumentBuilder = ((DocumentBuilderFactory) localObject).newDocumentBuilder();
			Document localDocument = localDocumentBuilder.newDocument();
			if (logger.isLoggable(Level.FINEST))
			{
				logger.logp(Level.FINEST, className, "transform", "Parsing the JSON content to XML");
			}
			convertJSONObject(localDocument, localDocument.getDocumentElement(), localJSONObject, "jsonObject");


			TransformerFactory localTransformerFactory = TransformerFactory.newInstance();
			Transformer localTransformer = null;
			if (paramBoolean)
			{
				localTransformer = localTransformerFactory
						.newTransformer(new StreamSource(
								new StringReader(
										" <xsl:stylesheet version=\"1.0\"                                   \n     xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">           \n   <xsl:output method=\"xml\"/>                                    \n   <xsl:param name=\"indent-increment\" select=\"'   '\" />        \n   <xsl:template match=\"*\">                                      \n      <xsl:param name=\"indent\" select=\"'&#xA;'\"/>              \n      <xsl:value-of select=\"$indent\"/>                           \n      <xsl:copy>                                                   \n        <xsl:copy-of select=\"@*\" />                              \n        <xsl:apply-templates>                                      \n          <xsl:with-param name=\"indent\"                          \n               select=\"concat($indent, $indent-increment)\"/>     \n        </xsl:apply-templates>                                     \n        <xsl:if test=\"*\">                                        \n          <xsl:value-of select=\"$indent\"/>                       \n        </xsl:if>                                                  \n      </xsl:copy>                                                  \n   </xsl:template>                                                 \n   <xsl:template match=\"comment()|processing-instruction()\">     \n      <xsl:param name=\"indent\" select=\"'&#xA;'\"/>              \n      <xsl:value-of select=\"$indent\"/>                           \n      <xsl:copy>                                                   \n        <xsl:copy-of select=\"@*\" />                              \n        <xsl:apply-templates>                                      \n          <xsl:with-param name=\"indent\"                          \n               select=\"concat($indent, $indent-increment)\"/>     \n        </xsl:apply-templates>                                     \n        <xsl:if test=\"*\">                                        \n          <xsl:value-of select=\"$indent\"/>                       \n        </xsl:if>                                                  \n      </xsl:copy>                                                  \n   </xsl:template>                                                 \n   <xsl:template match=\"text()[normalize-space(.)='']\"/>         \n </xsl:stylesheet>                                                 \n")));
			} else
			{
				localTransformer = localTransformerFactory.newTransformer();
			}
			Properties localProperties = new Properties();
			localProperties.put("method", "xml");
			localProperties.put("omit-xml-declaration", "yes");
			localProperties.put("version", "1.0");
			localProperties.put("indent", "true");
			localTransformer.setOutputProperties(localProperties);
			localTransformer.transform(new DOMSource(localDocument), new StreamResult(paramOutputStream));
		} catch (Exception localException)
		{
			Object localObject = new IOException("Problem during conversion");
			((IOException) localObject).initCause(localException);
			throw ((/*Throwable*/IOException) localObject);//Unhandled exception type Throwable	JSONToXMLTransformer.java	/URLConnectorSamples/src/com/ibm/json/xml	line 97	Java Problem
		}
		if (logger.isLoggable(Level.FINER))
		{
			logger.exiting(className, "transform(InputStream, OutputStream)");
		}
	}

	public static String transform(InputStream paramInputStream) throws IOException
	{
		return transform(paramInputStream, false);
	}

	public static String transform(InputStream paramInputStream, boolean paramBoolean) throws IOException
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

	public static String transform(File paramFile, boolean paramBoolean) throws IOException
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

	public static String transform(File paramFile) throws IOException
	{
		return transform(paramFile, false);
	}

	private static void convertJSONObject(Document paramDocument, Element paramElement, JSONObject paramJSONObject, String paramString)
	{
		Set localSet = paramJSONObject.keySet();
		Iterator localIterator = localSet.iterator();

		Element localElement = paramDocument.createElement(removeProblemCharacters(paramString));
		if (paramElement != null)
		{
			paramElement.appendChild(localElement);
		} else
		{
			paramDocument.appendChild(localElement);
		}
		while (localIterator.hasNext())
		{
			String str = (String) localIterator.next();
			Object localObject = paramJSONObject.get(str);
			if ((localObject instanceof Number))
			{
				localElement.setAttribute(str, escapeXMLSpecialCharacters(localObject.toString()));
			} else if ((localObject instanceof Boolean))
			{
				localElement.setAttribute(str, escapeXMLSpecialCharacters(localObject.toString()));
			} else if ((localObject instanceof String))
			{
				localElement.setAttribute(str, escapeXMLSpecialCharacters(localObject.toString()));
			} else if (localObject == null)
			{
				localElement.setAttribute(str, "");
			} else if ((localObject instanceof JSONObject))
			{
				convertJSONObject(paramDocument, localElement, (JSONObject) localObject, str);
			} else if ((localObject instanceof JSONArray))
			{
				convertJSONArray(paramDocument, localElement, (JSONArray) localObject, str);
			}
		}
	}

	private static void convertJSONArray(Document paramDocument, Element paramElement, JSONArray paramJSONArray, String paramString)
	{
		paramString = removeProblemCharacters(paramString);
		for (int i = 0; i < paramJSONArray.size(); i++)
		{
			Element localElement = paramDocument.createElement(paramString);
			if (paramElement != null)
			{
				paramElement.appendChild(localElement);
			} else
			{
				paramDocument.appendChild(localElement);
			}
			Object localObject = paramJSONArray.get(i);
			Text localText;
			if ((localObject instanceof Number))
			{
				localText = paramDocument.createTextNode(escapeXMLSpecialCharacters(localObject.toString()));
				localElement.appendChild(localText);
			} else if ((localObject instanceof Boolean))
			{
				localText = paramDocument.createTextNode(escapeXMLSpecialCharacters(localObject.toString()));
				localElement.appendChild(localText);
			} else if ((localObject instanceof String))
			{
				localText = paramDocument.createTextNode(escapeXMLSpecialCharacters(localObject.toString()));
				localElement.appendChild(localText);
			} else if ((localObject instanceof JSONObject))
			{
				convertJSONObject(paramDocument, localElement, (JSONObject) localObject, "jsonObject");
			} else if ((localObject instanceof JSONArray))
			{
				convertJSONArray(paramDocument, localElement, (JSONArray) localObject, "jsonArray");
			}
		}
	}

	private static String escapeXMLSpecialCharacters(String paramString)
	{
		String str = null;
		if (paramString != null)
		{
			StringBuffer localStringBuffer = new StringBuffer("");
			for (int i = 0; i < paramString.length(); i++)
			{
				char c = paramString.charAt(i);
				switch (c)
				{
				case '&':
					localStringBuffer.append("&amp;");
					break;
				case '>':
					localStringBuffer.append("&gt;");
					break;
				case '<':
					localStringBuffer.append("&lt;");
					break;
				case '"':
					localStringBuffer.append("&quot;");
					break;
				case '\'':
					localStringBuffer.append("&apos;");
					break;
				default:
					localStringBuffer.append(c);
				}
			}
			str = localStringBuffer.toString();
		}
		return str;
	}

	private static String removeProblemCharacters(String paramString)
	{
		String str = null;
		if (paramString != null)
		{
			StringBuffer localStringBuffer = new StringBuffer("");
			for (int i = 0; i < paramString.length(); i++)
			{
				char c = paramString.charAt(i);
				switch (c)
				{
				case ' ':
				case '!':
				case '"':
				case '#':
				case '%':
				case '&':
				case '\'':
				case '(':
				case ')':
				case '*':
				case '/':
				case ':':
				case ';':
				case '<':
				case '>':
				case '[':
				case '\\':
				case ']':
				case '^':
				case '{':
				case '|':
				case '}':
					localStringBuffer.append("_");
					break;
				case '$':
				case '+':
				case ',':
				case '-':
				case '.':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '=':
				case '?':
				case '@':
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case '_':
				case '`':
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
				case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
				case 'x':
				case 'y':
				case 'z':
				default:
					localStringBuffer.append(c);
				}
			}
			str = localStringBuffer.toString();
		}
		return str;
	}

	public static void main(String[] paramArrayOfString)
	{
		String str = paramArrayOfString[0];
		try
		{
			System.out.println(transform(new File(str)));
		} catch (Exception localException)
		{
			localException.printStackTrace();
		}
	}
}
