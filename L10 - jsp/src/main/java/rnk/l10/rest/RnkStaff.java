package rnk.l10.rest;

import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public interface RnkStaff {
    @PUT
    @Path("/staff/{id}")
    public Response edit(@Valid @BeanParam StaffDto staff)throws RnkWebServiceException;

    @POST
    @Path("/staff")
    public Response add( @Valid @BeanParam StaffDto staff)throws RnkWebServiceException;

    @DELETE
    @Path("/staff/{id}")
    public Response remove(@PathParam(value = "id") Integer id)throws RnkWebServiceException;
}
