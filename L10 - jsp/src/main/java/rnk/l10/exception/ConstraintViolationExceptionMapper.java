package rnk.l10.exception;

import rnk.l10.rest.model.ErrorDto;
import rnk.l10.rest.model.ErrorTypeEnum;
import rnk.l10.rest.model.FieldErrorDTO;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        final List<FieldErrorDTO> errors=e.getConstraintViolations().stream()
                .map(v->new FieldErrorDTO(
                        v.getMessage(),
                        Stream.of(v.getPropertyPath()
                                .toString()
                                .split("\\."))
                             .reduce((first,second)->second)
                             .get()))
                .collect(Collectors.toList());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorDto(ErrorTypeEnum.FIELD_ERROR,errors))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
