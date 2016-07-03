package eu.cxn.mema.rest;

import eu.cxn.mema.data.InMemory;

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
        return InMemory.net.toString();
    }

    /**
     * vraci json podobu netu, kdy vserchny entity nalezneme v mape klicovane pomoci id,
     * odkazy na ostatni nody jsou opet klicovane by id
     *
     * @return
     */
    @GET
    @Path("/net")
    @Produces("application/json")
    public String getNet() {
        return InMemory.net.toJson();
    }
}
