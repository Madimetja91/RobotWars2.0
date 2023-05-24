package za.co.wethinkcode.toyworld.server;

import za.co.wethinkcode.toyworld.client.StateStatus;

public class ResponseUpdate {
    private String result;
    private String message;
    private StateStatus state;

    public ResponseUpdate(String message, StateStatus state) {
        this.result = "OK";
        this.message = message;
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StateStatus getState() {
        return state;
    }

    public void setState(StateStatus state) {
        this.state = state;
    }
}
