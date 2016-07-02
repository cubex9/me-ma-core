package eu.cxn.mema.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;


/**
 * Created by kubasek on 7/2/2016.
 */

@Path("/api")
@Produces("text/plain")
public class MemaApi {

    @GET
    public String getApi() {
        // Return some cliched textual content
        return "This is me-ma-core rest service";
    }
}

