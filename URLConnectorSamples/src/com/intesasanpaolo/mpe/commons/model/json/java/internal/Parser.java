package com.intesasanpaolo.mpe.commons.model.json.java.internal;

import java.io.IOException;
import java.io.Reader;

import com.intesasanpaolo.mpe.commons.model.json.java.JSONArray;
import com.intesasanpaolo.mpe.commons.model.json.java.JSONObject;
import com.intesasanpaolo.mpe.commons.model.json.java.OrderedJSONObject;

public class Parser
{

	private Tokenizer tokenizer;
	private Token lastToken;

	public Parser(Reader paramReader) throws IOException
	{
		this.tokenizer = new Tokenizer(paramReader);
	}

	public JSONObject parse() throws IOException
	{
		return parse(false);
	}

	public JSONObject parse(boolean paramBoolean) throws IOException
	{
		this.lastToken = this.tokenizer.next();
		return parseObject(paramBoolean);
	}

	public JSONObject parseObject() throws IOException
	{
		return parseObject(false);
	}

	public JSONObject parseObject(boolean paramBoolean) throws IOException
	{
		Object localObject1 = null;
		if (!paramBoolean)
		{
			localObject1 = new JSONObject();
		} else
		{
			localObject1 = new OrderedJSONObject();
		}
		if (this.lastToken != Token.TokenBraceL)
		{
			throw new IOException("Expecting '{' " + this.tokenizer.onLineCol() + " instead, obtained token: '" + this.lastToken + "'");
		}
		this.lastToken = this.tokenizer.next();
		do
		{
			for (;;)
			{
				if (this.lastToken == Token.TokenEOF)
				{
					throw new IOException("Unterminated object " + this.tokenizer.onLineCol());
				}
				if (this.lastToken == Token.TokenBraceR)
				{
					this.lastToken = this.tokenizer.next();
					return (JSONObject) localObject1;//FIXED: Type mismatch: cannot convert from Object to JSONObject	Parser.java	/URLConnectorSamples/src/com/ibm/json/java/internal	line 62	Java Problem
				}
				if (!this.lastToken.isString())
				{
					throw new IOException("Expecting string key " + this.tokenizer.onLineCol());
				}
				String str = this.lastToken.getString();

				this.lastToken = this.tokenizer.next();
				if (this.lastToken != Token.TokenColon)
				{
					throw new IOException("Expecting colon " + this.tokenizer.onLineCol());
				}
				this.lastToken = this.tokenizer.next();
				Object localObject2 = parseValue(paramBoolean);

				((JSONObject) localObject1).put(str, localObject2);
				if (this.lastToken != Token.TokenComma)
				{
					break;
				}
				this.lastToken = this.tokenizer.next();
			}
		} while (this.lastToken == Token.TokenBraceR);
		throw new IOException("expecting either ',' or '}' " + this.tokenizer.onLineCol());



		//return localObject1;//Unreachable code	Parser.java	/URLConnectorSamples/src/com/ibm/json/java/internal	line 88	Java Problem
	}

	public JSONArray parseArray() throws IOException
	{
		return parseArray(false);
	}

	public JSONArray parseArray(boolean paramBoolean) throws IOException
	{
		JSONArray localJSONArray = new JSONArray();
		if (this.lastToken != Token.TokenBrackL)
		{
			throw new IOException("Expecting '[' " + this.tokenizer.onLineCol());
		}
		this.lastToken = this.tokenizer.next();
		do
		{
			for (;;)
			{
				if (this.lastToken == Token.TokenEOF)
				{
					throw new IOException("Unterminated array " + this.tokenizer.onLineCol());
				}
				if (this.lastToken == Token.TokenBrackR)
				{
					this.lastToken = this.tokenizer.next();
					return localJSONArray;
				}
				Object localObject = parseValue(paramBoolean);
				localJSONArray.add(localObject);
				if (this.lastToken != Token.TokenComma)
				{
					break;
				}
				this.lastToken = this.tokenizer.next();
			}
		} while (this.lastToken == Token.TokenBrackR);
		throw new IOException("expecting either ',' or ']' " + this.tokenizer.onLineCol());



		//return localJSONArray;//FIXED: Unreachable code	Parser.java	/URLConnectorSamples/src/com/ibm/json/java/internal	line 128	Java Problem
	}

	public Object parseValue() throws IOException
	{
		return parseValue(false);
	}

	public Object parseValue(boolean paramBoolean) throws IOException
	{
		if (this.lastToken == Token.TokenEOF)
		{
			throw new IOException("Expecting property value " + this.tokenizer.onLineCol());
		}
		Object localObject;
		if (this.lastToken.isNumber())
		{
			localObject = this.lastToken.getNumber();
			this.lastToken = this.tokenizer.next();
			return localObject;
		}
		if (this.lastToken.isString())
		{
			localObject = this.lastToken.getString();
			this.lastToken = this.tokenizer.next();
			return localObject;
		}
		if (this.lastToken == Token.TokenFalse)
		{
			this.lastToken = this.tokenizer.next();
			return Boolean.FALSE;
		}
		if (this.lastToken == Token.TokenTrue)
		{
			this.lastToken = this.tokenizer.next();
			return Boolean.TRUE;
		}
		if (this.lastToken == Token.TokenNull)
		{
			this.lastToken = this.tokenizer.next();
			return null;
		}
		if (this.lastToken == Token.TokenBrackL)
		{
			return parseArray(paramBoolean);
		}
		if (this.lastToken == Token.TokenBraceL)
		{
			return parseObject(paramBoolean);
		}
		throw new IOException("Invalid token " + this.tokenizer.onLineCol());
	}
}
