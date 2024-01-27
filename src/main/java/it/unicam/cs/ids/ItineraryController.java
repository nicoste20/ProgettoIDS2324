package it.unicam.cs.ids;

import it.unicam.cs.ids.model.itinerary.Itinerary;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import java.util.List;

public class ItineraryController {

    List<Itinerary> itinerary;

    List<Itinerary> pendingItinerary;

    public void addItinerary(Itinerary itinerary , IUserPlatform user){
        if(user.getUserType().equals(UserRole.Contributor))
            this.addWithPending(itinerary);
        else
            this.addWithoutPending(itinerary);
    }

    private void addWithPending(Itinerary itinerary){
        this.pendingItinerary.add(itinerary);
    }

    private void addWithoutPending(Itinerary itinerary){
        this.itinerary.add(itinerary);
    }

    public void validateItinerary(boolean choice, IUserPlatform user, Itinerary itinerary){
        if(choice)
            this.itinerary.add(itinerary);
        else
            this.pendingItinerary.remove(itinerary);
    }

}
