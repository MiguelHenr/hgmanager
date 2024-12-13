package com.cefetmg.hgmanager.Model.Enum;

public enum Status {
    AGUARDANDO, 
    APROVADA("lilac"), 
    REJEITADA("red"), 
    CANCELADA("red");

    private final String color;

    Status() {
        this("beige");
    }

    Status(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}