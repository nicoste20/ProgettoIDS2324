package it.unicam.cs.ids.model.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import it.unicam.cs.ids.model.content.Point;

/**
 * This class represents a green zone, which is a type of point on a map with specific characteristics.
 * It extends the Point class and adds a field to store characteristics specific to green zones.
 */
@Entity
public class GreenZone extends Point {
    @Column(name="characteristics")
    private String characteristics;

    /**
     * Constructs a GreenZone object with the specified coordinates, type, name, and characteristics.
     * @param x the x-coordinate of the green zone
     * @param y the y-coordinate of the green zone
     * @param type the type of the green zone
     * @param name the name of the green zone
     * @param characteristics the characteristics of the green zone
     */
    public GreenZone(Float x, Float y, String type,String name, String characteristics) {
        super(x , y, type, name);
        this.characteristics = characteristics;
    }

    /**
     * Default constructor for GreenZone class.
     */
    public GreenZone() {
    }

    /**
     * Retrieves the characteristics of the green zone.
     * @return the characteristics of the green zone
     */
    public String getCharacteristics() {
        return characteristics;
    }

    /**
     * Creates and returns a copy of this GreenZone object.
     * @return a clone of this GreenZone object
     */
    @Override
    public GreenZone clone() {
        return new GreenZone(this.getX(), this.getY(), super.getType(),super.getName(), this.getCharacteristics());
    }
}
