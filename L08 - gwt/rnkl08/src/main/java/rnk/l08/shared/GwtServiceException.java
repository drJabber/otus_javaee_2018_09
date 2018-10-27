package rnk.l08.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GwtServiceException extends Exception implements IsSerializable {

    public GwtServiceException(){
        super();
    }

    public GwtServiceException(String message){
        super(message);
    }

    public GwtServiceException(Exception ex){
        super(ex);
    }
}
