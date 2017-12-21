package eu.cxn.mema;

import eu.cxn.mema.skeleton.IEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kubasek on 7/1/2016.
 */
public class NodeTest {

    String json = "{" +
            "_id : '4546576756765756'," +
            "clazz : 'node'," +
            "name : 'TEST-NODE'" +
            "}";

    @Test
    public void readNode() {

        Node n = IEntity.read(json);
        assertEquals("{\n" +
                "  \"clazz\" : \"node\",\n" +
                "  \"name\" : \"TEST-NODE\",\n" +
                "  \"type\" : null,\n" +
                "  \"tags\" : null,\n" +
                "  \"_id\" : \"4546576756765756\"\n" +
                "}", n.toString());

        assertEquals("{\n" +
                "  \"clazz\" : \"node\",\n" +
                "  \"name\" : \"TEST-NODE\",\n" +
                "  \"type\" : null,\n" +
                "  \"tags\" : null,\n" +
                "  \"_id\" : \"4546576756765756\"\n" +
                "}", n.toString());
    }
}

