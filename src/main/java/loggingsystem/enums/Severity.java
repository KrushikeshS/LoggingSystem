package loggingsystem.enums;

public enum Severity {

    HIGH("high"),
    WARN("warn"),
    LOW("low");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Severity(String name){
        this.name = name;
    }
}
