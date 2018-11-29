#!/bin/sh

wsimport -d ../../java -p rnk.l10.soap.cbko -keep -b http://www.w3.org/2001/XMLSchema.xsd -b ./cbr_custom.xjb -J"-Djavax.xml.accessExternalDTD=all" -Xnocompile http://www.cbr.ru/CreditInfoWebServ/CreditOrgInfo.asmx?WSDL 
