package model;

import javafx.scene.paint.Color;
import java.util.Random;

public class Vehicle {

    protected double position;
    protected double speed;
    protected boolean moving;
    protected Axis axis;
    protected Color color;

    public Vehicle(double initialPosition, Axis axis) {
        this.position = initialPosition;
        this.axis = axis;
        this.speed = 30.0;
        this.moving = false; // começa parado

        Color[] palette = {
                Color.DODGERBLUE,
                Color.ORANGE,
                Color.MEDIUMSEAGREEN,
                Color.MEDIUMPURPLE
        };
        this.color = palette[new Random().nextInt(palette.length)];
    }

    public Axis getAxis() { return axis; }
    public double getPosition() { return position; }
    public boolean isMoving() { return moving; }
    public Color getColor() { return color; }

    public void go() { moving = true; }
    public void stop() { moving = false; }

    // =========================
    // MOVIMENTO CORRETO
    // =========================
    public void update(double dt) {
        if (!moving) return;

        switch (axis) {
            case NORTH_SOUTH -> position += speed * dt;
            case SOUTH_NORTH -> position -= speed * dt;
            case EAST_WEST   -> position += speed * dt;
            case WEST_EAST   -> position -= speed * dt;
        }

        if (position > 800) position = 0;
        if (position < 0) position = 800;
    }

    // =========================
    // DESENHO (LANES)
    // =========================
    public double getX() {
        return switch (axis) {
            case NORTH_SOUTH -> 365;
            case SOUTH_NORTH -> 410;
            case EAST_WEST, WEST_EAST -> position;
        };
    }

    public double getY() {
        return switch (axis) {
            case EAST_WEST -> 220;
            case WEST_EAST -> 160;
            case NORTH_SOUTH, SOUTH_NORTH -> position;
        };
    }
}
