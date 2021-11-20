package com.dtn.quickstart;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/gelf-logging")
@ApplicationScoped
public class GelfLoggingResource {

    private static final Logger LOG = Logger.getLogger(GelfLoggingResource.class);

    @GET
    @Path("/debug")
    @Produces(MediaType.TEXT_PLAIN)
    public String logDebug() {
        final String message = "debug message";
        LOG.debug(message);
        return message;
    }

    @GET
    @Path("/info")
    @Produces(MediaType.TEXT_PLAIN)
    public String logInfo() {
        final String message = "info message";
        LOG.info(message);
        return message;
    }

    @GET
    @Path("/error")
    @Produces(MediaType.TEXT_PLAIN)
    public String logError() {
        final String message = "error message";
        LOG.error(message, new Exception("WTF"));
        return message;
    }

    @GET
    @Path("/warn")
    @Produces(MediaType.TEXT_PLAIN)
    public String logWarn() {
        final String message = "warn message";
        LOG.warn(message);
        return message;
    }
}