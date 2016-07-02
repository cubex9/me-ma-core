package eu.cxn.mema.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;


/**
 * Created by kubasek on 7/2/2016.
 */

// The Java class will be hosted at the URI path "/helloworld"
@Path("/")
public class MemaApi {

    @GET
    @Produces("text/plain")
    @Path("api")
    public String api() {
        // Return some cliched textual content
        return "This is me-ma-core rest service";
    }
}

