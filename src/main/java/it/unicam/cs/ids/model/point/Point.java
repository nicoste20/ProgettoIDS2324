package it.unicam.cs.ids.model.point;

import java.awt.geom.Point2D;

public class Point implements IPoint{

    private final String type;

    private String description;

    private Point2D coordinates;

    public Point(Point2D point, String type){
        this.coordinates = point;
        this.type = type;
    }

    @Override
    public Point2D getCoordinates() {
        return this.coordinates;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
