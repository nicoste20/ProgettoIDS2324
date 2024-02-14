package it.unicam.cs.ids.util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Class representing a two-dimensional point with x and y coordinates.
 */
@Entity
public class Point2D {

    // x and y coordinates of the point
    private double x;
    private double y;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Constructor for the Point2D class.
     *
     * @param x X-coordinate of the point.
     * @param y Y-coordinate of the point.
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D() {
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return X-coordinate of the point.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the point.
     *
     * @param x New x-coordinate to assign to the point.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the point.
     *
     * @return Y-coordinate of the point.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the point.
     *
     * @param y New y-coordinate to assign to the point.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calculates the Euclidean distance between the current point and a specified point.
     *
     * @param target Destination point to calculate the distance.
     * @return Euclidean distance between the current point and the specified point.
     */
    public double distance(Point2D target) {
        double deltaX = this.x - target.getX();
        double deltaY = this.y - target.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

