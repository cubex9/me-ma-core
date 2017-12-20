package eu.cxn.mema.skeleton;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import eu.cxn.mema.Property;

import java.util.List;

@JsonSubTypes({
        @JsonSubTypes.Type(value = Property.class, name = "object")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "clazz")
public interface IProperty {

    /**
     * must have
     *
     * @return
     */
    String name();

    /**
     * can be null
     *
     * @return
     */
    Object value();

    /**
     * can be empty
     *
     * @return
     */
    List<? extends IProperty> properties();
}
