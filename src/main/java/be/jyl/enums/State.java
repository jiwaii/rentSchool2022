package be.jyl.enums;

public enum State {
    available ("available"),
    disabled("disabled"),
    lost("lost");

    private String text;

    State(String text) {
        this.text = text;
    }
    public String display(){return text;}
}
