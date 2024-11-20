package core;

public enum StatusCode {

    SUCCESS(200,"The request succeeeded"),
    CREATED(201,"The new resource  is created"),
    BAD(400,"This is a bad request"),
    UNAUTHORIZED(401,"Invalid access token"),
    NOTFOUND(404,"Cannot find requested resource"),
    NOCONTENT(204,"No response to send in body");



    public final int code;
    public final String msg;

    StatusCode(int code, String msg){
        this.code= code;
        this.msg=msg;
    }
}
