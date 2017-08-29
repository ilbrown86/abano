package it.ibm.sample;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PingMedarchiver
{

	public static void main(String[] args) throws Exception
	{

		String LF = "\r";
		Socket s = new Socket("10.4.2.39", 9999);
		//Socket s = new Socket("localhost", 1234);
		//Socket s = new Socket("localhost", 1111);

		OutputStream os = s.getOutputStream();

		os.write(0x0B);
		/* Write Parameters */
		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170308153035||ORU^R01|25233631|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170308153035||||20170308153035"+LF).getBytes());
		os.write(("PID|||131175600||TEST^CORSO MEDARCHIVER||19710101|F|||via maggini 200^^^^30100^^^027042|||||||TSTCSM71A41H360M"+LF).getBytes());
		os.write(("PV1||I|||||||||||||||||17/000004|||||||||||||||||||||||||20170308153035"+LF).getBytes());
		os.write(("OBR|1||12345|IBMAPPLE|||20170308153035|||||||||||||||||||||||||||CPORTELLI"+LF).getBytes());
		os.write(("OBX|1|NM|SC001^pharmacologicallySedated^SRT||1||||||F"+LF).getBytes());
		os.write(("OBX|2|NM|SC002^gcsEye^SRT||1^Nessuna||||||F"+LF).getBytes());
		os.write(("OBX|3|NM|SC004^gcsVerbal^SRT||1^Nessun suono emesso||||||F"+LF).getBytes());
		os.write(("OBX|4|NM|SC006^gcsMotor^SRT||1^Nessuna risposta||||||F").getBytes());

		/*measurement history + filtro su numero di risultati (campo 7 del QRD, ex:100) + filtro su date (campi 2 e 3 del QRF) */
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345|||100|131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC|20170101000000|20170103235959||ALL").getBytes());

		/*measurement history senza filtri - tutte le misure. Senza limiti di date.*/
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|11111||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||ALL").getBytes());

		/*measurement history senza filtri - tutte le misure. Con limiti di date.*/
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC|20170101000000|20170103235959||ALL").getBytes());

		/*measurement history + filtro su parametri (Codice parametri separati da virgola. Ex:SC001,SC002) */
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||SC001,SC002").getBytes());

		/*measurement history + filtro su parametri (Codice parametri separati da virgola. Ex:SC001,SC002). Con limiti di Date*/
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC|20170101000000|20170103235959||SC001,SC002").getBytes());

		/*measurement history + tutti i parametri. SOLO ID patient (12345) no Nosologic ID */
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES||" + LF).getBytes());
//		os.write(("QRF|CdC||||ALL").getBytes());
		
		/*patient details. Non dovrebbe restituire i dettagli dei risultati*/
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||").getBytes());

//		os.write(("MSH|^~\\&|HIS||CRITIS||||QRY^R02|22222|P|2.3" + LF).getBytes());
//		os.write(("QRD|201404190805|R|I|Q4412||||123457|RES|" + LF).getBytes());
//		os.write(("QRF|VITAL|201404190800|201404190859").getBytes());

		os.write(0x1C);
		os.write(0x0D);
		os.flush();

		InputStream is = s.getInputStream();
		byte[] b = new byte[1024];
		int read;
		while ((read = is.read(b)) > -1)
		{
			System.out.println(new String(b, 0, read));
		}

		os.close();
		is.close();
		s.close();
	}
}
