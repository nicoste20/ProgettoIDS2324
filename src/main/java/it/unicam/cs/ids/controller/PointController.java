package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.Point2DRepository;
import it.unicam.cs.ids.controller.Repository.PointRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.point.GreenZone;
import it.unicam.cs.ids.model.point.Monument;
import it.unicam.cs.ids.model.point.Restaurant;
import it.unicam.cs.ids.model.point.Square;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import it.unicam.cs.ids.util.Point2D;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The PointController class handles the logic for managing Point objects, including addition,
 * validation, and searching operations.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/points")
public class PointController {
    private final PointRepository points;
    private final Point2DRepository points2D;
    private final UserRepository users;

    /**
     * Constructs a new PointController with the specified repositories.
     * @param points    The repository for Point objects.
     * @param points2D  The repository for Point2D objects.
     * @param users     The repository for User objects.
     */
    @Autowired
    public PointController(PointRepository points, Point2DRepository points2D, UserRepository users) {
        this.points = points;
        this.points2D = points2D;
        this.users = users;
    }

    /**
     * Adds a restaurant point.
     * @param point  The restaurant point to add.
     * @param userId The ID of the user performing the action.
     */
    @PostMapping("/addRestaurant{userId}")
    public ResponseEntity<?> addPointRestaurant(@RequestBody Restaurant point, @PathParam(("userId")) int userId) {
        return addPoint(point,userId);
    }

    /**
     * Adds a monument point.
     * @param point  The monument point to add.
     * @param userId The ID of the user performing the action.
     */
    @PostMapping("/addMonument{userId}")
    public ResponseEntity<?> addPointMonument(@RequestBody Monument point, @PathParam(("userId")) int userId) {
        return addPoint(point,userId);
    }

    /**
     * Adds a green zone point.
     * @param point  The green zone point to add.
     * @param userId The ID of the user performing the action.
     */
    @PostMapping("/addGreenZone{userId}")
    public ResponseEntity<?> addPointGreenZone(@RequestBody GreenZone point, @PathParam(("userId")) int userId) {
        return addPoint(point, userId);
    }

    /**
     * Adds a square point.
     * @param point  The square point to add.
     * @param userId The ID of the user performing the action.
     */
    @PostMapping("/addSquare{userId}")
    public ResponseEntity<?> addPointSquare(@RequestBody Square point, @PathParam(("userId")) int userId) {
        return addPoint(point, userId);
    }

    /**
     * Private method to add a point
     * @param point the point to add
     * @param userId the id of the user who created the point
     * @return A response entity indicating the success of the operation.
     */
    private ResponseEntity<?> addPoint(Point point,  int userId) {
        if (points.isAlreadyIn(point.getX(), point.getY()) == 0 && points.findAllByTitle(point.getName()) == null) {
            points2D.save(new Point2D(point.getX(), point.getY()));
            BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
                point.setAuthor(userId);
                if(user.getUserType().equals(UserRole.Contributor))
                    this.addWithPending(point);
                else
                    this.addWithoutPending(point);
                return new ResponseEntity<>("Point created", HttpStatus.OK);
            } else throw new PointAlreadyInException();
    }

    /**
     * Private method to add a point with pending validation
     */
    private void addWithPending(Point point) {
        point.setValidation(false);
        this.points.save(point);
    }

    /**
     *  Private method to add a point without pending validation
     */
    private void addWithoutPending(Point point) {
        point.setValidation(true);
        this.points.save(point);
    }
    /**
     * Validates or deletes a point based on the curator's choice.
     * @param choice  True to validate, false to delete.
     * @param userId  The ID of the curator performing the action.
     * @param pointId The ID of the point to validate or delete.
     * @return A response entity indicating the success of the operation.
     */
    @RequestMapping(value = "/validate{choice}{userId}{pointId}", method = RequestMethod.PUT)
    public ResponseEntity<?> validatePoint(@PathParam("choice") boolean choice, @PathParam("userId") int userId, @PathParam("pointId") int pointId) {
        if (users.findById(userId).orElseThrow(UserNotExistException::new).getUserType().equals(UserRole.Curator)) {
            Point point = points.findById(pointId).orElseThrow(PointAlreadyInException::new);
                if (choice) {
                    point.setValidation(true);
                    points.save(point);
                    return new ResponseEntity<>("Point Validated", HttpStatus.OK);
                } else {
                    points2D.deleteByCoordinate(point.getX(),point.getY());
                    this.points.deleteById(pointId);
                    return new ResponseEntity<>("Point Deleted", HttpStatus.OK);
                }
            }else throw new UserBadTypeException();
    }

    /**
     * Searches for a point by its title.
     * @param title The title of the point to search for.
     * @return An optional containing the found point, if any.
     */
    @RequestMapping(value = "/search{title}" , method = RequestMethod.GET)
    public ResponseEntity<?> searchPoint(@PathParam("title") String title) {
        Optional<Integer> id = Optional.of(points.findAllByTitle(title));
        return new ResponseEntity<>(points.findById(id.get()),HttpStatus.OK);
    }

    /**
     * Retrieves all points.
     * @return A response entity containing all points.
     */
    @GetMapping(value ="/getAll")
    public ResponseEntity<?> getPoints(){
        return new ResponseEntity<>(points.findAll(),HttpStatus.OK);
    }

    /**
     * Retrieves all authorized points.
     * @return A response entity containing all authorized points.
     */
    @GetMapping(value ="/getAllAuthorized")
    public ResponseEntity<?> getAuthorizedPoints(){
        return new ResponseEntity<>(points.findAll().stream().filter(Point::isValidate),HttpStatus.OK);
    }

    /**
     * Requests to delete a point.
     *
     * @param id     The ID of the point to be deleted.
     * @param userId The ID of the user performing the deletion.
     * @return A ResponseEntity indicating the result of the deletion operation.
     */
    @GetMapping(value = "/delete{id}{userId}")
    public ResponseEntity<?> pointDelete(@PathParam("id") int id, @PathParam("userId") int userId){
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        Point point = points.findById(id).orElseThrow(PointNotExistException::new);
        if(user.getUserType().equals(UserRole.Curator) || point.getAuthor() == userId){
            points.delete(point);
        }
        return new ResponseEntity<>("Point Deleted", HttpStatus.OK);
    }

    /**
     * Request to clone a point
     * @param id the id of the point we want to clone
     * @return the clone point
     */
    @GetMapping(value="/clone{id}")
    public Point getCloneById(@PathParam("id") int id) {
        Point originalPoint = points.getById(id);
        return originalPoint.clone();
    }
}

