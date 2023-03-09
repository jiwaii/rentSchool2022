package be.jyl.enums;

public enum ResponsibleType {
    student("student"),
    teacher("teacher"),
    staff("staff");

    private String text;

    ResponsibleType(String text) {
        this.text = text;
    }
    public String display(){return text;}
}
