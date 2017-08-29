package it.ibm.hl7.util.main;

import it.ibm.hl7.util.http.CallURL;
import it.ibm.hl7.util.json.JSONObject;

import java.io.ByteArrayOutputStream;


public class CallHL7RestAPI extends CallURL
{

	public static void main(String[] args) throws Throwable
	{
		CallURL wcm = new CallURL();
		String baseUrl = "http://localhost:7800";
		String url;
		int code;
		ByteArrayOutputStream baos = null;
		String response = null;

		baos = new ByteArrayOutputStream();
		url = baseUrl + "/ehr/patients/123456/details";

		baos = new ByteArrayOutputStream();
		code = wcm.call(url, null, null, "GET", 1, null, baos);
		System.out.println(code + " for " + url);
		response = new String(baos.toByteArray());
		System.out.println("body=" + response);

		//test per verificare che la risposta sia un JSON valido
		/*JSONObject obj = */JSONObject.parse(response);

		//esempio di chiamata post / put
		//String body = "{\"logonId\" : \"cristianom@it.ibm.com\",\"logonPassword\" : \"zaq12wsx\"}";
		//code = wcm.call(url, null, body, "POST"/*PUT*/, 1, null, baos);
		//System.out.println(code + " for " + url);
		//response = new String(baos.toByteArray());
		//System.out.println("body=" + response);

	}

}
