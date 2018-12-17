package rnk.l10.soap;

import org.apache.log4j.Logger;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.utils.Snils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName="SnilsCheckreWebService",
        name="SnilsChecker",
        endpointInterface = "rnk.l10.soap.SnilsChecker",
        portName="snilschecker",
        targetNamespace = "urn://rnk.l10.soap")
public class SnilsCheckerWebService  implements SnilsChecker {
    private static final Logger logger = Logger.getLogger(SnilsCheckerWebService.class.getName());

    @WebMethod
    @WebResult(name="result")
    public boolean check(@WebParam(name="snils", targetNamespace = "urn://rnk.l10.soap")String snils) throws RnkWebServiceException {
        try {
            return Snils.isValid(snils);
        }catch(Exception ex){
            logger.error(ex);
            throw new RnkWebServiceException(ex);
        }
    }
}
