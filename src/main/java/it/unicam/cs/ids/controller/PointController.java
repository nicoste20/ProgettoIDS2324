package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.point.IPoint;
import it.unicam.cs.ids.model.point.Point;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.List;

public class PointController {

    List<IPoint> points;

    List<IPoint> pendingPoints;

    public void addPoint(Point point , IUserPlatform user){
        if(user.getUserType().equals(UserRole.Contributor))
            this.addWithPending(point);
        else
            this.addWithoutPending(point);
    }

    private void addWithPending(Point point){
        this.pendingPoints.add(point);
    }

    private void addWithoutPending(Point point){
        this.points.add(point);
    }

    public void validatePoint(boolean choice, IUserPlatform user, IPoint point){
        if(choice)
            this.points.add(point);
        else
            this.pendingPoints.remove(point);
    }

}
