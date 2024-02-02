package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.ArrayList;
import java.util.List;
/**
 * The  Itinerary controller class manages the addition and validation of itinerary,
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link BaseUser}, {@link UserRole}, {@link IUserPlatform}
 */
public class ItineraryController {

    List<Itinerary> itineraries;

    public ItineraryController() {
        this.itineraries = new ArrayList<Itinerary>();
    }
    /**
     * Adds an Itinerary to the list based on the user's role.
     *
     * @param itinerary The Itinerary to be added.
     * @param user  The user performing the operation.
     */

    public void addItinerary(Itinerary itinerary , IUserPlatform user) {
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.Animator))) {
            if (user.getUserType().equals(UserRole.Contributor))
                this.addWithPending(itinerary);
            else
                this.addWithoutPending(itinerary);
        }
    }
    /**
     * Adds an Itinerary with the pending
     *
     * @param itinerary The itinerary to be added
     */
    private void addWithPending(Itinerary itinerary){
        itinerary.setValidation(false);
        this.itineraries.add(itinerary);
    }
    /**
     * Adds an Itinerary without the pending
     *
     * @param itinerary The itinerary to be added
     */
    private void addWithoutPending(Itinerary itinerary){
        itinerary.setValidation(true);
        this.itineraries.add(itinerary);
    }
    /**
     *Validates an itinerary
     *
     * @param itinerary The itinerary to be validated
     * @param curator the User that validates the itinerary
     * @param choice if the itinerary will be validated or no
     */
    public void validateItinerary(IUserPlatform curator, boolean choice, Itinerary itinerary) {
        if (curator.getUserType().equals(UserRole.Curator)) {
            int index = this.itineraries.indexOf(itinerary);
            if (index != -1) {
                if (choice) {
                    itineraries.get(index).setValidation(true);
                } else
                    this.itineraries.remove(index);
            }
        }
    }
    /**
     * It returns how many itineraries are published
     */
    public int getItinerariesSize(){
        return this.itineraries.size();
    }
    /**
     * It returns how many itineraries are in a pending situation
     */
    public int getPendingItinerariesSize(){
        return 0;
    }
}
