package eu.cxn.mema;

import eu.cxn.mema.json.Oma;
import eu.cxn.mema.skelet.IEntity;
import org.bson.types.ObjectId;

import java.util.LinkedHashMap;
import java.util.Map;

import static eu.cxn.mema.xlo.Xlo.info;

/**
 *
 * @author kubasek
 */
public abstract class Entity implements IEntity {
    

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

        Oma.write(this);

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
