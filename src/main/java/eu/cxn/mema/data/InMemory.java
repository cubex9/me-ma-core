package eu.cxn.mema.data;

import eu.cxn.mema.Net;
import eu.cxn.mema.skelet.INet;

/**
 * Created by kubasek on 7/2/2016.
 */
public class InMemory {

    private static String[] entities = new String[]{
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

    public static INet net = Net.of("TEST-NET", entities);
}
