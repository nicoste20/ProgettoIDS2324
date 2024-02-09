package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Square extends Point {
    @Column(name="history")
    private String history;

    public Square(Point2D point, BaseUser author, String text, int id, String title, String history) {
        super(point, "Square", author, text, id, title);
        this.history = history;
    }

    public Square() {
    }

    public String getHistory() {
        return history;
    }

    @Override
    public Square clone() {
        return new Square(this.getCoordinates(), super.getAuthor(), super.getDescription()
                , this.getId(), this.getTitle(), this.getHistory());
    }
}
