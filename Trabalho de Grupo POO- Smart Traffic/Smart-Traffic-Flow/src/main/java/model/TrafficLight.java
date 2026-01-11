package model;

import model.state.LightState;
import model.state.RedState;
import controller.Strategy;

public class TrafficLight {

    private LightState state;
    private double timer;
    private Strategy strategy;
    private boolean greenFinished = false;

    public TrafficLight(Strategy strategy) {
        this.strategy = strategy;
        this.state = new RedState();
        this.timer = strategy.getRedDuration();
    }

    public void update(double dt) {
        timer -= dt;
        state.update(this, dt);
    }

    public void changeState(LightState newState) {
        state = newState;
        timer = strategy.getDurationFor(state);
    }

    public boolean isGreen() {
        return state.canPass();
    }

    public void markGreenFinished() {
        greenFinished = true;
    }

    public boolean wasGreenBefore() {
        boolean temp = greenFinished;
        greenFinished = false;
        return temp;
    }

    public boolean timerExpired() {
        return timer <= 0;
    }

    public String getStateName() {
        return state.getName();
    }
}
