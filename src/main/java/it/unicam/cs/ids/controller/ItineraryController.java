package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.Itinerary;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import java.util.List;

public class ItineraryController {

    List<Itinerary> itineraries;

    List<Itinerary> pendingItinerary;

    public void addItinerary(Itinerary itinerary , IUserPlatform user){
        if(user.getUserType().equals(UserRole.Contributor))
            this.addWithPending(itinerary);
        else
            this.addWithoutPending(itinerary);
    }

    private void addWithPending(Itinerary itinerary){
        itinerary.setValidation(false);
        this.pendingItinerary.add(itinerary);
    }

    private void addWithoutPending(Itinerary itinerary){
        itinerary.setValidation(true);
        this.itineraries.add(itinerary);
    }

    public void validateItinerary(boolean choice, IUserPlatform user, Itinerary itinerary){
        int index = this.itineraries.indexOf(itinerary);
        Itinerary itineraryToValidate = this.itineraries.get(index);
        if(choice)
            itineraryToValidate.setValidation(true);
        else
            this.itineraries.remove(itineraryToValidate);
    }

}
