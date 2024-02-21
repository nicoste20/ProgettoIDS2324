package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Represents a square, which is a type of point on a map typically used as a public open space.
 * It extends the Point class and includes an additional field to store historical information about the square.
 */
@Entity
public class Square extends Point {
    @Column(name="history")
    private String history;

    /**
     * Constructs a Square object with the specified coordinates, type, name, and historical information.
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type the type of the square (inherited from superclass)
     * @param name the name of the square (inherited from superclass)
     * @param history the historical information about the square
     */
    public Square(Float x, Float y,String type, String name,String history) {
        super(x , y, type,name);
        this.history = history;
    }

    /**
     * Default constructor for Square class.
     */
    public Square() {
    }

    /**
     * Retrieves the historical information about the square.
     * @return the historical information about the square
     */
    public String getHistory() {
        return history;
    }

    /**
     * Creates and returns a copy of this Square object.
     * @return a clone of this Square object
     */
    @Override
    public Square clone() {
        return new Square(this.getX(), this.getY(), super.getType(),super.getName(), this.history);
    }
}
