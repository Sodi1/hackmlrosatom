package com.rosatom.hackaton.model.enums;

public enum Gender {
    FEMALE(0), MALE(1);

    private final int id;

    Gender(int i) {
        id = i;
    }

    public int getId(){
        return id;
    }
}
