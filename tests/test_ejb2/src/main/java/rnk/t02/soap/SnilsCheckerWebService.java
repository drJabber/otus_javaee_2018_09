package rnk.t02.soap;

import org.apache.log4j.Logger;
import rnk.t02.exception.RnkWebServiceException;
import rnk.t02.utils.Snils;

import javax.ejb.Singleton;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Singleton(name = "SnisChecker")
//@WebService(serviceName="SnilsCheckerWebService",
//        name="SnilsChecker",
//        endpointInterface = "rnk.t02.soap.SnilsChecker",
//        portName="snilschecker",
//        targetNamespace = "urn://rnk.t02.soap")
public class SnilsCheckerWebService  implements SnilsChecker {
    private static final Logger logger = Logger.getLogger(SnilsCheckerWebService.class.getName());

    public boolean check(String snils) throws RnkWebServiceException {
        try {
            return Snils.isValid(snils);
        }catch(Exception ex){
            logger.error(ex);
            throw new RnkWebServiceException(ex);
        }
    }
}
