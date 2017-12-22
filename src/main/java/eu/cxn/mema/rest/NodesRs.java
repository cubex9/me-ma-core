package eu.cxn.mema.rest;

import eu.cxn.mema.app.MeMaCore;
import eu.cxn.mema.data.InMemory;
import eu.cxn.mema.script.adapters.MeMaApi;
import eu.cxn.mema.skeleton.IEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.stream.Collectors;

/**
 * Created by kubasek on 7/2/2016.
 */
@Path("/nodes")
@Produces("text/plain")
public class NodesRs {

    MeMaApi api = MeMaCore.getInstance();

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
    @Path("/project")
    @Produces("application/json; charset=utf-8")
    public String getNet() {
        return "[ "
                + api.mongo().collection(IEntity.class, "nodes").stream().map(n -> n.toString()).collect(Collectors.joining(",\n"))
                + " ]";
    }
}
