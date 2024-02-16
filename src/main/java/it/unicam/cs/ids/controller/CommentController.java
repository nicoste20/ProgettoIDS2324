package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.Repository.CommentRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Comment;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The  Comment controller class manages the addition and validation of a general comment
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link BaseUser}, {@link UserRole}, {@link IUserPlatform}, {@link Comment}
 */
@RestController
public class CommentController {

    private final CommentRepository comments;

    private final UserRepository users;

    @Autowired
    public CommentController(CommentRepository comments, UserRepository users){
        this.comments = comments;
        this.users = users;
    }

    /**
     * Adds a comment
     * @param comment the comment that we want to add
     * @param userId the user that wants to add a comment
     */
    @PostMapping("/add/comment/{id}")
    public void addComment(@RequestBody Comment comment , @PathParam("id") int userId) {
        if(users.existsById(userId)){
            BaseUser user = users.findById(userId).get();
            if (user.getUserType().equals(UserRole.Contributor) || user.getUserType().equals(UserRole.TouristAuthorized)) {
                if((user.getUserType().equals(UserRole.ContributorAuthorized) || user.getUserType().equals(UserRole.Curator)))
                    this.addWithoutPending(comment);
                else
                    this.addWithPending(comment);
            }
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
    @PutMapping("/validate/comment/{userId}/{commentId}")
    public void validateComment(@RequestBody boolean choice,@PathParam("userId") int userId, @PathParam("userId")
    int commentId) {
        if(users.existsById(userId)) {
            BaseUser curator = this.users.findById(userId).get();
            if(curator.getUserType().equals(UserRole.Curator)) {
                if(comments.existsById(commentId)){
                    if (choice)
                        this.comments.findById(commentId).get().setValidation(true);
                    else
                        this.comments.deleteById(commentId);
                }
            }
        }
    }

    @GetMapping(value ="/get/comment")
    public ResponseEntity<Object> getComment(){
        return new ResponseEntity<>(comments.findAll(), HttpStatus.OK);
    }
}

