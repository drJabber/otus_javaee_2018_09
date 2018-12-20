package rnk.l10.exception;

import org.glassfish.jersey.server.ParamException;
import rnk.l10.rest.model.ErrorDto;
import rnk.l10.rest.model.ErrorTypeEnum;
import rnk.l10.rest.model.FieldErrorDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class QueryParamExceptionMapper implements ExceptionMapper<ParamException.QueryParamException> {
        @Override
        public Response toResponse(ParamException.QueryParamException e) {
            String parameter=e.getParameterName();
            List<FieldErrorDTO> errors=new ArrayList<>();
            if (e.getCause().getCause().getClass().equals(java.lang.NumberFormatException.class)){
                errors.add(new FieldErrorDTO("Ожидался числовой параметр",parameter));
            }else{
                errors.add(new FieldErrorDTO("Неверный тип параметра",parameter));
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorDto(ErrorTypeEnum.PARAM_ERROR,errors))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
}

