package rnk.l10.entities.beans;

import rnk.l10.soap.ws.RnkWebServiceException_Exception;
import rnk.l10.soap.ws.StaffUtils;
import rnk.l10.soap.ws.StaffUtilsWebService;

public class StaffUtilsWrapper {

    private static StaffUtilsWebService ws=new StaffUtilsWebService();

    public String getMinSalary() throws RnkWebServiceException_Exception {
        StaffUtils staffutils=ws.getStaffutils();
        return ((Double)staffutils.getMinSalary()).toString();
    }
}
