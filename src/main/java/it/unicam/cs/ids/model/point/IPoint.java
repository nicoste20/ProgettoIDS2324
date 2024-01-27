package it.unicam.cs.ids.model.point;

import java.awt.geom.Point2D;

public interface IPoint {

    Point2D getCoordinates();

    String getDescription();

    void setDescription(String description);

    String getType();


    String toString();

}
