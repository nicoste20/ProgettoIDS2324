package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.ItineraryNotExistException;
import it.unicam.cs.ids.Exception.PointNotExistException;
import it.unicam.cs.ids.Exception.UserBadTypeException;
import it.unicam.cs.ids.Exception.UserNotExistException;
import it.unicam.cs.ids.controller.Repository.ItineraryRepository;
import it.unicam.cs.ids.controller.Repository.PointRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * The Itinerary controller class manages the addition and validation of itinerary,
 * differentiating between immediate addition and pending approval based on the user's role.
 */

@CrossOrigin(origins = "http://localhost:63342")
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
    public ResponseEntity<?> addItinerary(@RequestBody Itinerary itinerary ,@PathParam("userId") int userId) {
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.Animator))) {
            this.pointExistControl(itinerary);
            itinerary.setAuthor(userId);

            if (user.getUserType().equals(UserRole.Contributor))
                this.addWithPending(itinerary);
            else
                this.addWithoutPending(itinerary);

            return new ResponseEntity<>("Itinerary created", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    private void pointExistControl(Itinerary itinerary){
        for (Integer pointId: itinerary.getPoints()) {
            if(!pointRepository.existsById(pointId) || !pointRepository.findById(pointId).get().isValidate())
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
    public ResponseEntity<?> validateItinerary(@PathParam(("userId")) int userId, @PathParam(("id")) int id, @RequestBody boolean choice) {
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
            if(user.getUserType().equals(UserRole.Curator)){
                Itinerary itinerary = itineraryRepository.findById(id).orElseThrow(ItineraryNotExistException::new);
                    if (choice) {
                        itinerary.setValidation(true);
                        itineraryRepository.save(itinerary);
                        return new ResponseEntity<>("Itinerary validated", HttpStatus.OK);
                    }
                    this.itineraryRepository.deleteById(id);
                    return new ResponseEntity<>("Itinerary deleted", HttpStatus.OK);
            }
        return new ResponseEntity<>("Itinerary not validated", HttpStatus.OK);
    }

    /**
     * It returns how many itineraries are published
     */
    public long getItinerariesSize(){
        return itineraryRepository.findAll().parallelStream()
                .filter(itinerary -> !itinerary.isValidate())
                .count();
    }

    /**
     * It returns the itineraries that are in a pending situation
     */
    public List<Itinerary> getPendingItineraries(){
        return itineraryRepository.findAll().stream()
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
    public ResponseEntity<?> getItinerariesWithPoints(){
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
    /**
     * Retrieves all the itineraries from the repository.
     *
     * @return ResponseEntity containing a list of itineraries if present in the repository,
     *         otherwise a string "No itineraries found" with HttpStatus OK.
     */
    @GetMapping(value ="/getAll")
    public ResponseEntity<?> getItineraries(){
        if(itineraryRepository.findAllItineraries().isEmpty())
            return new ResponseEntity<>("No itineraries found", HttpStatus.OK);
        return new ResponseEntity<>(itineraryRepository.findAllItineraries(), HttpStatus.OK);
    }

    /**
     * Retrieves an itinerary by its ID.
     *
     * @param id The ID of the itinerary to retrieve.
     * @return ResponseEntity containing a list of points belonging to the itinerary
     *         if the itinerary is found, otherwise an ItineraryNotExistException is thrown.
     */
    @GetMapping("/get/itinerary{id}")
    public ResponseEntity<?> getItinerary(@PathParam("id") int id){
        List<Point> points = new ArrayList<>();
        Itinerary itinerary = itineraryRepository.findById(id).orElseThrow(ItineraryNotExistException::new);
            for (Integer pointId : itinerary.getPoints()) {
                pointRepository.findById(pointId).ifPresent(points::add);
            }
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    /**
     * Deletes an itinerary based on the provided itinerary ID and user ID.
     *
     * @param itineraryId The ID of the itinerary to delete.
     * @param userId      The ID of the user attempting to delete the itinerary.
     * @return ResponseEntity indicating the deletion status, with a success message if
     *         the itinerary is deleted successfully, otherwise an exception is thrown.
     */
    @DeleteMapping("/delete{itineraryId}{userId}")
    public ResponseEntity<?> deleteItinerary(@PathParam("itineraryId") int itineraryId , @PathParam("userId") int userId){
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow(ItineraryNotExistException::new);
            if(user.getId() == itinerary.getAuthor() || user.getUserType().equals(UserRole.Curator))
                itineraryRepository.delete(itinerary);
        return new ResponseEntity<>("Itinerary deleted", HttpStatus.OK);
    }

}
