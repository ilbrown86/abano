package it.ibm.hl7.util.json.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SerializerVerbose extends Serializer
{

	private int indent = 0;

	public SerializerVerbose(Writer paramWriter)
	{
		super(paramWriter);
	}

	public void space() throws IOException
	{
		writeRawString(" ");
	}

	public void newLine() throws IOException
	{
		writeRawString("\n");
	}

	public void indent() throws IOException
	{
		for (int i = 0; i < this.indent; i++)
		{
			writeRawString("   ");
		}
	}

	public void indentPush()
	{
		this.indent += 1;
	}

	public void indentPop()
	{
		this.indent -= 1;
		if (this.indent < 0)
		{
			throw new IllegalStateException();
		}
	}

	public List getPropertyNames(Map paramMap)
	{
		List localList = super.getPropertyNames(paramMap);

		Collections.sort(localList);

		return localList;
	}
}
