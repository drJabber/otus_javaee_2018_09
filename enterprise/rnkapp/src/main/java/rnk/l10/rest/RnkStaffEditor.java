package rnk.l10.rest;

import rnk.l10.exception.RnkWebServiceException;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/v2")
public interface RnkStaffEditor {
    @Path("/staffeditor/{id}")
    public Response edit(@PathParam("id") String id)throws RnkWebServiceException;

    @Path("/staffeditor")
    public Response add()throws RnkWebServiceException;

    @Path("/staffremover/{id}")
    public Response remove(@PathParam("id") String id) throws RnkWebServiceException;
}
