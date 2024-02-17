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
 * validation, and searching operations. It leverages the IUserPlatform interface and UserRole
 * enumeration to determine user roles and permissions.
 */
@RestController
public class PointController {
    /**
     * List to store Point objects managed by the controller.
     */
    private final PointRepository points;
    private final Point2DRepository points2D;

    private final UserRepository users;

    @Autowired
    public PointController(PointRepository points, Point2DRepository points2D, UserRepository users) {
        this.points = points;
        this.points2D = points2D;
        this.users = users;
    }

    /**
     * Adds a Point to the list based on the user's role.
     *
     * @param point  The Point to be added.
     * @param userId The user performing the operation.
     */

    //TODO: quando costruisci oggetto da grafica mettere il tipo !!
    @PostMapping("/addRestaurant/point{userId}")
    public void addPointRestaurant(@RequestBody Restaurant point, @PathParam(("userId")) int userId) {
        addPoint(point,userId);
    }

    @PostMapping("/addMonument/point{userId}")
    public void addPointMonument(@RequestBody Monument point, @PathParam(("userId")) int userId) {
        addPoint(point,userId);
    }
    @PostMapping("/addGreenZone/point{userId}")
    public void addPointGreenZone(@RequestBody GreenZone point, @PathParam(("userId")) int userId) {
        addPoint(point, userId);
    }

    @PostMapping("/addSquare/point{userId}")
    public void addPointSquare(@RequestBody Square point, @PathParam(("userId")) int userId) {
        addPoint(point, userId);
    }

    private ResponseEntity<Object> addPoint(Point point,  int userId) {
        if (points.isAlreadyIn(point.getX(), point.getY()) == 0) {
            points2D.save(new Point2D(point.getX(), point.getY()));
            if (users.existsById(userId)) {
                //int user = users.findById(userId).get();
                point.setAuthor(userId);
                if (users.findById(userId).get().getUserType().equals(UserRole.Contributor)) {
                    this.addWithPending(point);
                    return new ResponseEntity<>("Point created", HttpStatus.OK);
                } else {
                    this.addWithoutPending(point);
                    return new ResponseEntity<>("Point created", HttpStatus.OK);
                }
            } else throw new UserBadTypeException();
        }else throw new PointAlreadyInException();
    }

    /**
     * Adds a Point with pending validation to the list.
     *
     * @param point The Point to be added.
     */
    private void addWithPending(Point point) {
        point.setValidation(false);
        this.points.save(point);
    }

    /**
     * Adds a Point without pending validation to the list.
     *
     * @param point The Point to be added.
     */
    private void addWithoutPending(Point point) {
        point.setValidation(true);
        this.points.save(point);
    }

    /**
     * Validates or removes a Point based on the user's choice.
     *
     * @param choice The user's choice for validation.
     * @param userId   The user performing the validation.
     * @param pointId  The Point to be validated or removed.
     */
    @RequestMapping(value = "/validate/point{choice}{userId}{pointId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> validatePoint(@PathParam("choice") boolean choice, @PathParam("userId") int userId, @PathParam("pointId") int pointId) {
        if (users.findById(userId).get().getUserType().equals(UserRole.Curator)) {
            if (points.existsById(pointId)) {
                if (choice) {
                    Point x =  points.findById(pointId).get();
                    x.setValidation(true);
                    points.save(x);
                    return new ResponseEntity<>("Point Validated", HttpStatus.OK);
                } else {
                    points2D.deleteByCoordinate(points.findById(pointId).get().getX(),points.findById(pointId).get().getY());
                    this.points.deleteById(pointId);
                    return new ResponseEntity<>("Point Deleted", HttpStatus.OK);
                }
            }else throw new PointNotExistException();
        }else throw new UserBadTypeException();
    }

    /**
     * Searches for a Point by its title in the list.
     *
     * @param title The title of the Point to be searched.
     * @return An Optional containing the found Point, or empty if not found.
     */
    @RequestMapping(value = "/search/point{title}" , method = RequestMethod.PUT)
    public Optional<Point> searchPoint(@PathParam("title") String title) {
        return Optional.of(points.findAllByTitle(title));
    }

    @GetMapping(value ="get/points")
    public ResponseEntity<Object> getPoints(){
        return new ResponseEntity<>(points.findAll(),HttpStatus.OK);
    }
}

