package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import it.unicam.cs.ids.model.content.Point;

@Entity
public class GreenZone extends Point {
    @Column(name="characteristics")
    private String characteristics;


    public GreenZone(Float x, Float y, String type,String name, String characteristics) {
        super(x , y, type, name);
        this.characteristics = characteristics;
    }

    public GreenZone() {
    }

    public String getCharacteristics() {
        return characteristics;
    }

    @Override
    public GreenZone clone() {
        return new GreenZone(this.getX(), this.getY(), super.getType(),super.getName(), this.getCharacteristics());
    }
}
