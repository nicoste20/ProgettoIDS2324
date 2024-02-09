package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Monument extends Point {
    private Date inaugurationDate;
    private String story;

    public Monument(Point2D point, BaseUser author, String text, int id, String title, Date inaugurationDate, String story) {
        super(point, "Monument", author, text, id, title);
        this.inaugurationDate = inaugurationDate;
        this.story = story;
    }

    public Monument() {
    }

    public Monument(Point2D point2D, BaseUser contributorAuthorized, String monument, String testPoint, int i, String colosseum, int i1, String antiqueMonument) {
    }

    public Date getInaugurationDate() {
        return inaugurationDate;
    }

    public String getStory() {
        return story;
    }

    @Override
    public Monument clone() {
        return new Monument(this.getCoordinates(), super.getAuthor(), super.getDescription(),
                this.getId(), this.getTitle(), this.getInaugurationDate(), this.getStory());
    }
}
