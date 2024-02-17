package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Monument extends Point {
    @Column(name="inaugurationDate")
    private Date inaugurationDate;
    @Column(name="story")
    private String story;

    public Monument(Float x, Float y, String type, String name, Date inaugurationDate, String story) {
        super(x, y,type , name);
        this.inaugurationDate = inaugurationDate;
        this.story = story;
    }

    public Monument() {
    }

    public Date getInaugurationDate() {
        return inaugurationDate;
    }

    public String getStory() {
        return story;
    }

    @Override
    public Monument clone() {

        return new Monument(this.getX(), this.getY(),super.getName(), super.getType(),
                this.getInaugurationDate(), this.getStory());

    }
}
