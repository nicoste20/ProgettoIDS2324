package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Restaurant extends Point {
    @Enumerated(EnumType.STRING)
    private RestaurantType typeRestaurant;
    @Column(name="openingHours")
    private String openingHours;

    public Restaurant(Point2D point, BaseUser author, String name, int id, RestaurantType type, String openingHours) {
        super(point, "Restaurant", author, name, id);
        this.typeRestaurant = type;
        this.openingHours = openingHours;
    }

    public Restaurant() {
    }

    public RestaurantType getTypeRestaurant() {
        return typeRestaurant;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    @Override
    public Restaurant clone() {
        return new Restaurant(this.getCoordinates(), super.getAuthor(), super.getDescription(), this.getId(), this.getTypeRestaurant(), this.getOpeningHours());
    }
}

