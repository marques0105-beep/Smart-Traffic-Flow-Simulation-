package model.state;

import model.TrafficLight;

public class GreenState implements LightState {

    @Override
    public void update(TrafficLight light, double dt) {
        if (light.timerExpired()) {
            light.markGreenFinished();
            light.changeState(new YellowState());
        }
    }


    @Override
    public boolean canPass() {
        return true;
    }

    @Override
    public String getName() {
        return "GREEN";
    }
}
