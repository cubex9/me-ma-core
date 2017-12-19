package eu.cxn.mema.skeleton;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Podminky
 *
 * @author kubasek
 */
public interface IConditioned {
    
    /**
     *  zdali pak je za danych podminek platny ?
     */
    default boolean isValid( IConditioned c ) {
        return true;
    }
    
    /**
     *  za danych podminek muze objekt transmutovat na jiny
     */
    default IConditioned transmutedTo( IConditioned c ) {
        return c;
    }
    
    /**
     *  za jakych podminek je feature platna
     */
    default Collection<ICondition> conditions() {
        return new LinkedList<>();
    }
}
