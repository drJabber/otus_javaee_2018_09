package rnk.l10.exception;

import javax.xml.ws.WebFault;

@WebFault(name="RnkWebServiceException")
public class RnkWebServiceException extends Exception {
    public RnkWebServiceException(String message){
        super(message);
    }

    public RnkWebServiceException(String message, Throwable cause){
        super(message,cause);
    }

    public RnkWebServiceException(Throwable cause){
        super(cause);
    }
}
