package rnk.l10.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName="StaffUtilsWebService",
        name="StaffUtils",
        endpointInterface = "rnk.l10.soap.StaffUtils",
        portName="staffutils",
        targetNamespace = "urn://rnk.l10.soap")
public class StaffUtilsWebService implements StaffUtils{
    @WebMethod @WebResult(name="result")
    public Double getMaxSalary() throws RnkWebServiceException{
        return (new rnk.l10.utils.StaffUtils()).getMaxSalary();
    };

    @WebMethod @WebResult(name="result")
    public Double getMinSalary() throws RnkWebServiceException{
        return (new rnk.l10.utils.StaffUtils()).getMinSalary();
    };

    @WebMethod @WebResult(name="result")
    public Double getAvgSalary() throws RnkWebServiceException{
        return (new rnk.l10.utils.StaffUtils()).getAvgSalary();
    };

    @WebMethod @WebResult(name="result")
    public String getPersonWithMaxSalary()throws RnkWebServiceException{
        return (new rnk.l10.utils.StaffUtils()).getPersonWithMaxSalary();
    };
}
