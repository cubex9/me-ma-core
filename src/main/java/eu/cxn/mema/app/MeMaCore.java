package eu.cxn.mema.app;


import eu.cxn.mema.mongoj.MongoJAuth;
import eu.cxn.mema.mongoj.MongoJConn;
import eu.cxn.mema.mongoj.MongoJCredentials;
import eu.cxn.mema.mongoj.MongoJException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 */
public class MeMaCore {
    private static final Logger LOG = LoggerFactory.getLogger(MeMaCore.class);


    /**
     * database object
     */
    public static MongoJConn mongo = null;

    /**
     * database credentials
     */
    public static MongoJCredentials mongoCredentials = null;


    static {

        environmentReader("c:/Users/kubasek/.me-ma/environment.properties");

        try {
            mongo = MongoJConn.connect(mongoCredentials);
        } catch (MongoJException e) {
            LOG.error("Cannot connect to mongo database {}: " + e.getMessage());
        }
    }


    private static void environmentReader(String file) {

        // load a properties file
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(file));
        } catch (Exception e) {
            LOG.error("enviroment.properties error: " + e.getMessage());
        }


        /* Database */
        mongoCredentials = new MongoJCredentials(
                prop.getProperty("mongo.host"),
                prop.getProperty("mongo.database"),
                new MongoJAuth(
                        prop.getProperty("mongo.user"),
                        prop.getProperty("mongo.passwd")
                )
        );
    }
}
