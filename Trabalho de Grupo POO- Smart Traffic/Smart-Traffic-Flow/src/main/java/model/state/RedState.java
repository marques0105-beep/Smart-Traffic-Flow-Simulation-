package model.state;

import model.TrafficLight;

public class RedState implements LightState {

    @Override
    public void update(TrafficLight light, double dt) {
        if (light.timerExpired()) {
            light.changeState(new YellowState());
        }
    }


    @Override
    public boolean canPass() {
        return false;
    }

    @Override
    public String getName() {
        return "RED";
    }
}
