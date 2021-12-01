package com.dtn.quickstart;

import java.net.URI;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import io.smallrye.mutiny.Uni;

@Path("/v1/gelf-logging")
@ApplicationScoped
public class GelfLoggingResource {

    private static final Logger LOG = Logger.getLogger(GelfLoggingResource.class);
    private static final String NORMAL_MESSAGE = "{\"errorCode\":\"%s\", \"errorMessage\":\"%s\",\"message\":\"%s\"}";

    @GET
    @Path("/debug")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> logDebug() {
        final String message = "debug message";
        LOG.debug(message);
        return Uni.createFrom().item(Response.ok(String.format(NORMAL_MESSAGE, "", "", message)).build());
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> logInfo(@QueryParam("message") String message) {
        return VeryBasicTableEntity.saveData(VeryBasicTableEntity.builder().message(message).build())
                .onItem()
                .transform(entity -> {
                    String redirectUri = String.join("", "/v1/gelf-logging/info/", entity.id.toString());
                    LOG.info(String.join("", "save successed redirect to", redirectUri));
                    return URI.create(redirectUri);
                })
                .onItem().transform(uri -> Response.created(uri))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> logInfoDetail(@PathParam("id") String id) {
        return VeryBasicTableEntity.findById(UUID.fromString(id))
                .onItem().ifNotNull().transform(entity -> {
                    LOG.info(String.join("", "find entity with id : ", id));
                    return Response.ok(entity).build();
                })
                .onItem().ifNull().continueWith(Response.ok().status(Status.NOT_FOUND)::build);
    }

    @GET
    @Path("/error")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> logError() {
        final String message = "error message";
        LOG.error(message, new Exception("WTF"));
        return Uni.createFrom().item(Response.ok(String.format(NORMAL_MESSAGE, "", "", message)).build());
    }

    @GET
    @Path("/warn")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> logWarn() {
        final String message = "warn message";
        LOG.warn(message);
        return Uni.createFrom().item(Response.ok(String.format(NORMAL_MESSAGE, "", "", message)).build());
    }
}