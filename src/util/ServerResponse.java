package util;

import java.io.Serializable;

public class ServerResponse  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String response;
    private String reason;
    private String value;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
