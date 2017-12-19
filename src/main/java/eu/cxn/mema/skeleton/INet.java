package eu.cxn.mema.skeleton;

/**
 *
 * @author kubasek
 */
public interface INet {

    String getName();

    IEntity get( String id);

    String toJson();
}
