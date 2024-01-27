package it.unicam.cs.ids.model.itinerary;

import it.unicam.cs.ids.model.point.IPoint;

import java.util.ArrayList;
import java.util.List;

public class DefaultItinerary implements Itinerary{

    private List<IPoint> points;

    private String description;

    public DefaultItinerary(){
        this.points = new ArrayList<>();
    }

    public DefaultItinerary(List<IPoint> points){
        this.points = points;
    }

    @Override
    public List<IPoint> getPoints() {
        return this.points;
    }

    @Override
    public void addPoint(IPoint point) {
        this.points.add(point);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
