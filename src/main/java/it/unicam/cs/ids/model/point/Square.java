package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Square extends Point {
    @Column(name="history")
    private String history;

    public Square(Float x, Float y,String type, String name,String history) {
        super(x , y, type,name);
        this.history = history;
    }

    public Square() {
    }

    public String getHistory() {
        return history;
    }

    @Override
    public Square clone() {
        return new Square(this.getX(), this.getY(), super.getType(),super.getName(), this.history);
    }
}
