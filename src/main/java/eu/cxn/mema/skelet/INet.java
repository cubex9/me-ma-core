package eu.cxn.mema.skelet;

import eu.cxn.mema.skelet.ILink;
import eu.cxn.mema.skelet.INode;
import eu.cxn.mema.skelet.ITag;
import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface INet {
    
    Collection<INode> nodes();
    
    Collection<ILink> links();
    
    Collection<ITag> tags();
}
