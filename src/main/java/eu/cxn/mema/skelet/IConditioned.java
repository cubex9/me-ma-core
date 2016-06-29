package eu.cxn.mema.skelet;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public interface IConditioned {
    
    /* zdali pak je za danych podminek platny ? */
    boolean isValid( IConditioned c );
    
    /* za danych podminek muze objekt transmutovat na jiny */
    IConditioned transmutedTo( IConditioned c );
    
    /* za jakych podminek je feature platna */
    Collection<ICondition> conditions();
}
