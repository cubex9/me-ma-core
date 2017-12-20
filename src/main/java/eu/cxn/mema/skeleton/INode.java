package eu.cxn.mema.skeleton;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface INode extends IEntity, IConditioned {

    String name();

    INodeType type();
    
    Collection<IFeature> features();
    
    Collection<ITag> tags();
    
    Collection<ILink> links();

    /**
     * vlozi jakoukoliv entitu do Nodu, on si ho uz spravne zaradi
     *
     * @param e
     * @return
     */
    INode put( IEntity e );
    
    Collection<IData> datas();
}
