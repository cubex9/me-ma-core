package eu.cxn.mema.mongoj;

public class MongoJEnums {

    /**
     * return codes of connection and operation of mongo
     */
    public enum ReturnCodes {

        OK(0),
        ERROR(1),
        ERROR_CONNECTION(4),
        ERROR_DATABASE_NOT_EXIST(5),
        ERROR_COLLECTION_NOT_EXIST(6),
        UNKNOWN_HOST(2),
        ILLEGAL_STATE(3);

        private int code;

        private ReturnCodes(final int c) {
            code = c;
        }

        public int getCode() {
            return code;
        }
    }
}
