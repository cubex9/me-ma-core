package eu.cxn.mema.script.adapters;

import eu.cxn.mema.mongoj.MongoJConn;

/**
 * Me-Ma-Core application api
 */
public interface MeMaApi {

    MongoJConn mongo();
}
