package eu.cxn.mema.skeleton;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface ILink extends IEntity, IConditioned {

    INode off();

    INode target();

    ILinkType type();
    
    Collection<ITag> tags();

    double weight();
}
