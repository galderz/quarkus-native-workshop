package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/timestamp")
public class TimestampResource {

    static long firstAccess = System.currentTimeMillis();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String timestamp() {
        return "First access " + firstAccess;
    }
}
