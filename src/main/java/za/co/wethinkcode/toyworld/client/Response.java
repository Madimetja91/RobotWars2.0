package za.co.wethinkcode.toyworld.client;

public class Response {
    private String result;
    private String message;
    private State state;

    public Response(String result, String message, State state) {
        this.result = result;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
