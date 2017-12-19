package eu.cxn.mema.mongoj;

public class MongoJCredentials {

    public String host;

    public String database;

    public MongoJAuth auth;


    public MongoJCredentials(String host, String database, MongoJAuth auth) {

        this.host = host;
        this.database = database;

        this.auth = auth;
    }
}
