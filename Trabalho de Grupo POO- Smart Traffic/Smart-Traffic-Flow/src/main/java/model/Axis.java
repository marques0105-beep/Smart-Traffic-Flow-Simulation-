package model;

public enum Axis {
    NORTH_SOUTH,
    SOUTH_NORTH,
    EAST_WEST,
    WEST_EAST;

    public boolean isVertical() {
        return this == NORTH_SOUTH || this == SOUTH_NORTH;
    }

    public boolean isHorizontal() {
        return this == EAST_WEST || this == WEST_EAST;
    }
}
