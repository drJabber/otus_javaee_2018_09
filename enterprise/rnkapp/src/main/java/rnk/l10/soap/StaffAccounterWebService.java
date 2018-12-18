package rnk.l10.soap;

import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.utils.StaffUtils;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName="StaffAccounterWebService",
        name="StaffAccounter",
        endpointInterface = "rnk.l10.soap.StaffAccounter",
        portName="staffutils",
        targetNamespace = "urn://rnk.l10.soap")
public class StaffAccounterWebService implements StaffAccounter {
    public Double getMaxSalary() throws RnkWebServiceException {
        return StaffUtils.getMaxSalary();
    };

    public Double getMinSalary() throws RnkWebServiceException{
        return StaffUtils.getMinSalary();
    };

    public Double getAvgSalary() throws RnkWebServiceException{
        return StaffUtils.getAvgSalary();
    };

    public String getPersonWithMaxSalary()throws RnkWebServiceException{
        return StaffUtils.getPersonWithMaxSalary();
    };
}
