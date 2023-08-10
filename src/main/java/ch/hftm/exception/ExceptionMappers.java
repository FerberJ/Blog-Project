package ch.hftm.exception;


import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyReactiveViolationException;
import jakarta.ws.rs.core.Response;

public class ExceptionMappers {
    @ServerExceptionMapper(ResteasyReactiveViolationException.class)
    public Response handleViolationException(ResteasyReactiveViolationException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}
