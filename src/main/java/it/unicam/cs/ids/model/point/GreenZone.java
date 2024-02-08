package it.unicam.cs.ids.model.point;

import jakarta.persistence.Entity;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;

@Entity
public class GreenZone extends Point {
    private String characteristics;

    public GreenZone(Point2D point, BaseUser author, String text, int id, String title, String characteristics) {
        super(point, "Green Zone", author, text, id, title);
        this.characteristics = characteristics;
    }

    public GreenZone() {
    }

    public String getCharacteristics() {
        return characteristics;
    }

    @Override
    public GreenZone clone() {
        return new GreenZone(this.getCoordinates(), super.getAuthor(), super.getDescription(), this.getId(), this.getTitle(), this.getCharacteristics());
    }
}
