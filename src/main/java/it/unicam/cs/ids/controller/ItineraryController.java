package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.Repository.ItineraryRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * The  Itinerary controller class manages the addition and validation of itinerary,
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link BaseUser}, {@link UserRole}, {@link IUserPlatform}
 */
@RestController
public class ItineraryController {

    /**
     * The list of itineraries.
     */
    private final ItineraryRepository itineraries;

    private final UserRepository users;

    @Autowired
    public ItineraryController(ItineraryRepository itinerariesList, UserRepository users) {
        this.itineraries = itinerariesList;
        this.users = users;
    }

    /**
     * Adds an Itinerary to the list based on the user's role.
     *
     * @param itinerary The Itinerary to be added.
     * @param userId      The user performing the operation.
     * @return the response
     */
    @PostMapping("/add/itinerary/{userId}")
    public ResponseEntity<String> addItinerary(@RequestBody Itinerary itinerary ,@PathParam("userId") int userId) {
        if(users.existsById(userId)){
           IUserPlatform user = users.findById(userId).get();
            if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.Animator))) {
                if (user.getUserType().equals(UserRole.Contributor))
                    this.addWithPending(itinerary);
                else
                    this.addWithoutPending(itinerary);
            }
            return new ResponseEntity<>("Itinerary created", HttpStatus.OK);
        }
        return new ResponseEntity<>("Itinerary not created", HttpStatus.NOT_FOUND);
    }

    /**
     * Adds an Itinerary with the pending
     *
     * @param itinerary The itinerary to be added
     */
    private void addWithPending(Itinerary itinerary){
        itinerary.setValidation(false);
        this.itineraries.save(itinerary);
    }
    /**
     * Adds an Itinerary without the pending
     *
     * @param itinerary The itinerary to be added
     */
    private void addWithoutPending(Itinerary itinerary){
        itinerary.setValidation(true);
        this.itineraries.save(itinerary);
    }
    /**
     *Validates an itinerary
     *
     * @param id The itinerary to be validated
     * @param userID if of the User that validates the itinerary
     * @param choice if the itinerary will be validated or no
     */
    @PostMapping("/validate/itinerary/{id}/{userId}")
    public void validateItinerary(@PathParam(("userId")) int userID, @PathParam(("id")) int id, @RequestBody boolean choice) {
        if (users.existsById(userID)) {
            if(users.findById(id).get().equals(UserRole.Curator)){
                if (this.itineraries.existsById(id)) {
                    if (choice) {
                        this.itineraries.findById(id).get().setValidation(true);
                    }else
                        this.itineraries.deleteById(id);
                }else
                    throw new RuntimeException("Itinerary doesn't exist");
            }
        }else
            throw new RuntimeException("Users doesn't exist");
    }

    /**
     * It returns how many itineraries are published
     */
    public long getItinerariesSize(){
        return StreamSupport.stream(itineraries.findAll().spliterator(), true)
                .filter(itinerary -> !itinerary.isValidate())
                .count();
    }

    /**
     * It returns the itineraries that are in a pending situation
     */
    public List<Itinerary> getPendingItineraries(){
        return StreamSupport.stream(itineraries.findAll().spliterator(), false)
                .filter(itinerary -> !itinerary.isValidate()).toList();
    }

    /**
     *It does the research of an itinerary by his title
     * @param title The title of an itinerary
     * @return the Itinerary researched
     */
    @PostMapping("/search/itinerary/{title}")
    public Optional<Itinerary> searchPoint(@PathParam(("title"))String title) {
        return itineraries.findByDescription(title);
    }
    @GetMapping(value ="/get/itinerary")
    public ResponseEntity<Object> getItineraries(){
        return new ResponseEntity<>(itineraries.findAll(), HttpStatus.OK);
    }
}
