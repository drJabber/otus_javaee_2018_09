package rnk.l10.entities.beans;

import rnk.l10.soap.ws.RnkWebServiceException_Exception;
import rnk.l10.soap.ws.StaffAccounter;
import rnk.l10.soap.ws.StaffAccounterWebService;

public class StaffUtilsWrapper {

    private static StaffAccounterWebService ws=new StaffAccounterWebService();
    public String getMinSalary() throws RnkWebServiceException_Exception{
        StaffAccounter staffUtils=ws.getStaffutils();
        return ((Double)staffUtils.getMinSalary()).toString();
    }
}
