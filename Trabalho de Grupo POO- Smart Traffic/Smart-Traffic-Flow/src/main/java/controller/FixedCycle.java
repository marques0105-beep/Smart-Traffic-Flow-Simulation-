package controller;

import model.state.*;

public class FixedCycle implements Strategy {

    @Override
    public double getDurationFor(LightState state) {
        if (state instanceof GreenState) return 5;
        if (state instanceof YellowState) return 2;
        return 5; // Red
    }
}
