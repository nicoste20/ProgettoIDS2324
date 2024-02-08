package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.Entity;

@Entity
public class Monument extends Point {
    private String inaugurationDate;
    private String story;

    public Monument(Point2D point, BaseUser author, String text, int id, String title, String inaugurationDate, String story) {
        super(point, "Monument", author, text, id, title);
        this.inaugurationDate = inaugurationDate;
        this.story = story;
    }

    public Monument() {
    }

    public String getInaugurationDate() {
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
