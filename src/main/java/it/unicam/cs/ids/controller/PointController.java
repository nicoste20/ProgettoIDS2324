package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * The PointController class handles the logic for managing Point objects, including addition,
 * validation, and searching operations. It leverages the IUserPlatform interface and UserRole
 * enumeration to determine user roles and permissions.
 */
public class PointController {

    /**
     * List to store Point objects managed by the controller.
     */
    List<Point> points;

    public PointController(List<Point> points) {
        this.points = points;
    }

    /**
     * Adds a Point to the list based on the user's role.
     *
     * @param point The Point to be added.
     * @param user  The user performing the operation.
     */
    public void addPoint(Point point, IUserPlatform user) {
        if (user.getUserType().equals(UserRole.Contributor))
            this.addWithPending(point);
        else
            this.addWithoutPending(point);
    }

    /**
     * Adds a Point with pending validation to the list.
     *
     * @param point The Point to be added.
     */
    private void addWithPending(Point point) {
        point.setValidation(false);
        this.points.add(point);
    }

    /**
     * Adds a Point without pending validation to the list.
     *
     * @param point The Point to be added.
     */
    private void addWithoutPending(Point point) {
        point.setValidation(true);
        this.points.add(point);
    }

    /**
     * Validates or removes a Point based on the user's choice.
     *
     * @param choice The user's choice for validation.
     * @param user   The user performing the validation.
     * @param point  The Point to be validated or removed.
     */
    public void validatePoint(boolean choice, IUserPlatform user, Point point) {
        if (user.getUserType().equals(UserRole.Curator)) {
            int index = this.points.indexOf(point);
            if(index != -1){
                Point pointToValidate = this.points.get(index);
                if (choice)
                    pointToValidate.setValidation(true);
                else
                    this.points.remove(pointToValidate);
            }
        }
    }

    /**
     * Searches for a Point by its title in the list.
     *
     * @param title The title of the Point to be searched.
     * @return An Optional containing the found Point, or empty if not found.
     */
    public Optional<Point> searchPoint(String title) {
        for (Point point : points) {
            if (point.getTitle().equals(title))
                return Optional.of(point);
        }
        return Optional.empty();
    }

}

