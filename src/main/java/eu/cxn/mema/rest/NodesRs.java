package eu.cxn.mema.rest;

import eu.cxn.mema.StaticStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by kubasek on 7/2/2016.
 */
@Path("/nodes")
@Produces("text/plain")
public class NodesRs {

    @GET
    @Path("/info")
    public String info() {
        return StaticStore.net.toString();
    }

    @GET
    public String getList() {
        return "nic";
    }
}
