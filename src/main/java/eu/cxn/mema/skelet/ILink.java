package eu.cxn.mema.skelet;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface ILink extends IEntity, IConditioned {
    
    INode getOff();
    
    INode getTarget();
    
    ILinkType getType();
    
    Collection<ITag> tags();
    
    double getWeight();
}
