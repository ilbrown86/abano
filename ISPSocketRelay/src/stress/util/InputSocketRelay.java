package stress.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class InputSocketRelay extends SocketRelay
{

	public InputSocketRelay(Socket inputSocket, InputStream inputStream, Socket outputSocket, OutputStream outputStream, File dest, String id)
			throws FileNotFoundException
	{
		super(inputSocket, inputStream, outputSocket, outputStream, new File(dest.getAbsolutePath() + File.separator + id + "_request"));
	}
}
