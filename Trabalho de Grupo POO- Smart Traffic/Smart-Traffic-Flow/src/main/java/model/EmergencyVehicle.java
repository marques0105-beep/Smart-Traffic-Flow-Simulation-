package model;

import javafx.scene.paint.Color;

public class EmergencyVehicle extends Vehicle {

    public EmergencyVehicle(double initialPosition, Axis axis) {
        super(initialPosition, axis);
        this.speed = 35.0;
        this.color = Color.DARKRED;
        this.moving = false;
    }

    @Override
    public void update(double dt) {
        this.moving = true;
        super.update(dt);
    }
}
