package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author kubasek
 */
public class Node extends Entity {

    @JsonProperty
    String name;

    public Node() {

    }

    public String getName() {
        return name;
    }

}
