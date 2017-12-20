package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.IProperty;

import java.util.List;

public class Property implements IProperty {

    @JsonProperty
    @JsonView(Views.Db.class)
    private String name;

    @JsonProperty
    @JsonView(Views.Db.class)
    private Object value;

    @JsonProperty
    @JsonView(Views.Db.class)
    private List<IProperty> properties;


    Property() {
    }

    public Property(String name, Object value) {

        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public List<IProperty> properties() {
        return properties;
    }

}
