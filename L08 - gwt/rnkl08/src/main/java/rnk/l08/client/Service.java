package rnk.l08.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import rnk.l08.shared.dto.StaffDTO;
import rnk.l08.shared.dto.User;
import rnk.l08.shared.GwtServiceException;

import java.util.List;

@RemoteServiceRelativePath("Service")
public interface Service extends RemoteService {

    String getCurrencies() throws GwtServiceException;
    String getNews() throws GwtServiceException;
     List<StaffDTO> getStaff(String session) throws GwtServiceException;
    void saveStaff(String session, StaffDTO staff) throws GwtServiceException;
    void removeStaff(String session, StaffDTO staff) throws GwtServiceException;

}
