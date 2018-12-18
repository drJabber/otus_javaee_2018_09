package rnk.l10.soap;

import rnk.l10.exception.RnkWebServiceException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)

public interface SnilsChecker {
    @WebMethod @WebResult(name="result") 
    boolean check(@WebParam(name="snils", targetNamespace = "urn://rnk.l10.soap") 
                  String snils) throws RnkWebServiceException;
}
