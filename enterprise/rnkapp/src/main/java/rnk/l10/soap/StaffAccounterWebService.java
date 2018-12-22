package rnk.l10.soap;

import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.ejb.stats.StaffUtils;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(serviceName="StaffAccounterWebService",
        name="StaffAccounter",
        endpointInterface = "rnk.l10.soap.StaffAccounter",
        portName="staffutils",
        targetNamespace = "urn://rnk.l10.soap")
public class StaffAccounterWebService implements StaffAccounter {

    @EJB
    StaffUtils staffUtils;


    public Double getMaxSalary() throws RnkWebServiceException {
        return staffUtils.getMaxSalary();
    };

    public Double getMinSalary() throws RnkWebServiceException{
        return staffUtils.getMinSalary();
    };

    public Double getAvgSalary() throws RnkWebServiceException{
        return staffUtils.getAvgSalary();
    };

    public String getPersonWithMaxSalary()throws RnkWebServiceException{
        return staffUtils.getPersonWithMaxSalary();
    };
}
