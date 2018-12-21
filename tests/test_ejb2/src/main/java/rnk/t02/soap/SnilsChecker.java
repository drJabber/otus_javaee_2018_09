package rnk.t02.soap;

import rnk.t02.exception.RnkWebServiceException;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

//@WebService
//@SOAPBinding(style= SOAPBinding.Style.RPC)
//
@Remote
public interface SnilsChecker {
//    @WebMethod @WebResult(name="result")
    boolean check(
//            @WebParam(name="snils", targetNamespace = "urn://rnk.t02.soap")
                  String snils) throws RnkWebServiceException;
}
