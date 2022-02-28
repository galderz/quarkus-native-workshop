package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Example sourced from
 * <a href="https://github.com/apangin/java-profiling-presentation/blob/master/src/demo1/StringBuilderTest.java">Andrei Pangin's Java Profiling presentation</a>.
 */
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
