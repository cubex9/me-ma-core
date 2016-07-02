package eu.cxn.mema.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by kubasek on 7/2/2016.
 */
@Path("/nodes")
public class NodesRs {

    @GET
    @Produces("text/plain")
    public String getList() {
        return "AHOJ";
    }

}
