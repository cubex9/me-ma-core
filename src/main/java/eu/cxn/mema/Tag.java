package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.ITag;

/**
 * Created by kubasek on 7/1/2016.
 */
public class Tag extends Entity implements ITag {

    @JsonProperty
    String name;

    public Tag() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "("+id()+")";
    }
}
