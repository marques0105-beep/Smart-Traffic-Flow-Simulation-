package controller;

import model.state.*;

public class AdaptiveCycle implements Strategy {

    private int waitingCars = 0;

    public void setWaitingCars(int count) {
        this.waitingCars = count;
    }

    @Override
    public double getDurationFor(LightState state) {
        if (state instanceof GreenState) {
            return waitingCars > 2 ? 10 : 6;
        }
        if (state instanceof YellowState) return 3;
        return 6;
    }
}
