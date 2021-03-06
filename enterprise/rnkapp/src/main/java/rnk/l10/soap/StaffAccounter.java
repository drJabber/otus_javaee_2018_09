package rnk.l10.soap;

import rnk.l10.exception.RnkWebServiceException;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface StaffAccounter {
    @WebMethod @WebResult(name="result") 
    Double getMaxSalary() throws RnkWebServiceException;

    @WebMethod @WebResult(name="result")
    Double getMinSalary() throws RnkWebServiceException;

    @WebMethod @WebResult(name="result")
    Double getAvgSalary() throws RnkWebServiceException;

    @WebMethod @WebResult(name="result") 
    String getPersonWithMaxSalary()throws RnkWebServiceException;
}
