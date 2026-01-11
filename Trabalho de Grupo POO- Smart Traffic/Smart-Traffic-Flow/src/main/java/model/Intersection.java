package model;

import controller.Strategy;

public class Intersection {

    private double position;
    private double crosswalkPosition;

    private TrafficLight nsLight;
    private TrafficLight ewLight;

    private boolean nsActive = true;   // Começa com Norte/Sul verde

    public Intersection(Strategy strategy, double position) {
        this.position = position;
        this.crosswalkPosition = position;

        nsLight = new TrafficLight(strategy);
        ewLight = new TrafficLight(strategy);

        // N/S começa verde, E/W começa vermelho
        nsLight.changeState(new model.state.GreenState());
        ewLight.changeState(new model.state.RedState());
    }

    public void update(double dt) {

        if (nsActive) {
            nsLight.update(dt);

            // Quando o N/S terminar o ciclo verde → troca
            if (nsLight.getStateName().equals("RED")) {
                nsActive = false;
                ewLight.changeState(new model.state.GreenState());
            }

        } else {
            ewLight.update(dt);

            // Quando o E/W terminar o ciclo verde → troca
            if (ewLight.getStateName().equals("RED")) {
                nsActive = true;
                nsLight.changeState(new model.state.GreenState());
            }
        }
    }

    public TrafficLight getNsLight() {
        return nsLight;
    }

    public TrafficLight getEwLight() {
        return ewLight;
    }

    public TrafficLight getTrafficLight() {
        return ewLight;
    }

    public double getPosition() {
        return position;
    }

    public double getCrosswalkPosition() {
        return crosswalkPosition;
    }
}
