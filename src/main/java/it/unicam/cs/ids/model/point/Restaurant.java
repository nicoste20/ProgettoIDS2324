package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
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

    public Restaurant(Float x, Float y,String typeR, String name, RestaurantType type, String openingHours) {
        super(x, y, typeR, name);
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
        return new Restaurant(this.getX(), this.getY(), super.getName(),super.getType(), this.getTypeRestaurant(), this.getOpeningHours());
    }
}

