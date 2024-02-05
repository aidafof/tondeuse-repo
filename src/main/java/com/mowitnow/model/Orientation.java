package com.mowitnow.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Getter
public enum Orientation {
    N("North"),
    E("East"),
    S("South"),
    W("West");

    //private char code;
    private String label;

    Orientation(String pLabel) {
        this.label = pLabel;
    }
    public static final Map<String, Orientation> MAP_TURN_LEFT = Map.of(
            "N", Orientation.W,
            "W", Orientation.S,
            "S", Orientation.E,
            "E", Orientation.N);
    public static final  Map<String, Orientation>MAP_TURN_RIGHT = Map.of(
            "N", Orientation.E,
            "W", Orientation.N,
            "S", Orientation.W,
            "E", Orientation.S);
    }