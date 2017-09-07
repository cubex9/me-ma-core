package eu.cxn.mema;

import eu.cxn.mema.skelet.*;
import org.junit.Test;

/**
 * Created by kubasek on 7/1/2016.
 */
public class NetTest {

    String[] entities = new String[]{
        "{ id: '1', clazz: 'Node', name: 'Caffe', tags: [ '8', '9' ]}",
        "{ id: '2', clazz: 'Node', name: 'Milk', tags: [ '8', '10' ] }",
        "{ id: '3', clazz: 'Node', name: 'Sugar', tags: [ '8', '10' ] }",
        "{ id: '4', clazz: 'Node', name: 'Cup', tags: ['10'] }",

        "{ id: '5', clazz: 'Link', off: '4', target: '1' }",
        "{ id: '6', clazz: 'Link', off: '4', target: '2' }",
        "{ id: '7', clazz: 'Link', off: '4', target: '3' }",

        "{ id: '8', clazz: 'Tag', name: 'food' }",
        "{ id: '9', clazz: 'Tag', name: 'brown' }",
        "{ id: '10', clazz: 'Tag', name: 'white' }"
    };

    @Test
    public void build() {

        INet net = Net.of("TEST", entities);

        String exp = "Node:\n" +
            "\tCaffe(1),\n" +
            "\tMilk(2),\n" +
            "\tSugar(3),\n" +
            "\tCup(4)\n" +
            "Tag:\n" +
            "\tfood(8)\n" +
            "Link:\n" +
            "\t[Cup(4) -> Caffe(1)] (5),\n" +
            "\t[Cup(4) -> Milk(2)] (6),\n" +
            "\t[Cup(4) -> Sugar(3)] (7)";

        System.out.println(net.toString());
        System.out.println(net.toJson());

    }
}
