package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.CommentNotExistExcpetion;
import it.unicam.cs.ids.Exception.MultimediaNotFoundException;
import it.unicam.cs.ids.Exception.UserBadTypeException;
import it.unicam.cs.ids.Exception.UserNotExistException;
import it.unicam.cs.ids.controller.Repository.*;
import it.unicam.cs.ids.model.content.Comment;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The  Comment controller class manages the addition and validation of a general comment
 * differentiating between immediate addition and pending approval based on the user's role.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository comments;
    private final PointRepository pointRepository;
    private final ItineraryRepository itineraryRepository;
    private final MultimediaRepository multimediaRepository;
    private final UserRepository users;

    @Autowired
    public CommentController(CommentRepository comments, PointRepository pointRepository, ItineraryRepository itineraryRepository, MultimediaRepository multimediaRepository, UserRepository users){
        this.comments = comments;
        this.pointRepository = pointRepository;
        this.itineraryRepository = itineraryRepository;
        this.multimediaRepository = multimediaRepository;
        this.users = users;
    }

    /**
     * Adds a comment to a multimedia
     * @param comment the comment that we want to add
     * @param userId the user that wants to add a comment
     * @param multimediaId the multimedia that we want to add
     */
    @PostMapping("/multimedia/add{userId}{multimediaId}")
    public ResponseEntity<?> addMultimediaComment(@RequestBody Comment comment , @PathParam("userId") int userId , @PathParam("multimediaId") int multimediaId) {
        Multimedia multimedia = this.multimediaRepository.findById(multimediaId).orElseThrow(MultimediaNotFoundException::new);
        this.addComment(userId,comment);
        multimedia.addComment(comment.getId());
        multimediaRepository.save(multimedia);
        return new ResponseEntity<>("Comment added", HttpStatus.OK);
    }
    /**
     * Adds a comment to an itinerary
     * @param comment the comment that we want to add
     * @param userId the user that wants to add a comment
     * @param itineraryId the itinerary that we want to add
     */
    @PostMapping("/itinerary/add{userId}{itineraryId}")
    public ResponseEntity<?> addItineraryComment(@RequestBody Comment comment , @PathParam("userId") int userId , @PathParam("itineraryId") int itineraryId) {
        Itinerary itinerary = this.itineraryRepository.findById(itineraryId).orElseThrow(MultimediaNotFoundException::new);
        this.addComment(userId,comment);
        itinerary.addComment(comment.getId());
        itineraryRepository.save(itinerary);
        return new ResponseEntity<>("Comment added", HttpStatus.OK);
    }
    /**
     * Adds a comment to a point
     * @param comment the comment that we want to add
     * @param userId the user that wants to add a comment
     * @param pointId the point that we want to add
     */
    @PostMapping("/point/add{userId}{pointId}")
    public ResponseEntity<?> addPointComment(@RequestBody Comment comment , @PathParam("userId") int userId, @PathParam("pointId") int pointId) {
        Point point = this.pointRepository.findById(pointId).orElseThrow(MultimediaNotFoundException::new);
        this.addComment(userId,comment);
        point.addComment(comment.getId());
        pointRepository.save(point);
        return new ResponseEntity<>("Comment added", HttpStatus.OK);
    }


    /**
     * Adds a comment with the specified user ID.
     * @param userId The ID of the user adding the comment.
     *@param comment The comment to be added.
     * @throws UserNotExistException if the user does not exist.
     */
    private void addComment(int userId, Comment comment){
        BaseUser user = this.users.findById(userId).orElseThrow(UserNotExistException::new);
        comment.setAuthorId(userId);
        if (user.getUserType().equals(UserRole.Contributor) || user.getUserType().equals(UserRole.TouristAuthorized)) {
            this.addWithPending(comment);
        }else {
            if((user.getUserType().equals(UserRole.ContributorAuthorized) || user.getUserType().equals(UserRole.Curator)))
                this.addWithoutPending(comment);
        }
    }

    /**
     * Adds a comment with pending validation to the list.
     *
     * @param comment The comment to be added.
     */
    private void addWithPending(Comment comment) {
        comment.setValidation(false);
        this.comments.save(comment);
    }

    /**
     * Adds a comment without pending validation to the list.
     *
     * @param comment The comment to be added.
     */
    private void addWithoutPending(Comment comment) {
        comment.setValidation(true);
        this.comments.save(comment);
    }

    /**
     * Validates or removes a comment based on the user's choice.
     *
     * @param choice The user's choice for validation.
     * @param userId   The user performing the validation.
     * @param commentId  The Point to be validated or removed.
     */
    @PutMapping("/validate{userId}{commentId}")
    public ResponseEntity<?> validateComment(@RequestBody boolean choice,@PathParam("userId") int userId, @PathParam("commentId") int commentId) {
        BaseUser curator = this.users.findById(userId).orElseThrow(UserNotExistException::new);
        if(curator.getUserType().equals(UserRole.Curator)) {
            if (choice) {
                Comment comment = comments.findById(commentId).orElseThrow(CommentNotExistExcpetion::new);
                comment.setValidation(true);
                comments.save(comment);
                return new ResponseEntity<>("Comment validated", HttpStatus.OK);
            }else
                this.comments.deleteById(commentId);
            return new ResponseEntity<>("Comment eliminated", HttpStatus.OK);
        }else
            throw new UserBadTypeException();
    }

    /**
     * Retrieves all comments.
     * @return A ResponseEntity containing all comments and HTTP status OK.
     */
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getComment(){
        return new ResponseEntity<>(comments.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves comments associated with a multimedia item specified by its ID.
     * @param multimediaId The ID of the multimedia item.
     * @return A ResponseEntity containing comments associated with the multimedia item and HTTP status OK.
     */
    @GetMapping("/multimedia{multimediaId}")
    public ResponseEntity<?> getCommentsByMultimediaId(@PathParam("multimediaId") int multimediaId) {
        List<Integer> commentIds = comments.findByMultimediaId(multimediaId);
        List<Comment> multimediaComments = new ArrayList<>();
        commentIds.forEach(commentId -> {
            Comment comment = comments.findById(commentId).orElse(null);
            if (comment != null) {
                multimediaComments.add(comment);
            }
        });
        return ResponseEntity.ok(multimediaComments);
    }
}

