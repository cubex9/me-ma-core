package eu.cxn.mema.skelet;

/**
 *
 * @author kubasek
 */
public interface INet {

    String getName();

    IEntity get( String id);

    String toJson();
}
