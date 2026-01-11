package controller;

import model.state.LightState;

public interface Strategy {
    double getDurationFor(LightState state);

    default double getRedDuration() {
        return getDurationFor(null);
    }
}