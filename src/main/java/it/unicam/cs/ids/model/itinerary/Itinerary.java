package it.unicam.cs.ids.model.itinerary;

import it.unicam.cs.ids.model.point.IPoint;
import java.util.List;

public interface Itinerary {

    List<IPoint> getPoints();

    void addPoint(IPoint point);

    String getDescription();

    void setDescription(String description);

    String toString();
}
