/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.mongo;

/**
 *
 * @author kubasek
 */
public class MongoEnu {

    /**
     * return codes of connection and operation of mongo
     */
    public enum ReturnCodes {

        OK(0),
        ERROR(1),
        ERROR_CONNECTION(4),
        ERROR_DATABASE_NOT_EXIST(5),
        ERROR_COLLECTION_NOT_EXIST(6),
        EXCEPTION_UNKNOWN_HOST(2),
        EXCEPTION_ILLEGALSTATE(3);
        
        private int code;

        private ReturnCodes( final int c) {
            code = c;
        }

        public int getCode() {
            return code;
        }
    }
}
