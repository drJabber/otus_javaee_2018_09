package rnk.l10.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import rnk.l10.utils.StaffUtils;

@WebService(targetNamespace = "urn://rnk.l10.soap")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface StaffUtils {
    @WebMethod @WebResult(name="result") 
    Double getMaxSalary() throws RnkWebServiceException;
    
    @WebMethod @WebResult(name="result") 
    Double getAvgSalary() throws RnkWebServiceException;

    @WebMethod @WebResult(name="result") 
    String getPersonWithMaxSalary()throws RnkWebServiceExceptio;
}
