package rnk.l10.soap;

import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.utils.StaffUtils;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName="StaffAccounterWebService",
        name="StaffAccounter",
        endpointInterface = "rnk.l10.soap.StaffAccounter",
        portName="staffutils",
        targetNamespace = "urn://rnk.l10.soap")
public class StaffAccounterWebService implements StaffAccounter {
    @WebMethod @WebResult(name="result")
    public Double getMaxSalary() throws RnkWebServiceException {
        return StaffUtils.getMaxSalary();
    };

    @WebMethod @WebResult(name="result")
    public Double getMinSalary() throws RnkWebServiceException{
        return StaffUtils.getMinSalary();
    };

    @WebMethod @WebResult(name="result")
    public Double getAvgSalary() throws RnkWebServiceException{
        return StaffUtils.getAvgSalary();
    };

    @WebMethod @WebResult(name="result")
    public String getPersonWithMaxSalary()throws RnkWebServiceException{
        return StaffUtils.getPersonWithMaxSalary();
    };
}
