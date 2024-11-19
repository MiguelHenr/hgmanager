package com.cefetmg.hgmanager.Model.Enum;

public enum Cargo {
    TAE, PROFESSOR;

    public static Cargo ofName(String name) {
        return valueOf(name.toUpperCase());
    }
}