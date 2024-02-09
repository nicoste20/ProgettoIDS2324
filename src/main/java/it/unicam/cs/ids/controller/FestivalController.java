package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.content.Festival;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.Date;
import java.util.List;
/**
 * The  Festival controller class manages the addition and removing of Festivals,
 * It interacts with instances of {@link UserRole}, {@link IUserPlatform}
 */

public class FestivalController {
    List<Festival> festivals;
    boolean alreadyIn=false;
    /**
     * Adds a Festival to the list
     *
     * @param newfestival The Festival to be added.
     */
    public void addFestival(Festival newfestival){
        if(newfestival.getEnd().after(new Date())){
            for (Festival festival : festivals){
                if(newfestival.equals(festival))
                {
                    alreadyIn=true;
                }
                if(!alreadyIn)  this.festivals.add(festival);
        }
        }
    }
    /**
     * Remove a specific Festival searched by the title
     *
     * @param title the festival's title
     */
    public void removeFestival(String title){
        festivals.removeIf(festival -> festival.getDescription().equals(title));
    }

    /**
     * Gets if the festival is active
     * @return if the Festival is active
     */
    public boolean isActive(Festival festival){
        return festival.getEnd().after(new Date());
    }

}
