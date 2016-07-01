/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author kubasek
 */
public class EntityTest {

    String json = "{" +
            "id : '4546576756765756'," +
            "clazz : 'Entity'" +
            "}";

    @Test
    public void readWrite() {

        Entity e = Entity.of( json );
        assertEquals( "4546576756765756", e.id());
    }

}
