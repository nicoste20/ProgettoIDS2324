package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Monument extends Point {
    @Column(name="inaugurationDate")
    private String inaugurationDate;
    @Column(name="story")
    private String story;

    public Monument(Point2D point, BaseUser author, String name, int id, String inaugurationDate, String story) {
        super(point, "Monument",author, name, id);
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
                this.getId(), this.getInaugurationDate(), this.getStory());
    }
}
