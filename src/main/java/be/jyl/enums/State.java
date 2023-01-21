package be.jyl.enums;

public enum State {
    available ("available"),
    rental("rental"),
    disabled("disabled"),
    lost("lost");

    private String text;

    State(String text) {
        this.text = text;
    }
    public String display(){return text;}
}
