package loggingsystem.enums;

import java.io.Serializable;

public enum Severity implements Serializable {

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
