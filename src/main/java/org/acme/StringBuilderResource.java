package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/string-builder")
public class StringBuilderResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String appendDelete() {
        StringBuilder sb = new StringBuilder();
        sb.append(new char[1_000_000]);

        do
        {
            sb.append(12345);
            sb.delete(0, 5);
        } while (Thread.currentThread().isAlive());

        return "Never happens";
    }
}
