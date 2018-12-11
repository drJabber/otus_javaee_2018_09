package rnk.l10.rest;

import rnk.l10.entities.StaffEntity;
import rnk.l10.entities.beans.SearchResultCache;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;
import rnk.l10.utils.StaffUtils;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class RnkStaffImpl implements RnkStaff{

    @Context
    UriInfo uriInfo;

    @Context
    ServletContext context;

    @Override
    @PUT
    @Path("/staff/{id}")
    public Response edit(@Valid @BeanParam StaffDto staff)throws RnkWebServiceException{
        staff.save();
        invalidateCache(staff.getId());
        URI uri=uriInfo.getAbsolutePathBuilder().replacePath("/main/admin/staff").build();
        return Response.seeOther(uri).build();
    }

    @Override
    @DELETE
    @Path("/staff/{id}")
    public Response remove(@PathParam(value = "id") Integer id)throws RnkWebServiceException{
        StaffUtils.removeStaff(id);
        invalidateCache(id);

//        URI uri=uriInfo.getAbsolutePathBuilder().replacePath("/main/admin/staff").build();
//        return Response.seeOther(uri).build();
        return Response.ok().build();
    }

    @Override
    @POST
    @Path("/staff")
    public Response add( @Valid @BeanParam StaffDto staff) throws RnkWebServiceException {
        staff.save();
        invalidateCache(null);

        URI uri=uriInfo.getAbsolutePathBuilder().replacePath("/main/admin/staff").build();
        return Response.seeOther(uri).build();
    }

    private void invalidateCache(Integer staffId){
        SearchResultCache cache= (SearchResultCache)context.getAttribute("admin-search-cache");
        if (cache!=null){
            if (staffId!=null){
                cache.invalidateStaff(staffId);
            }else{
                cache.invalidateAll();
            }
        }
    }
}
