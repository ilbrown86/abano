package it.ibm.hl7.util.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public abstract interface JSONArtifact
{

	public abstract void serialize(OutputStream paramOutputStream) throws IOException;

	public abstract void serialize(OutputStream paramOutputStream, boolean paramBoolean) throws IOException;

	public abstract void serialize(Writer paramWriter) throws IOException;

	public abstract void serialize(Writer paramWriter, boolean paramBoolean) throws IOException;

	public abstract String serialize(boolean paramBoolean) throws IOException;

	public abstract String serialize() throws IOException;
}
