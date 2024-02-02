package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.Itinerary;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.ArrayList;
import java.util.List;

public class ItineraryController {

    List<Itinerary> itineraries;
    List<Itinerary> pendingItinerary;


    public ItineraryController() {
        this.itineraries = new ArrayList<Itinerary>();
        this.pendingItinerary = new ArrayList<Itinerary>();
    }

    public void addItinerary(Itinerary itinerary , IUserPlatform user) {
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.Animator))) {
            if (user.getUserType().equals(UserRole.Contributor))
                this.addWithPending(itinerary);
            else
                this.addWithoutPending(itinerary);
        }
    }
    private void addWithPending(Itinerary itinerary){
        itinerary.setValidation(false);
        this.pendingItinerary.add(itinerary);
    }

    private void addWithoutPending(Itinerary itinerary){
        itinerary.setValidation(true);
        this.itineraries.add(itinerary);
    }

    public void validateItinerary(BaseUser curator, boolean choice, Itinerary itinerary) {
        if (curator.getUserType().equals(UserRole.Curator)) {
            int index = this.pendingItinerary.indexOf(itinerary);
            if (index != -1) {
                if (choice) {
                    //   pendingItinerary.setValidation(true);
                    itineraries.add(itinerary);
                    this.pendingItinerary.remove(itinerary);
                } else
                    this.pendingItinerary.remove(itinerary);
            }
        }
    }

    public int getItinerariesSize(){
        return this.itineraries.size();
    }
    public int getPendingItinerariesSize(){
        return this.pendingItinerary.size();
    }
}
