package eu.cxn.mema.skeleton;

/**
 *
 * @author kubasek
 */
public interface IProject {

    String name();

    IEntity get(String id);
}
