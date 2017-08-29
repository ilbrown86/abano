package jad.cmd.bat;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;



public class Main
{
	public static void main(String[] args)
	{
		//dir /s /B *.class > list.bat
		//jadcmd.bat 1>jadcmd.log 2>&1
		BufferedReader reader = null;//new BufferedReader
		StringBuffer sc = new StringBuffer();

//		String base = "C:\\deco\\android\\";
		String base = "C:\\deco\\cordova_plugins\\";
		
		try
		{
			reader = new BufferedReader(new FileReader(base+"list.bat"));
			String s;
			while ((s = reader.readLine()) !=null)
			{
				s = "jad -lnc -s .java -d " + s.substring(0,s.lastIndexOf('\\')) + " " + s;
				sc.append(s+System.getProperty("line.separator"));
				
			}
			//System.out.println(sc);
			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		OutputStream out = null;
		try
		{
			out = new FileOutputStream(base+"jadcmd.bat");
			out.write(sc.toString().getBytes());
			out.flush();
			out.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}
