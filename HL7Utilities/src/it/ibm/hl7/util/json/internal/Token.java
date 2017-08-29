package it.ibm.hl7.util.json.internal;

public class Token
{

	public static final Token TokenEOF = new Token();
	public static final Token TokenBraceL = new Token();
	public static final Token TokenBraceR = new Token();
	public static final Token TokenBrackL = new Token();
	public static final Token TokenBrackR = new Token();
	public static final Token TokenColon = new Token();
	public static final Token TokenComma = new Token();
	public static final Token TokenTrue = new Token();
	public static final Token TokenFalse = new Token();
	public static final Token TokenNull = new Token();
	private String valueString;
	private Number valueNumber;

	public Token()
	{
	}

	public Token(String paramString)
	{
		this.valueString = paramString;
	}

	public Token(Number paramNumber)
	{
		this.valueNumber = paramNumber;
	}

	public String getString()
	{
		return this.valueString;
	}

	public Number getNumber()
	{
		return this.valueNumber;
	}

	public boolean isString()
	{
		return null != this.valueString;
	}

	public boolean isNumber()
	{
		return null != this.valueNumber;
	}

	public String toString()
	{
		if (this == TokenEOF)
		{
			return "Token: EOF";
		}
		if (this == TokenBraceL)
		{
			return "Token: {";
		}
		if (this == TokenBraceR)
		{
			return "Token: }";
		}
		if (this == TokenBrackL)
		{
			return "Token: [";
		}
		if (this == TokenBrackR)
		{
			return "Token: ]";
		}
		if (this == TokenColon)
		{
			return "Token: :";
		}
		if (this == TokenComma)
		{
			return "Token: ,";
		}
		if (this == TokenTrue)
		{
			return "Token: true";
		}
		if (this == TokenFalse)
		{
			return "Token: false";
		}
		if (this == TokenNull)
		{
			return "Token: null";
		}
		if (isNumber())
		{
			return "Token: Number - " + getNumber();
		}
		if (isString())
		{
			return "Token: String - '" + getString() + "'";
		}
		return "Token: unknown.";
	}
}
