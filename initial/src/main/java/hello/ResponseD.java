package hello;

public class ResponseD {

    private final long code;
    private final String result;

    public ResponseD(long code, String result) {
        this.code = code;
        this.result = result;
    }

    public long getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }
}