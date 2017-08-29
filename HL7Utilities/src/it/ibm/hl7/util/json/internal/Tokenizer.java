package it.ibm.hl7.util.json.internal;

import java.io.IOException;
import java.io.Reader;

public class Tokenizer
{

	private Reader reader;
	private int lineNo;
	private int colNo;
	private int lastChar;

	public Tokenizer(Reader paramReader) throws IOException
	{
		this.reader = paramReader;
		this.lineNo = 0;
		this.colNo = 0;
		this.lastChar = 10;

		readChar();
	}

	public Token next() throws IOException
	{
		while (Character.isWhitespace((char) this.lastChar))
		{
			readChar();
		}
		switch (this.lastChar)
		{
		case -1:
			readChar();
			return Token.TokenEOF;
		case 123:
			readChar();
			return Token.TokenBraceL;
		case 125:
			readChar();
			return Token.TokenBraceR;
		case 91:
			readChar();
			return Token.TokenBrackL;
		case 93:
			readChar();
			return Token.TokenBrackR;
		case 58:
			readChar();
			return Token.TokenColon;
		case 44:
			readChar();
			return Token.TokenComma;
		case 34:
		case 39:
			String str1 = readString();
			return new Token(str1);
		case 45:
		case 46:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
			Number localNumber = readNumber();
			return new Token(localNumber);
		case 102:
		case 110:
		case 116:
			String str2 = readIdentifier();
			if (str2.equals("null"))
			{
				return Token.TokenNull;
			}
			if (str2.equals("true"))
			{
				return Token.TokenTrue;
			}
			if (str2.equals("false"))
			{
				return Token.TokenFalse;
			}
			throw new IOException("Unexpected identifier '" + str2 + "' " + onLineCol());
		}
		throw new IOException("Unexpected character '" + (char) this.lastChar + "' " + onLineCol());
	}

	private String readString() throws IOException
	{
		StringBuffer localStringBuffer1 = new StringBuffer();
		int i = this.lastChar;
		int j = this.lineNo;
		int k = this.colNo;

		readChar();
		while ((-1 != this.lastChar) && (i != this.lastChar))
		{
			if (this.lastChar != 92)
			{
				localStringBuffer1.append((char) this.lastChar);
				readChar();
			} else
			{
				readChar();
				StringBuffer localStringBuffer2;
				switch (this.lastChar)
				{
				case 98:
					readChar();
					localStringBuffer1.append('\b');
					break;
				case 102:
					readChar();
					localStringBuffer1.append('\f');
					break;
				case 110:
					readChar();
					localStringBuffer1.append('\n');
					break;
				case 114:
					readChar();
					localStringBuffer1.append('\r');
					break;
				case 116:
					readChar();
					localStringBuffer1.append('\t');
					break;
				case 39:
					readChar();
					localStringBuffer1.append('\'');
					break;
				case 34:
					readChar();
					localStringBuffer1.append('"');
					break;
				case 92:
					readChar();
					localStringBuffer1.append('\\');
					break;
				case 47:
					readChar();
					localStringBuffer1.append('/');
					break;
				case 117:
				case 120:
					localStringBuffer2 = new StringBuffer();

					int m = 2;
					if (this.lastChar == 117)
					{
						m = 4;
					}
					for (int n = 0; n < m; n++)
					{
						readChar();
						if (!isHexDigit(this.lastChar))
						{
							throw new IOException("non-hex digit " + onLineCol());
						}
						localStringBuffer2.append((char) this.lastChar);
					}
					readChar();
					try
					{
						int n = Integer.parseInt(localStringBuffer2.toString(), 16);//n cannot be resolved to a variable	Tokenizer.java	/URLConnectorSamples/src/com/ibm/json/java/internal	line 140	Java Problem
						localStringBuffer1.append((char) n);
					} catch (NumberFormatException localNumberFormatException1)
					{
						throw new IOException("non-hex digit " + onLineCol());
					}
				default:
					if (!isOctalDigit(this.lastChar))
					{
						throw new IOException("non-hex digit " + onLineCol());
					}
					localStringBuffer2 = new StringBuffer();
					localStringBuffer2.append((char) this.lastChar);
					for (int i1 = 0; i1 < 2; i1++)
					{
						readChar();
						if (!isOctalDigit(this.lastChar))
						{
							break;
						}
						localStringBuffer2.append((char) this.lastChar);
					}
					try
					{
						int i1 = Integer.parseInt(localStringBuffer2.toString(), 8);//i1 cannot be resolved to a variable	Tokenizer.java	/URLConnectorSamples/src/com/ibm/json/java/internal	line 163	Java Problem
						localStringBuffer1.append((char) i1);
					} catch (NumberFormatException localNumberFormatException2)
					{
						throw new IOException("non-hex digit " + onLineCol());
					}
				}
			}
		}
		if (-1 == this.lastChar)
		{
			throw new IOException("String not terminated " + onLineCol(j, k));
		}
		readChar();

		return localStringBuffer1.toString();
	}

