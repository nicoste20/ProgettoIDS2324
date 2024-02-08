package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.Entity;

@Entity
public class Restaurant extends Point {
    private String type;
    private String openingHours;

    public Restaurant(Point2D point, BaseUser author, String text, int id, String title, String type, String openingHours) {
        super(point, "Restaurant", author, text, id, title);
        this.type = type;
        this.openingHours = openingHours;
    }

    public Restaurant() {
    }

    public String getType() {
        return type;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    @Override
    public Restaurant clone() {
        return new Restaurant(this.getCoordinates(), super.getAuthor(), super.getDescription(), this.getId(), this.getTitle(), this.getType(), this.getOpeningHours());
    }
}

