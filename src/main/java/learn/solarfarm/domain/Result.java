package learn.solarfarm.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private ResultType type = ResultType.SUCCESS;
    private T payload;
    private ArrayList<String> messages = new ArrayList<>();

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;

    }
    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String message, ResultType type) {
        messages.add(message);
        this.type = type;
    }

    public boolean isSuccess(){
        return messages.isEmpty();
    }
}
