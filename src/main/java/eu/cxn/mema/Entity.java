package eu.cxn.mema;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.cxn.mema.skelet.IEntity;
import static eu.cxn.mema.xlo.Xlo.info;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import static eu.cxn.mema.xlo.Xlo.info;

/**
 *
 * @author kubasek
 */
public abstract class Entity implements IEntity {
    
    private static final ObjectMapper om = new ObjectMapper();
    static {
        om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        om.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        om.configure(MapperFeature.USE_ANNOTATIONS,true);
    }



    private ObjectId guid;

    private String clazz;

    @Override
    public Map serialize() {
        /* pokud nebyl nikdy pouzity, je potreba inicializovat id */
        info( "SERIALIZE: " + clazz() + " [ "+guid()+" ]");
        
        /* pak uz standartni process */
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("_id", guid);
        res.put("clazz", clazz());
        
        om.writeValueAsString(this);

        return res;
    }

    @Override
    public <T extends IEntity> T deserialize(Map m) {
        
        
        return null;
    }

    @Override
    public String guid() {
        if (guid == null) {
            guid = ObjectId.get();
        }
        return guid.toString();
    }

    @Override
    public String clazz() {
        try {
            if (clazz == null) {
                clazz = getClass().getName();
            }
            return clazz;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
