package eu.cxn.mema.mongoj;

public class MongoJException extends Exception {


    private MongoJEnums.ReturnCodes rc;

    public MongoJException(MongoJEnums.ReturnCodes rc, String message) {
        super(message);
    }

    public MongoJEnums.ReturnCodes getReturnCode() {
        return rc;
    }

    @Override
    public String toString() {
        return rc.name() + ": " + getMessage();
    }
}
