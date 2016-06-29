  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.mongo;

import java.util.Map;
import org.bson.BasicBSONObject;

/**
 *
 * @author kubasek
 */
public class MongoAuth {

    private String user;
    private String passwd;

    public MongoAuth() {
    }

    public MongoAuth(String user, String passwd) {
        this.user = user;
        this.passwd = passwd;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @return
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * serialization implementation
     */
    public Map toMap() {
        return (Map) (new BasicBSONObject("name", "MongoAuth")
                .append("user", user)
                .append("passwd", passwd));
    }

    /**
     * deserialization implementation
     *
     * @param map
     */
    public void putAll(Map map) {
        user = (String) map.get("user");
        passwd = (String) map.get("passwd");
    }
}
