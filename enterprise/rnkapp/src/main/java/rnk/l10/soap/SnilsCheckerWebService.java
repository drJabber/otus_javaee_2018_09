package rnk.l10.soap;

import org.apache.log4j.Logger;
import rnk.l10.ejb.snils.Validator;
import rnk.l10.exception.RnkWebServiceException;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.jws.WebService;

@WebService(serviceName="SnilsCheckerWebService",
        name="SnilsChecker",
        endpointInterface = "rnk.l10.soap.SnilsChecker",
        portName="snilschecker",
        targetNamespace = "urn://rnk.l10.soap")
public class SnilsCheckerWebService  implements SnilsChecker {
    private static final Logger logger = Logger.getLogger(SnilsCheckerWebService.class.getName());

    @EJB
    Validator snilsValidator;

    public boolean check(String snils) throws RnkWebServiceException {
        try {
            return snilsValidator.check(snils);
        }catch(Exception ex){
            logger.error(ex);
            throw new RnkWebServiceException(ex);
        }
    }
}
