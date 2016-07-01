package eu.cxn.mema;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kubasek on 7/1/2016.
 */
public class NodeTest {

    String json = "{" +
            "guid : '4546576756765756'," +
            "clazz : 'Node'," +
            "name : 'pepa'" +
            "}";

    @Test
    public void readNode() {

        Node n = (Node)Entity.of( json );
        assertEquals("pepa", n.getName());
    }
}

