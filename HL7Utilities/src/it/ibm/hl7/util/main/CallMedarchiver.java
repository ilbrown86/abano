package it.ibm.hl7.util.main;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CallMedarchiver
{

	public static void main(String[] args) throws Exception
	{

		String LF = "\r";
		//Socket s = new Socket("10.4.2.221", 9999);
		//Call HL7Receiver LOCAL
		//Socket s = new Socket("localhost", 8888);
		//Socket s = new Socket("10.4.2.212", 8889);
		//Socket s = new Socket("localhost", 9999);
		//Socket s = new Socket("localhost", 1111);

		//call HL7Receiver PROD
		Socket s = new Socket("10.4.2.213", 8889);

		OutputStream os = s.getOutputStream();

		os.write(0x0B);

		
		//dimission
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A03|20170828161500|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170828161502||||20170828161504"+LF).getBytes());
		os.write(("PID|||132419600||TEST^PROVA||19800517|F|||VIA DELLE ROSE^^^^10^^^028060|||||||PRTRJH80H57Z604E"+LF).getBytes());
		os.write(("PV1||I|Chirurgia Maggiore^01^008A^1||||||||||||||||17/008606|||||||||||||||||||||||||20170828161506"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A||||||||||||||112402796"+LF).getBytes());
		//admission
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|20170724124300|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170724124302||||20170724124304"+LF).getBytes());
		os.write(("PID|||132779960||Gazzin^Giacomo||19800517|F|||VIA DELLE ROSE^^^^10^^^028060|||||||PRTRJH80H57Z604E"+LF).getBytes());
		os.write(("PV1||I|RIABILITAZIONE NUTRIZIONALE^10^01C^6||||||||||||||||16/008817|||||||||||||||||||||||||20170724124306"+LF).getBytes());
		os.write(("DG1|1||19^Riabilitazione nutrizionale|||A||||||||||||||113258901"+LF).getBytes());
		*/
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|5036201|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170724122711||||201707241216"+LF).getBytes());
		os.write(("PID|||118809570||PROVA^PROVA||19571213|F|||   ^^^^35100^^^028060|||||||PRVPRV57T53G224M"+LF).getBytes());
		os.write(("PV1||I|ORTOPEDIA^102^102^1||||||||||||||||17/000030|||||||||||||||||||||||||201707241216"+LF).getBytes());
		os.write(("DG1|1||1^Chirurgia maggiore|||A||||||||||||||109961490"+LF).getBytes());
		*/
		//dimissione
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|20170719122010|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170719122012||T||20170719122014"+LF).getBytes());
		os.write(("PID|||104452910||Cambria^Andrea||19800517|F|||VIA DELLE ROSE^^^^10^^^028060|||||||PRTRJH80H57Z604E"+LF).getBytes());
		os.write(("PV1||I|TERAPIA INTENSIVA^10^50F^10||||||||||||||||16/008816|||||||||||||||||||||||||20170719122016"+LF).getBytes());
		os.write(("DG1|1||12^Terapia Intensiva|||A||||||||||||||112258965"+LF).getBytes());
		*/
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|20170724153400|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170724153402||T||20170724153404"+LF).getBytes());
		os.write(("PID|||104452910||Cambria^Andrea||19800517|F|||VIA DELLE ROSE^^^^10^^^028060|||||||PRTRJH80H57Z604E"+LF).getBytes());
		os.write(("PV1||I|Tipo1^12^12G^12||||||||||||||||16/008816|||||||||||||||||||||||||20170724153406"+LF).getBytes());
		os.write(("DG1|1||12^Tipo1|||A||||||||||||||111358964"+LF).getBytes());
		*/
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|20170531154822|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170620213756||||20170620213758"+LF).getBytes());
		os.write(("PID|||132995500||Piero^Bardazzi||19800618|M|||VIA DELLE ROSE^^^^11^^^028060|||||||PRTTJH80H57Z604E"+LF).getBytes());
		os.write(("PV1||I|Cardiologia^^102C||||||||||||||||16/008818|||||||||||||||||||||||||20170620213800"+LF).getBytes());
		os.write(("DG1|1||00^Test|||A"+LF).getBytes());
		*/
		//admission greggio roberto
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|20170627125122|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170627125124||||20170627125126"+LF).getBytes());
		os.write(("PID|||133008380||Cupspricovero^Ceccato||19800518|M|||VIA DELLE ROSE^^^^13^^^028060|||||||PRTTJH80H57Z605E"+LF).getBytes());
		os.write(("PV1||I|Cardiologia^^102F||||||||||||||||16/008823|||||||||||||||||||||||||20170627125128"+LF).getBytes());
		os.write(("DG1|1||01^Chiururgia Maggiore|||A"+LF).getBytes());
		*/
		//admission bertan giovanna
/*		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|5035258|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170511112400||||20170511112430"+LF).getBytes());
		os.write(("PID|||105253890||BERTAN^GIOVANNA||19450801|F|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
		os.write(("PV1||I|300||||||||||||||||17/900001|||||||||||||||||||||||||20170511112430"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());*/
		
		//admission turrin mario
/*		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|5035274|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170511124900||||20170511124930"+LF).getBytes());
		os.write(("PID|||104866860||TURRIN^MARIO||19450801|F|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
		os.write(("PV1||I|202D||||||||||||||||99/008821|||||||||||||||||||||||||20170511124930"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());*/
		
		/*
		//transfer turrin mario --giusto adt02 e adt08
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|20170705125202|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170705125204||L||20170705125206"+LF).getBytes());
		os.write(("PID|||132507800||Bragato^Dina||19450801|F|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
		os.write(("PV1||I|CHIRURGIA GENERALE^^102D^1||||||||||||||||16/008820|||||||||||||||||||||||||20170705125208"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());*/
		
/*		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A03|5035273|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170511124500||||20170511124530"+LF).getBytes());
		os.write(("PID|||104866860||TURRIN^MARIO||19450801|F|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
		os.write(("PV1||I|610C||||||||||||||||16/008821|||||||||||||||||||||||||20170511124530"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());*/
		
		//transfer bertan giovanna
//		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|5035257|P|2.5.1"+LF).getBytes());
//		os.write(("EVN||20170504172400||||20170504172430"+LF).getBytes());
//		os.write(("PID|||105253890||BERTAN^GIOVANNA||19450801|F|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
//		os.write(("PV1||I|300||||||||||||||||17/900001|||||||||||||||||||||||||20170504172430"+LF).getBytes());
//		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());
		
		//DIMISSIONE BERTAN GIOVANNA
//		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A03|5035257|P|2.5.1"+LF).getBytes());
//		os.write(("EVN||20170504172400||||20170504172430"+LF).getBytes());
//		os.write(("PID|||105253890||BERTAN^GIOVANNA||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
//		os.write(("PV1||I|702A||||||||||||||||17/900001|||||||||||||||||||||||||20170504172430"+LF).getBytes());
//		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());
		
		
//		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|5035257|P|2.5.1"+LF).getBytes());
//		os.write(("EVN||20170502120358||||20170502120400"+LF).getBytes());
//		os.write(("PID|||113311310||PROVA^CUNO||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
//		os.write(("PV1||I|101||||||||||||||||17/000014|||||||||||||||||||||||||20170502120400"+LF).getBytes());
//		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());
//		
//		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A01|5035257|P|2.5.1"+LF).getBytes());
//		os.write(("EVN||20170419165058||||201704191649"+LF).getBytes());
//		os.write(("PID|||113311340||PROVA^CTRE||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCTR45M01G224N"+LF).getBytes());
//		os.write(("PV1||I|30794||||||||||||||||17/000015|||||||||||||||||||||||||201704191649"+LF).getBytes());
//		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|5035257|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170419165058||||201704191649"+LF).getBytes());
		os.write(("PID|||113311340||PROVA^CTRE||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCTR45M01G224N"+LF).getBytes());
		os.write(("PV1||I|30791||||||||||||||||17/000015|||||||||||||||||||||||||201704191649"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());
		*/
		//os.write(("DG1|1||CLIN1^DESC CLINICAL 	PATH|||A"+LF).getBytes());		

		//transfer
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A02|5035277|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170419165057||||201704191648"+LF).getBytes());
		os.write(("PID|||113311310||PROVA^CUNO||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
		os.write(("PV1||I|30796||||||||||||||||17/000014|||||||||||||||||||||||||201704191648"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());	*/
		//discharge
		/*os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A03|5035257|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170419165057||||201704191646"+LF).getBytes());
		os.write(("PID|||113311310||PROVA^CUNO||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCNU45M01G224W"+LF).getBytes());
		os.write(("PV1||I|30794||||||||||||||||17/000014|||||||||||||||||||||||||201704191646|201704191649"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());	*/
		//discharge
		/*
		os.write(("MSH|^~\\&|EHR_MEDARCHIVER|MEDARCHIVER|IBM_IIB|IBM|||ADT^A03|5035257|P|2.5.1"+LF).getBytes());
		os.write(("EVN||20170419165058||||201704191649"+LF).getBytes());
		os.write(("PID|||113311340||PROVA^CTRE||19450801|M|||VIA SAN PIETRO MONTAGNON^^^^35038^^^028092|||||||PRVCTR45M01G224N"+LF).getBytes());
		os.write(("PV1||I|30794||||||||||||||||17/000015|||||||||||||||||||||||||201704191649"+LF).getBytes());
		os.write(("DG1|1||01^Chirurgia Maggiore|||A"+LF).getBytes());*/
		
		//WRITE PARAMS
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170509153637||ORU^R01|HX4854ff5450000000000000000092076ded4429000000000000|P|2.5.1"+LF).getBytes());
//		os.write(("PID|||131175600||"+LF).getBytes());
//		os.write(("PV1||I|||||||||||||||||17/000004"+LF).getBytes());
//		os.write(("OBR|1||230010|IBMAPPLE|||20170317170900|||||||||||||||||||||||||||CPORTELLI"+LF).getBytes());
//		os.write(("OBX|1|NM|SC001^gcsEye^SRT||3.0||||||F"+LF).getBytes());
//		os.write(("OBX|2|NM|SC003^gcsEye^SRT||3.0||||||F"+LF).getBytes());
//		os.write(("OBR|2||230011|IBMAPPLE|||20170317170910|||||||||||||||||||||||||||CPORTELLI"+LF).getBytes());
//		os.write(("OBX|1|NM|SC001^gcsEye^SRT||3.0||||||F"+LF).getBytes());
		
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170308153035||ORU^R01|2523366|P|2.5.1"+LF).getBytes());
//		os.write(("EVN||20170308153035||||20170308153035"+LF).getBytes());
//		os.write(("PID|||131175600||TEST^CORSO MEDARCHIVER||19710101|F|||via maggini 200^^^^30100^^^027042|||||||TSTCSM71A41H360M"+LF).getBytes());
//		os.write(("PV1||I|||||||||||||||||17/000004|||||||||||||||||||||||||20170308153035"+LF).getBytes());
//		os.write(("OBR|1||9999|IBMAPPLE|||20170308153035|||||||||||||||||||||||||||CPORTELLI"+LF).getBytes());
//		os.write(("OBX|1|NM|SC001^pharmacologicallySedated^SRT||1||||||F"+LF).getBytes());
//		os.write(("OBX|2|NM|SC002^gcsEye^SRT||1^Nessuna||||||F"+LF).getBytes());
//		os.write(("OBX|3|NM|SC004^gcsVerbal^SRT||1^Nessun suono emesso||||||F"+LF).getBytes());
//		os.write(("OBX|4|NM|SC006^gcsMotor^SRT||1^Nessuna risposta||||||F").getBytes());

		/*measurement history + filtro su numero di risultati (campo 7 del QRD, ex:100) + filtro su date (campi 2 e 3 del QRF) */
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345|||100|131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC|20170101000000|20170103235959||ALL").getBytes());

		/*measurement history senza filtri */
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||ALL").getBytes());
//
//		/*measurement history + filtro su parametri (Codice parametri separati da virgola. Ex:SC001,SC002) */
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|25233631|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||SC001,SC002").getBytes());
//
//		/*patient details*/
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170309130000||QRY^R02|12345444444|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170309130000|R|I|12345444444||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||PAZ").getBytes());

//		os.write(("MSH|^~\\&|HIS||CRITIS||||QRY^R02|22222|P|2.3" + LF).getBytes());
//		os.write(("QRD|201404190805|R|I|Q4412||||123457|RES|" + LF).getBytes());
//		os.write(("QRF|VITAL|201404190800|201404190859").getBytes());

//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170322101358||QRY^R02|100120|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170322101358|R|I|100120||||131175600|RES|17/000004" + LF).getBytes());
//		os.write(("QRF|CdC||||ALL").getBytes());
		//os.write(("QRF|CdC").getBytes());

		//query con letto
//		os.write(("MSH|^~\\&|IBM_IIB|IBM|EHR_MEDARCHIVER|MEDARCHIVER|20170322101358||QRY^R02|100135|P|2.5.1" + LF).getBytes());
//		os.write(("QRD|20170322101358|R|I|100135|||||RES|^dip^stanza^letto" + LF).getBytes());
//		os.write(("QRF|CdC||||BED").getBytes());
		//os.write(("QRF|CdC").getBytes());
		
		os.write(0x1C);
		os.write(0x0D);
		os.flush();

		InputStream is = s.getInputStream();
		byte[] b = new byte[1024];
		int read;
		while ((read = is.read(b)) > -1)
		{
			System.out.println(new String(b, 0, read));
			if (b[read-1] == 0x0D) break; 

		}

		os.close();
		is.close();
		s.close();
	}
}
