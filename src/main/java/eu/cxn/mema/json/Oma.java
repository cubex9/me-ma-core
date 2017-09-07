package eu.cxn.mema.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author kubasek
 */
public class Oma {
    private static final Logger LOG = LoggerFactory.getLogger(Oma.class);

    /**
     * default object mapper settings
     */
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
        om.configure(MapperFeature.USE_ANNOTATIONS, true);
    }

    /**
     * get the object mapper, yo, yo to je vonco... ;-)
     *
     *
     * @return
     */
    public static ObjectMapper get() {
        return om;
    }

    /**
     * write to string
     *
     * @param o
     * @return
     */
    public static String write(Object o) {
        try {
            return get().writeValueAsString(o);
        } catch (JsonProcessingException jpe) {
            LOG.error("Can't process object to json: {}", o, jpe);
            throw new IllegalStateException(jpe);
        }
    }

    /**
     * read from string
     * @param data
     * @param clazz
     * @return
     */
    public static Object read(String data, Class<?> clazz) {
        try {
            return get().readValue(data, clazz);
        } catch( IOException ioe ) {
            LOG.error( "Cant read json data: " + data, ioe );
            throw new IllegalStateException("Cant read" + data);
        }
    }

}
