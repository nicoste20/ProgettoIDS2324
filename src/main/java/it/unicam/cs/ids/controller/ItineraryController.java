package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.PointNotExistException;
import it.unicam.cs.ids.Exception.UserBadTypeException;
import it.unicam.cs.ids.Exception.UserNotExistException;
import it.unicam.cs.ids.controller.Repository.ItineraryRepository;
import it.unicam.cs.ids.controller.Repository.PointRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * The Itinerary controller class manages the addition and validation of itinerary,
 * differentiating between immediate addition and pending approval based on the user's role.
 */
@RestController
@RequestMapping("/itineraries")
public class ItineraryController {

    private final ItineraryRepository itineraryRepository;

    private final PointRepository pointRepository;

    private final UserRepository users;

    /**
     * Constructs a new ItineraryController with the specified repositories.
     *
     * @param itinerariesList The repository for Itinerary objects.
     * @param pointRepository
     * @param users           The repository for User objects.
     */
    @Autowired
    public ItineraryController(ItineraryRepository itinerariesList, PointRepository pointRepository, UserRepository users) {
        this.itineraryRepository = itinerariesList;
        this.pointRepository = pointRepository;
        this.users = users;
    }

    /**
     * Adds a new itinerary.
     * @param itinerary The itinerary to add.
     * @param userId    The ID of the user performing the action.
     * @return A response entity indicating the success of the operation.
     */
    @PostMapping("/add{userId}")
    public ResponseEntity<String> addItinerary(@RequestBody Itinerary itinerary ,@PathParam("userId") int userId) {
        if(users.existsById(userId)){
            IUserPlatform user = users.findById(userId).get();
            if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.Animator))) {
                this.pointExistControl(itinerary);
                itinerary.setAuthor(userId);
                if (user.getUserType().equals(UserRole.Contributor))
                    this.addWithPending(itinerary);
                else
                    this.addWithoutPending(itinerary);
                return new ResponseEntity<>("Itinerary created", HttpStatus.OK);
            }else throw new UserBadTypeException();
        }else throw new UserNotExistException();
    }

    private void pointExistControl(Itinerary itinerary){
        for (Integer point: itinerary.getPoints()) {
            if(!pointRepository.existsById(point))
                throw new PointNotExistException();
        }
    }

    /**
     * Adds an Itinerary with the pending
     * @param itinerary The itinerary to be added
     */
    private void addWithPending(Itinerary itinerary){
        itinerary.setValidation(false);
        this.itineraryRepository.save(itinerary);
    }

    /**
     * Adds an Itinerary without the pending
     * @param itinerary The itinerary to be added
     */
    private void addWithoutPending(Itinerary itinerary){
        itinerary.setValidation(true);
        this.itineraryRepository.save(itinerary);
    }

    /**
     * Validates or deletes an itinerary based on the curator's choice.
     * @param id     The ID of the itinerary to validate or delete.
     * @param userId The ID of the curator performing the action.
     * @param choice True to validate, false to delete.
     * @return A response entity indicating the success of the operation.
     */
    @PostMapping("/validate{id}{userId}")
    public ResponseEntity<Object> validateItinerary(@PathParam(("userId")) int userId, @PathParam(("id")) int id, @RequestBody boolean choice) {
        if (users.existsById(userId)) {
            if(users.findById(id).get().equals(UserRole.Curator)){
                if (this.itineraryRepository.existsById(id)) {
                    if (choice) {
                        Itinerary x =  itineraryRepository.findById(id).get();
                        x.setValidation(true);
                        itineraryRepository.save(x);
                        return new ResponseEntity<>("Itinerary validated", HttpStatus.OK);
                    }else{
                        this.itineraryRepository.deleteById(id);
                        return new ResponseEntity<>("Itinerary deleted", HttpStatus.OK);
                    }
                }else throw new RuntimeException("Itinerary doesn't exist");
            }
        }else throw new RuntimeException("Users doesn't exist");
        return null;
    }

    /**
     * It returns how many itineraries are published
     */
    public long getItinerariesSize(){
        return StreamSupport.stream(itineraryRepository.findAll().spliterator(), true)
                .filter(itinerary -> !itinerary.isValidate())
                .count();
    }

    /**
     * It returns the itineraries that are in a pending situation
     */
    public List<Itinerary> getPendingItineraries(){
        return StreamSupport.stream(itineraryRepository.findAll().spliterator(), false)
                .filter(itinerary -> !itinerary.isValidate()).toList();
    }

    /**
     * Searches for an itinerary by its title.
     * @param title The title of the itinerary to search for.
     * @return An optional containing the found itinerary, if any.
     */
    @PostMapping("/search{title}")
    public Optional<Itinerary> searchPoint(@PathParam(("title"))String title) {
        return itineraryRepository.findByDescription(title);
    }

    /**
     * Retrieves all itineraries.
     * @return A response entity containing all itineraries.
     */
    @GetMapping(value ="/getAll/Points")
    public ResponseEntity<Object> getItinerariesWithPoints(){
        Map<Integer, List<Point>> result = new HashMap<>();
        for (Itinerary itinerary : itineraryRepository.findAllItineraries()) {
            List<Point> points = new ArrayList<>();
            for (Integer id : itinerary.getPoints()) {
                pointRepository.findById(id).ifPresent(points::add);
            }
            result.put(itinerary.getId(), points);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/getAll")
    public ResponseEntity<Object> getItineraries(){
        return new ResponseEntity<>(itineraryRepository.findAllItineraries(), HttpStatus.OK);
    }

    @GetMapping("/get/itinerary{id}")
    public ResponseEntity<Object> getItinerary(@PathParam("id") int id){
        List<Point> points = new ArrayList<>();
        if(this.itineraryRepository.findById(id).isPresent()){
            for (Integer pointId : this.itineraryRepository.findById(id).get().getPoints()) {
                pointRepository.findById(pointId).ifPresent(points::add);
            }
        }
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

}
