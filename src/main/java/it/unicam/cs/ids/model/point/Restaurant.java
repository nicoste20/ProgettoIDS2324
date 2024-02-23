package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Represents a restaurant, which is a type of point on a map.
 * It extends the Point class and includes additional fields to store the type of restaurant and its opening hours.
 */
@Entity
public class Restaurant extends Point {
    @Enumerated(EnumType.STRING)
    private RestaurantType typeRestaurant;
    @Column(name="openingHours")
    private String openingHours;

    /**
     * Constructs a Restaurant object with the specified coordinates, type, name, restaurant type, and opening hours.
     * @param x the x-coordinate of the restaurant
     * @param y the y-coordinate of the restaurant
     * @param typeR the type of the restaurant (inherited from superclass)
     * @param name the name of the restaurant (inherited from superclass)
     * @param type the type of restaurant
     * @param openingHours the opening hours of the restaurant
     */
    public Restaurant(Float x, Float y,String typeR, String name, String type, String openingHours) {
        super(x, y, typeR, name);
        this.typeRestaurant = RestaurantType.valueOf(RestaurantType.class,type);
        this.openingHours = openingHours;
    }

    /**
     * Default constructor for Restaurant class.
     */
    public Restaurant() {
    }

    /**
     * Retrieves the type of restaurant.
     * @return the type of restaurant
     */
    public RestaurantType getTypeRestaurant() {
        return typeRestaurant;
    }

    /**
     * Retrieves the opening hours of the restaurant.
     * @return the opening hours of the restaurant
     */
    public String getOpeningHours() {
        return openingHours;
    }

    /**
     * Creates and returns a copy of this Restaurant object.
     * @return a clone of this Restaurant object
     */
    @Override
    public Restaurant clone() {
        return new Restaurant(this.getX(), this.getY(), super.getName(),super.getType(), this.getTypeRestaurant().toString(), this.getOpeningHours());
    }
}