	private Number readNumber() throws IOException
	{
		StringBuffer localStringBuffer = new StringBuffer();
		int i = this.lineNo;
		int j = this.colNo;
		while (isDigitChar(this.lastChar))
		{
			localStringBuffer.append((char) this.lastChar);
			readChar();
		}
		String str1 = localStringBuffer.toString();
		try
		{
			if (-1 != str1.indexOf('.'))
			{
				return Double.valueOf(str1);
			}
			String str2 = "";
			if (str1.startsWith("-"))
			{
				str2 = "-";
				str1 = str1.substring(1);
			}
			if (str1.toUpperCase().startsWith("0X"))
			{
				return Long.valueOf(str2 + str1.substring(2), 16);
			}
			if (str1.equals("0"))
			{
				return new Long(0L);
			}
			if ((str1.startsWith("0")) && (str1.length() > 1))
			{
				return Long.valueOf(str2 + str1.substring(1), 8);
			}
			if ((str1.indexOf("e") != -1) || (str1.indexOf("E") != -1))
			{
				return Double.valueOf(str2 + str1);
			}
			return Long.valueOf(str2 + str1, 10);
		} catch (NumberFormatException localNumberFormatException)
		{
			IOException localIOException = new IOException("Invalid number literal " + onLineCol(i, j));
			localIOException.initCause(localNumberFormatException);
			throw localIOException;
		}
	}

	private boolean isHexDigit(int paramInt)
	{
		switch (paramInt)
		{
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 97:
		case 98:
		case 99:
		case 100:
		case 101:
		case 102:
			return true;
		}
		return false;
	}

	private boolean isOctalDigit(int paramInt)
	{
		switch (paramInt)
		{
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
			return true;
		}
		return false;
	}

	private boolean isDigitChar(int paramInt)
	{
		switch (paramInt)
		{
		case 43:
		case 45:
		case 46:
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 69:
		case 88:
		case 101:
		case 120:
			return true;
		}
		return false;
	}

	private String readIdentifier() throws IOException
	{
		StringBuffer localStringBuffer = new StringBuffer();
		while ((-1 != this.lastChar) && (Character.isLetter((char) this.lastChar)))
		{
			localStringBuffer.append((char) this.lastChar);
			readChar();
		}
		return localStringBuffer.toString();
	}

	private void readChar() throws IOException
	{
		if (10 == this.lastChar)
		{
			this.colNo = 0;
			this.lineNo += 1;
		}
		this.lastChar = this.reader.read();
		if (-1 == this.lastChar)
		{
			return;
		}
		this.colNo += 1;
	}

	private String onLineCol(int paramInt1, int paramInt2)
	{
		return "on line " + paramInt1 + ", column " + paramInt2;
	}

	public String onLineCol()
	{
		return onLineCol(this.lineNo, this.colNo);
	}
}
