package stress.util;


public class HTTPRelay
{

	public static void main(String[] args)
	{
		try
		{
			new SocketWaiter(1234);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
