package learn.solarfarm.domain;

import learn.solarfarm.models.Panel;

import java.util.ArrayList;
import java.util.List;

public class PanelResult {

    private ArrayList<String> messages = new ArrayList<>();

    private Panel payload;

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    public boolean isSuccess() {
        // If any error messages exist then return false because the operation failed
        return messages.size() == 0;
    }

    public Panel getPayload() {
        return payload;
    }

    public void setPayload() {
        this.payload = payload;

    }}
