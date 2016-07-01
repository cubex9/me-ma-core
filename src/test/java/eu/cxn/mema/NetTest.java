package eu.cxn.mema;

import eu.cxn.mema.skelet.INet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kubasek on 7/1/2016.
 */
public class NetTest {

    String[] entities = new String[]{
        "{ id: '1', clazz: 'Node', name: 'Caffe'}",
        "{ id: '2', clazz: 'Node', name: 'Milk' }",
        "{ id: '3', clazz: 'Node', name: 'Sugar' }",
        "{ id: '4', clazz: 'Node', name: 'Cup' }",

        "{ id: '5', clazz: 'Link', off: '4', target: '1' }",
        "{ id: '6', clazz: 'Link', off: '4', target: '2' }",
        "{ id: '7', clazz: 'Link', off: '4', target: '3' }",

        "{ id: '8', clazz: 'Tag', name: 'food' }"
    };

    @Test
    public void build() {

        INet net = Net.of("TEST", entities );

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

        assertEquals( exp, net.toString());
    }
}
