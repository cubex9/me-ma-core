package eu.cxn.mema.mongoj;

import eu.cxn.mema.Node;
import eu.cxn.mema.app.MeMaCore;
import eu.cxn.mema.script.adapters.MeMaApi;
import eu.cxn.mema.skeleton.IEntity;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MongoJCollectionTest {


    MeMaApi api = MeMaCore.getInstance();


    @Test
    public void insertAndList() {

        String collection = "nodes";


        api.mongo().dropCollection(collection);

        /* vytvorit kolekci */
        MongoJCollection<IEntity> coll = api.mongo().collection(IEntity.class, collection);

        /* vlozit Node */
        //Node n = IEntity.read("{ clazz: 'node', name: 'Caffe', tags: [ '8', '9' ]}");
        Node n = (Node) coll.insert(IEntity.read("{ clazz: 'node', name: 'Caffe', tags: [ '8', '9' ]}"));

        /* pri insertu se vyplni ID objektu */
        assertNotNull(n.id());

        /* stream kolekce */
        String nodes = coll.stream().map(q -> q.toString())
                .collect(Collectors.joining(",\n"));

        assertEquals("{\n" +
                "  \"clazz\" : \"node\",\n" +
                "  \"name\" : \"Caffe\",\n" +
                "  \"type\" : null,\n" +
                "  \"tags\" : [ \"8\", \"9\" ],\n" +
                "  \"_id\" : \"5a3a60c9acef8605dc7ac0f8\"\n" +
                "}", nodes);
    }

}