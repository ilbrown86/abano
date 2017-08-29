package HL7CoreApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class HL7Sender_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		//definizione oggetti di comunicazione
		Socket s = null;
		OutputStream os = null;
		InputStream is = null;

		try {
			// create empty new message as a copy of the input
			MbMessage outMessage = new MbMessage();
			copyMessageHeaders(inMessage, outMessage);
			// OutputRoot
			MbElement outRoot = outMessage.getRootElement();
			// Create the Broker Blob Parser element - OutputRoot.BLOB
			MbElement outParser = outRoot
					.createElementAsLastChild(MbBLOB.PARSER_NAME);
			// assign outmessage to Output tree
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Delimiter, LF
			String LF = "\r";
// TEST
			boolean testReply = (boolean) getUserDefinedAttribute("testReply");
			// get MEDArchiver HL7 Client IP and Port
			String hl7ClientIP = (String) getUserDefinedAttribute("HL7ConnectionIP");
			int hl7ClientPort = (int) getUserDefinedAttribute("HL7ConnectionPORT");

			if (!testReply) {
				// Open connection with HL7 client
				s = new Socket(hl7ClientIP, hl7ClientPort);
				// Socket s = new Socket("10.4.2.39", 9999);
				// Socket s = new Socket("localhost", 9999);

				// set timeout 2 sec.
				s.setSoTimeout(180000);
				// Write HL7 Message got from InputRoot
				MbElement inBlob = inAssembly.getMessage().getRootElement()
						.getLastChild();
				MbElement blob = inBlob.getLastChild();
				byte[] mybytes = (byte[]) blob.getValue();
//TEST
// TEST				OutputStream reqToFile = new FileOutputStream("C:\\temp\\HL7response\\request_to_MED");
// TEST				reqToFile.write(mybytes, 0, mybytes.length);
// TEST				reqToFile.close();
//END TEST
				os = s.getOutputStream();
				os.write(0x0B);
				os.write(mybytes);

				os.write(0x1C);
				os.write(0x0D);
				os.flush();
				// Get response
				is = s.getInputStream();
				byte[] b = new byte[1024];
				int read;
//TEST
// TEST				OutputStream toFile = new FileOutputStream("C:\\temp\\HL7response\\response_from_MED");
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				while ((read = is.read(b)) > -1) {
// TEST					toFile.write(b, 0, read);
					outputStream.write(b, 0, read);
					if (b[read - 1] == 0x0D)
						break;

				}
				// test
// TEST				toFile.close();
// TEST				String testaRisposta = outputStream.toString();
				// Write to Out
				outParser.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
						"BLOB", outputStream.toByteArray());

				// close streams and connections
				closeConnections(s, is, os);
				// propagate message
				out.propagate(outAssembly);
			} 
		else {
			MbElement inBlob = inAssembly.getMessage().getRootElement()
					.getLastChild();
			MbElement blob = inBlob.getLastChild();
			byte[] mybytes = (byte[]) blob.getValue();
			OutputStream reqToFile = new FileOutputStream("C:\\temp\\HL7response\\request_to_MED");
			reqToFile.write(mybytes, 0, mybytes.length);
			reqToFile.close();
			
			// test risposta
				is = new FileInputStream(
						"C:\\temp\\HL7response\\test_response");
// chunk of 1Kb (1024 bytes)
				byte[] b = new byte[1024];
				int read;
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				while ((read = is.read(b)) > -1) {
					outputStream.write(b, 0, read);
					if (b[read - 1] == 0x0D)
						break;
				}
// test
				String testaRisposta = outputStream.toString();
				// Write to Out
				outParser.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
						"BLOB", outputStream.toByteArray());

// TEST				// close streams and connections
// TEST				closeConnections(s, is, os);
				// propagate message
				out.propagate(outAssembly);

			}

			// End of user code
			// ----------------------------------------------------------

			// TODO gestione eccezione di Timeout.. segnalare errore
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be
			// handled in the flow
			try {
				closeConnections(s, is, os);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				throw new MbUserException(this, "evaluate()", "", "", e1.toString(),
						null);
			}
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		// out.propagate(outAssembly);

	}

	private void closeConnections(Socket socket, InputStream inStream, OutputStream outStream) throws IOException {

		if (outStream != null) outStream.close();
		if (inStream != null) inStream.close();
		if (socket != null) socket.close();
	}

	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();
		MbElement header = inMessage.getRootElement().getFirstChild();

		while (header != null && header.getNextSibling() != null) {
			outRoot.addAsLastChild(header.copy());
			header = header.getNextSibling();
		}
	}

}
