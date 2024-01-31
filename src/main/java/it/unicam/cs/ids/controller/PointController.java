package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.Point;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import java.util.List;

public class PointController {

    List<Point> points;

    public void addPoint(Point point , IUserPlatform user){
        if(user.getUserType().equals(UserRole.Contributor))
            this.addWithPending(point);
        else
            this.addWithoutPending(point);
    }

    private void addWithPending(Point point){
        point.setValidation(false);
        this.points.add(point);
    }

    private void addWithoutPending(Point point){
        point.setValidation(true);
        this.points.add(point);
    }

    public void validatePoint(boolean choice, Point point){
       int index = this.points.indexOf(point);
       Point pointToValidate = this.points.get(index);
       if(choice)
           pointToValidate.setValidation(true);
       else
           this.points.remove(pointToValidate);
    }

}
