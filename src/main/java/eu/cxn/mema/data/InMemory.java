package eu.cxn.mema.data;

import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.IProject;

/**
 * Created by kubasek on 7/2/2016.
 */
public class InMemory {

    private static String entities = "{" +
            "'clazz' :'project'," +
            "'name' : 'TEST-NET'," +
            "'entities' :[ " +
            "{ _id: '1', clazz: 'Node', name: 'Caffe', tags: [ '8', '9' ]}," +
            "{ _id: '2', clazz: 'Node', name: 'Milk', tags: [ '8', '10' ] }," +
            "{ _id: '3', clazz: 'Node', name: 'Sugar', tags: [ '8', '10' ] }," +
            "{ _id: '4', clazz: 'Node', name: 'Cup', tags: ['10'] }," +

            "{ _id: '5', clazz: 'Link', off: '4', target: '1' }," +
            "{ _id: '6', clazz: 'Link', off: '4', target: '2' }," +
            "{ _id: '7', clazz: 'Link', off: '4', target: '3' }," +

            "{ _id: '8', clazz: 'Tag', name: 'food' }," +
            "{ _id: '9', clazz: 'Tag', name: 'brown' }," +
            "{ _id: '10', clazz: 'Tag', name: 'white' }," +
            "]" +
            "}";


    public static IProject net = IEntity.read(entities);
}
