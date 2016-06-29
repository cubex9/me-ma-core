package eu.cxn.mema.skelet;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface ILink extends IEntity, IConditioned {
    
    INode off();
    
    INode target();
    
    ILinkType type();
    
    Collection<ILinkTag> tags();
    
    double weight();
}
