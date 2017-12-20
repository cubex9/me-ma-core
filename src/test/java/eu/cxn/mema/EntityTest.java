/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema;

import eu.cxn.mema.skeleton.IEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author kubasek
 */
public class EntityTest {

    String json = "{" +
            "'_id' : '4546576756765756'," +
            "'clazz' : 'entity'" +
            "}";

    @Test
    public void readWrite() {

        Entity e = IEntity.read(json);
        assertEquals( "4546576756765756", e.id());
    }

}
