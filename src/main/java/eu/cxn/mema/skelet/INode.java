package eu.cxn.mema.skelet;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface INode extends IEntity, IConditioned {
    
    String getName();

    INodeType getType();
    
    Collection<IFeature> features();
    
    Collection<INodeTag> tags();
    
    Collection<ILink> links();
    
    Collection<IData> datas();
}
