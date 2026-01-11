package model.state;

import model.TrafficLight;

public class YellowState implements LightState {

    @Override
    public void update(TrafficLight light, double dt) {
        if (light.timerExpired()) {

            if (light.wasGreenBefore()) {
                light.changeState(new RedState());
            } else {
                light.changeState(new GreenState());
            }
        }
    }


    @Override
    public boolean canPass() {
        return false;
    }

    @Override
    public String getName() {
        return "YELLOW";
    }
}