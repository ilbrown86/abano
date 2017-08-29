######################
Struttura progetti IIB
######################

1. HL7CommonLibrary
Contiene il modello HL7Model di disaccoppiamento tra i sistemi integrati dal bus e HL7.

2. HL7CoreApplication
Contiene i flussi applicativi da e verso Medarchiver.

3. HL7IntegrationService
Applicazione SOAP di test per verificare che i flussi di HL7CoreApplication funzionino correttamente.

4. HL7RestAPI
Applicazione rest esposta verso MFP.

5. HL7MQApplication
Applicazione che gestisce le code input e output WebSphere MQ.


######################
Struttura progetto Doc
######################

activities: stima attivita'

BrokerConfig: esempi di comandini per configurare nodi e server

MEDarchiver: documentazione interfacce Medarchiver

MFPRestInterface: swagger con specifiche interfacce rest per MFP

tmp: trash

writeParameters: sniff delle chiamate per write dei parametri con Medarchiver


###############
Main di utility
###############

CallHL7RestAPI: chiama le API esposte dall'applicazione HL7RestAPI

CallMedarchiver: chiama il server Medarchiver o un eventuale receiver

HTTPRelay: "sniffa" le chiamate e le salva su flesystem

